package com.backend.user.security.authentication;

import com.backend.user.model.dto.UserDTO;
import com.backend.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api-user/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(
            UserService userService) {

        this.userService = userService;
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
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {

        // Call the service to handle login and return a JWT token
        String token = userService.login(userDTO);

        // If an exception occurs, it will be handled by GlobalExceptionHandler
        return ResponseEntity.ok(token);
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
}
