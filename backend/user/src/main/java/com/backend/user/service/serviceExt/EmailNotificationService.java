package com.backend.user.service.serviceExt;

import com.backend.user.model.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService {

    @Autowired
    private EmailSenderService emailSenderService;

    // Méthode pour envoyer un email de vérification de compte
    public void sendAccountVerificationEmail(User user, String verificationLink) {
        String subject = "Vérification de votre compte";
        String body = String.format(
                "Bonjour %s %s,\n\n" +
                        "Merci de vous être inscrit sur notre plateforme de signalements 1civil-it ! \n" +
                        "Pour finaliser votre inscription, veuillez cliquer sur le lien ci-dessous afin de vérifier votre adresse e-mail : \n\n" +
                        "%s\n\n" +
                        "Ce lien expirera dans 24 heures.\n\n" +
                        "Cordialement,\n" +
                        "L'équipe 1civil-it",
                user.getFirstname(), user.getLastname(), verificationLink
        );

        emailSenderService.sendEmail(user.getEmail(), subject, body);
    }


    // Méthode pour envoyer un email de validation de compte
    public void sendAccountValidationEmail(User user) {
        String subject = "Votre compte a été activé avec succès";
        String body = String.format(
                "Bonjour %s %s,\n\n" +
                        "Félicitations ! Votre compte sur la plateforme 1civil-it est maintenant actif.\n\n" +
                        "Voici votre identifiant de connexion :\n" +
                        "Email : %s\n" +
                        "Vous pouvez désormais vous connecter à votre compte en utilisant ces informations.\n\n" +
                        "Cordialement,\n" +
                        "L'équipe 1civil-it",
                user.getFirstname(),
                user.getLastname(),
                user.getEmail()
        );

        emailSenderService.sendEmail(user.getEmail(), subject, body);
    }


    // Méthode pour envoyer un email de réinitialisation de mot de passe
    public void sendPasswordResetEmail(User user, String resetToken) {

        String resetLink = "http://localhost:8080/api-user/auth/reset-password?token=" + resetToken;
        String subject = "Réinitialisation de mot de passe";
        String body = "Cliquez sur le lien suivant pour réinitialiser votre mot de passe : " + resetLink;

        emailSenderService.sendEmail(user.getEmail(), subject, body);
    }
}
