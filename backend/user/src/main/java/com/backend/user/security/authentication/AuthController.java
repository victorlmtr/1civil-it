package com.backend.user.security.authentication;

import com.backend.user.model.dto.UserDTO;
import com.backend.user.security.tokenJWT.JwtTokenService;
import com.backend.user.security.tokenJWT.TokenBlacklist;
import com.backend.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    @PostMapping("/login-desktop")
    public ResponseEntity<String> loginDesktop(@RequestBody UserDTO userDTO) {

        // Appel à la méthode de login avec la vérification du rôle "ADMIN"
        String token = userService.login(userDTO, true);
        return ResponseEntity.ok(token);
    }


    // Endpoint for handling forgotten password
    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> request) {
        Map<String, String> response = new HashMap<>();

        try {
            String email = request.get("email");

            // Appel au service pour gérer la réinitialisation du mot de passe
            userService.handleForgotPassword(email);

            response.put("message", "Un email de réinitialisation a été envoyé.");
            return ResponseEntity.ok(response);

        } catch (Exception e) {

            response.put("message", "Erreur : " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }


    // Endpoint for resetting the password with a token and new password
    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestParam("token") String token, @RequestBody Map<String, String> request) {
        // Call the service to reset the password
        String newPassword = request.get("newPassword");

        // Appeler le service pour réinitialiser le mot de passe
        Map<String, String> response = userService.resetPassword(token, newPassword);

        return ResponseEntity.ok(response);  // Réponse JSON
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


    @GetMapping("/current-user-id")
    public ResponseEntity<Integer> getCurrentUserId(@RequestHeader("Authorization") String authorizationHeader) {

        // extract token from header autorization
        String token = authorizationHeader.replace("Bearer ", "");

        // get user id from token
        int userId = userService.getUserIdFromToken(token);

        // return id
        return ResponseEntity.ok(userId);
    }


}
