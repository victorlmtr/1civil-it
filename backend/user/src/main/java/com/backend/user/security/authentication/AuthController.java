package com.backend.user.security.authentication;

import com.backend.user.model.dto.UserDTO;
import com.backend.user.security.tokenJWT.JwtTokenService;
import com.backend.user.security.tokenJWT.TokenBlacklist;
import com.backend.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api-user/auth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenService jwtTokenService;

    public AuthController(
            UserService userService,
            JwtTokenService jwtTokenService) {

        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
    }


    // Endpoint to verify the account with the given token
    @GetMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam("token") String token) {

        try {

            // Call the service to verify the account
            userService.verifyAccount(token);

            return ResponseEntity.ok("Compte vérifié avec succès !");

        } catch (Exception e) {

            // If an error occurs, return a bad request with the error message
            return ResponseEntity.badRequest().body("Token invalide ou autre erreur: " + e.getMessage());
        }
    }


    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody UserDTO userDTO,
            @RequestParam(required = false, defaultValue = "false") boolean includeUserId) {

        // Call the service to handle login and return a JWT token
        String token = userService.login(userDTO);

        // Conditionally include userId in the response
        if (includeUserId) {
            // Retrieve user information (e.g., userId) from the service
            Integer userId = userService.findByEmail(userDTO.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found")) // Replace with custom exception
                    .getId();

            // Return a response with both token and userId
            return ResponseEntity.ok(Map.of("token", token, "userId", userId));
        }

        // Default response (token only)
        return ResponseEntity.ok(Map.of("token", token));
    }


    // Endpoint for handling forgotten password
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {

        try {

            String email = request.get("email");

            // Call the service to handle password reset logic
            userService.handleForgotPassword(email);

            return ResponseEntity.ok("Un email de réinitialisation a été envoyé.");

        } catch (Exception e) {

            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }


    // Endpoint for resetting the password with a token and new password
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String token, @RequestBody Map<String, String> request) {
        try {

            String newPassword = request.get("newPassword");

            // Call the service to reset the password
            String response = userService.resetPassword(token, newPassword);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }


    // endpoint for deconnect user
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {

        try {
            // extract token from request
            String token = jwtTokenService.extractToken(request);

            if (token == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token manquant");
            }

            // add token to userService
            userService.logout(token);

            return ResponseEntity.ok("Déconnexion réussie");

        } catch (Exception e) {

            // handle exceptions and errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la déconnexion : " + e.getMessage());
        }
    }


}
