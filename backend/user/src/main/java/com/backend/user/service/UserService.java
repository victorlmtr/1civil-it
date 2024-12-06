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
import jakarta.servlet.http.HttpServletRequest;
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
import java.util.List;
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
        CityDTO cityDTO = cityService.findOrCreateCity(userDTO.getCity().getPostcode(), userDTO.getCity().getInseecode(), userDTO.getCity().getCityname());
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
        return "http://localhost:8081/api-user/auth/verify?token=" + URLEncoder.encode(token, StandardCharsets.UTF_8);
    }


    // Verifies the user's account by enabling the user and sending a validation email
    public void verifyAccount(String token) {

        String email = jwtTokenService.extractEmailFromToken(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND.value()));

        user.setIsenabled(true);
        userRepository.save(user);

        // Send validation email
        emailNotificationService.sendAccountValidationEmail(user);
    }


    // Log in a user and generate a JWT token
    public String login(UserDTO userDTO) {

        // Verify credentials (if the user exists)
        User user = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND.value()));

        // Check if the user is enabled
        if (!user.getIsenabled()) {
            System.out.println("Compte non activé pour l'email: " + userDTO.getEmail());
            throw new AccountNotVerifiedException(HttpStatus.FORBIDDEN.value()); // The account is not activated
        }

        // Authenticate via AuthenticationManager
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword())
            );
        } catch (BadCredentialsException e) {
            System.out.println("Bad credentials pour l'email: " + userDTO.getEmail());
            throw new InvalidCredentialsException(HttpStatus.UNAUTHORIZED.value());
        }

        // authentication ok and user isEnabled
        System.out.println("Authentification réussie pour l'utilisateur : " + user.getEmail());

        // Generate and return the JWT token
        return jwtTokenService.generateToken(userDTO.getEmail());
    }


    // Handle forgot password by sending a reset token
    public void handleForgotPassword(String email) {

        System.out.println("Email reçu : " + email);

        // Check if the user exists
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND.value()));

        // Generate a password reset token
        String resetToken = jwtTokenService.generateToken(email);

        // send the reset mail
        emailNotificationService.sendPasswordResetEmail(user, resetToken);  // Utilisation du service
    }


    // Reset the user's password using a token and new password
    public String resetPassword(String token, String newPassword) {
        try {

            if (!jwtTokenService.validateToken(token)) {
                throw new RuntimeException("Token invalide ou expiré.");
            }

            // Extract the email from the token
            String email = jwtTokenService.extractEmailFromToken(token);

            // Retrieve the user by email
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND.value()));

            // Update the password
            user.setPasswordhash(passwordEncoder.encode(newPassword));
            userRepository.save(user);

            return "Mot de passe réinitialisé avec succès.";

        } catch (Exception e) {

            throw new RuntimeException("Erreur lors de la réinitialisation du mot de passe : " + e.getMessage());
        }
    }


    // Disconnect user and add token to a blacklist
    public void logout(String token) throws IllegalArgumentException {

        if (token == null) {

            throw new IllegalArgumentException("Token manquant");
        }

        // Add token to blacklist
        tokenBlacklist.add(token);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
