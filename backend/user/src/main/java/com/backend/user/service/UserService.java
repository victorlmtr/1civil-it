package com.backend.user.service;

import com.backend.user.model.Entity.Role;
import com.backend.user.model.Entity.User;
import com.backend.user.model.Mapper.CityMapper;
import com.backend.user.model.Mapper.UserMapper;
import com.backend.user.model.dto.CityDTO;
import com.backend.user.model.dto.UserDTO;
import com.backend.user.model.repository.RoleRepository;
import com.backend.user.model.repository.UserRepository;
import com.backend.user.service.serviceExt.EmailSenderService;
import com.backend.user.security.JwtTokenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.backend.user.config.WebSecurityConfig.passwordEncoder;

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
    private EmailSenderService emailSenderService;
    
    @Autowired
    private JwtTokenService jwtTokenService;

    // Create a new User
    public UserDTO createUser(UserDTO userDTO, String sourceApp) {
        // 1. Convert the UserDTO to a User entity
        User user = userMapper.toEntity(userDTO);

        // 2. Hash the password before persisting
        String passwordNoEncoded = user.getPasswordhash();
        user.setPasswordhash(passwordEncoder().encode(passwordNoEncoded));

        // 3. Set the creation date
        user.setCreationdate(Instant.now());

        // 4. Set default values for isVerified and isEnabled
        user.setIsverified(Optional.ofNullable(user.getIsverified()).orElse(false)); // Default: not verified
        user.setIsenabled(Optional.ofNullable(user.getIsenabled()).orElse(false));   // Default: disabled

        // 5. Assign the role based on the application source
        Role role = getRoleBasedOnAppSource(sourceApp);
        user.setRole(role);

        // 6. Find or create the city
        CityDTO cityDTO = cityService.findOrCreateCity(userDTO.getCity().getPostcode(), userDTO.getCity().getInseecode(), userDTO.getCity().getCityname());
        user.setCity(cityMapper.toEntity(cityDTO));

        // 7. Save the user in the database
        User createdUser = userRepository.save(user);

        // 8. Generate a JWT Token
        String token = jwtTokenService.generateToken(createdUser.getEmail());

        // 9. Build verification link
        String verificationLink = buildVerificationLink(token);

        // 10. Send verification email
        sendVerificationEmail(createdUser, verificationLink);

        // 11. Return the UserDTO of the created user
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


    private Role getRoleBasedOnAppSource(String sourceApp) {
        int roleId = "mobile".equalsIgnoreCase(sourceApp) ? 2 : 1; // Mobile -> Citizen role, Desktop -> Admin role
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    private String buildVerificationLink(String token) {
        return "http://localhost:8081/api-user/auth/verify?token=" + URLEncoder.encode(token, StandardCharsets.UTF_8);
    }

    private void sendVerificationEmail(User user, String verificationLink) {
        String subject = "Vérification de votre compte";

        // build body messsage with user first name and last name
        String body = String.format(
                "Bonjour %s %s,\n\n" +  // Ajout du prénom et nom de l'utilisateur
                        "Merci de vous être inscrit sur notre plateforme de signalements 1civil-it ! \nPour finaliser votre inscription, veuillez cliquer sur le lien ci-dessous afin de vérifier votre adresse e-mail : \n\n" +
                        "%s\n\n" +
                        "Ce lien expirera dans 24 heures.\n\n" +
                        "Cordialement,\n" +
                        "L'équipe 1civil-it",
                user.getFirstname(),    // user first name
                user.getLastname(),     // user last name
                verificationLink       // verification link
        );

        // send email
        emailSenderService.sendEmail(user.getEmail(), subject, body);
    }
}
