package com.backend.user.service;

import com.backend.user.exception.*;
import com.backend.user.model.Entity.Role;
import com.backend.user.model.Entity.User;
import com.backend.user.model.Mapper.CityMapper;
import com.backend.user.model.Mapper.UserMapper;
import com.backend.user.model.dto.CityDTO;
import com.backend.user.model.dto.UserDTO;
import com.backend.user.model.repository.RoleRepository;
import com.backend.user.model.repository.UserRepository;
import com.backend.user.security.tokenJWT.TokenBlacklist;
import com.backend.user.service.serviceExt.EmailNotificationService;
import com.backend.user.security.tokenJWT.JwtTokenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private CityService cityService;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenBlacklist tokenBlacklist;


    // Create a new User
    public UserDTO createUser(UserDTO userDTO, String sourceApp) {

        // 0. Check if the user already exists by email
        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException(HttpStatus.CONFLICT.value());
        }

        // 1. Check the password strength
        if (!isPasswordStrong(userDTO.getPassword())) {
            throw new InvalidPasswordException(HttpStatus.BAD_REQUEST.value());
        }

        // 2. Convert the UserDTO to a User entity
        User user = userMapper.toEntity(userDTO);

        // 3. Hash the password before persisting
        String passwordNoEncoded = user.getPasswordhash();
        user.setPasswordhash(passwordEncoder.encode(passwordNoEncoded));

        // 4. Set the creation date
        user.setCreationdate(Instant.now());

        // 5. Set default values for isVerified and isEnabled
        user.setIsverified(Optional.ofNullable(user.getIsverified()).orElse(false)); // Default: not verified
        user.setIsenabled(Optional.ofNullable(user.getIsenabled()).orElse(false));   // Default: disabled

        // 6. Assign the role based on the application source
        Role role = getRoleBasedOnAppSource(sourceApp);
        user.setRole(role);

        // 7. Find or create the city
        CityDTO cityDTO = cityService.findOrCreateCity(
                userDTO.getCity().getPostcode(),
                userDTO.getCity().getInseecode(),
                userDTO.getCity().getCityname());
        user.setCity(cityMapper.toEntity(cityDTO));

        // 8. Save the user in the database
        User createdUser = userRepository.save(user);

        // 9. Generate a JWT Token
        String token = jwtTokenService.generateToken(createdUser.getEmail());

        // 10. Build verification link
        String verificationLink = buildVerificationLink(token);

        // 11. Send verification email
        emailNotificationService.sendAccountVerificationEmail(createdUser, verificationLink);

        // 12. Return the UserDTO of the created user
        return userMapper.toDTO(createdUser);
    }

    // Get all Users
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get a User by ID
    public Optional<UserDTO> getUserById(Integer id) {
        return userRepository.findById(id).map(userMapper::toDTO);
    }

    // Update an existing User
    public Optional<UserDTO> updateUser(Integer id, UserDTO userDTO) {
        return userRepository.findById(id).map(existingUser -> {
            userMapper.partialUpdate(userDTO, existingUser);
            User updatedUser = userRepository.save(existingUser);
            return userMapper.toDTO(updatedUser);
        });
    }

    // Delete a User by ID
    public boolean deleteUser(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }


    // check the password strength
    private boolean isPasswordStrong(String password) {

        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";

        return password.matches(regex);
    }


    // Determines the user's role based on the application source (mobile or desktop)
    private Role getRoleBasedOnAppSource(String sourceApp) {
        int roleId = "mobile".equalsIgnoreCase(sourceApp) ? 2 : 1; // Mobile -> Citizen role, Desktop -> Admin role
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }


    // Constructs the verification link for the account based on the generated JWT token
    private String buildVerificationLink(String token) {
        return "http://localhost:8081/api-user/auth/verify?token=" +
                URLEncoder.encode(token, StandardCharsets.UTF_8);
    }


    // Verifies the user's account by enabling the user and sending a validation email
    public void verifyAccount(String token) {

        // Validate the token before processing
        if (!jwtTokenService.validateToken(token)) {
            throw new InvalidTokenException(HttpStatus.UNAUTHORIZED.value());
        }

        String email = jwtTokenService.extractEmailFromToken(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND.value()));

        user.setIsenabled(true);
        userRepository.save(user);

        // Send validation email
        emailNotificationService.sendAccountValidationEmail(user);
    }


    // Méthode utilisée par l'application mobile
    public String login(UserDTO userDTO) {
        return login(userDTO, false); // Par défaut, la requête n'est pas considérée comme venant de l'application desktop
    }

    // Méthode utilisée par l'application desktop
    public String login(UserDTO userDTO, boolean isDesktopRequest) {
        // Logique existante
        User user = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND.value()));

        if (!user.getIsenabled()) {
            System.out.println("Compte non activé pour l'email: " + userDTO.getEmail());
            throw new AccountNotVerifiedException(HttpStatus.FORBIDDEN.value());
        }

        if (isDesktopRequest && !user.getRole().getRolename().contains("Admin")) {
            System.out.println("Accès refusé pour l'utilisateur non admin : " + userDTO.getEmail());
            throw new AccessDeniedException(HttpStatus.FORBIDDEN.value());
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword())
            );
        } catch (BadCredentialsException e) {
            System.out.println("Bad credentials pour l'email: " + userDTO.getEmail());
            throw new InvalidCredentialsException(HttpStatus.UNAUTHORIZED.value());
        }

        System.out.println("Authentification réussie pour l'utilisateur : " + user.getEmail());
        return jwtTokenService.generateToken(userDTO.getEmail());
    }


    // Handle forgot password by sending a reset token
    public void handleForgotPassword(String email) {

        // Afficher l'email reçu
        System.out.println("Email reçu : " + email);

        // Vérifier si l'utilisateur existe
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND.value()));

        // Générer un jeton de réinitialisation de mot de passe
        String resetToken = jwtTokenService.generateToken(email);

        // Construire le lien de réinitialisation
        String resetLink = buildResetPasswordLink(resetToken);

        // Envoyer l'email de réinitialisation du mot de passe
        emailNotificationService.sendPasswordResetEmail(user, resetLink);
    }

    // Méthode pour construire le lien de réinitialisation du mot de passe
    private String buildResetPasswordLink(String token) {
        return "http://localhost:4200/reset-password?token=" + URLEncoder.encode(token, StandardCharsets.UTF_8);
    }


    // Reset the user's password using a token and new password
    public Map<String, String> resetPassword(String token, String newPassword) {
        // Vérification de la force du mot de passe
        if (!isPasswordStrong(newPassword)) {
            throw new InvalidPasswordException(HttpStatus.BAD_REQUEST.value());
        }

        if (!jwtTokenService.validateToken(token)) {
            throw new RuntimeException("Token invalide ou expiré.");
        }

        // Extraire l'email du token
        String email = jwtTokenService.extractEmailFromToken(token);

        // Récupérer l'utilisateur par email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND.value()));

        // Mettre à jour le mot de passe
        user.setPasswordhash(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Créer un objet JSON avec le message
        Map<String, String> response = new HashMap<>();
        response.put("message", "Mot de passe réinitialisé avec succès.");

        return response;  // Renvoi de la réponse sous forme d'objet JSON
    }



    // Deconnect user and add token to a blacklist
    public void logout(String token) throws IllegalArgumentException {

        if (token == null) {

            throw new IllegalArgumentException("Token manquant");
        }

        // Add token to blacklist
        tokenBlacklist.add(token);
    }


    public int getUserIdFromToken(String token) {
        // extract mail from token
        String email = jwtTokenService.extractEmailFromToken(token);

        // find user in db
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND.value()));

        // return user id
        return user.getId();
    }

}
