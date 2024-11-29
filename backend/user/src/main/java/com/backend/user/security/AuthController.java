package com.backend.user.security;

import com.backend.user.model.Entity.User;
import com.backend.user.model.repository.UserRepository;
import com.backend.user.service.serviceExt.EmailSenderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-user/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final EmailSenderService emailSenderService;

    public AuthController(
            UserRepository userRepository,
            JwtTokenService jwtTokenService,
            EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
        this.emailSenderService = emailSenderService;
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam("token") String token) {
        try {
            // Extract the email from the token
            String email = jwtTokenService.extractEmailFromToken(token);

            //Retrieve user by email
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

            System.out.println("email de l'utilisateur récupéré : " + user.getEmail());

            // Validate account
            user.setIsenabled(true);
            userRepository.save(user);

            // Sending the confirmation email
            sendAccountValidationEmail(user);

            return ResponseEntity.ok("Compte vérifié avec succès !");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Token invalide ou autre erreur: " + e.getMessage());
        }
    }

    private void sendAccountValidationEmail(User user) {
        String subject = "Votre compte a été activé avec succès";

        // Construction of the body of the message with the user's first name, last name, and email
        String body = String.format(
                "Bonjour %s %s,\n\n" +  // place for user firstname and lastname
                        "Félicitations ! Votre compte sur la plateforme 1civil-it est maintenant actif.\n\n" +
                        "Voici votre identifiant de connexion :\n" +
                        "Email : %s\n" +  // user email
                        "Vous pouvez désormais vous connecter à votre compte en utilisant ces informations.\n\n" +
                        "Cordialement,\n" +
                        "L'équipe 1civil-it",
                user.getFirstname(),    // user firstname
                user.getLastname(),     // user lastname
                user.getEmail()        // user email
        );

        // Envoi de l'email
        emailSenderService.sendEmail(user.getEmail(), subject, body);
    }
}
