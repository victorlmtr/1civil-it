package com.backend.user.service.serviceExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;
    @Qualifier("resourceHandlerMapping")
    @Autowired
    private HandlerMapping resourceHandlerMapping;

    // service for sending emails from the email address of the application
    public void sendEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1civil.it@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);

        System.out.println("Mail envoyé avec succès !");
    }
}
