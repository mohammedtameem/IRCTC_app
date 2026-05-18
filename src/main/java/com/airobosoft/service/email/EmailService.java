package com.airobosoft.service.email;

import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendExcelReport(String toEmail,
                                ByteArrayInputStream excelFile) {

        try {

            MimeMessage message =
                    mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(
                            message,
                            true
                    );

            helper.setTo(toEmail);

            helper.setSubject("Train Report");

            helper.setText(
                    "Please find attached train report."
            );

            helper.addAttachment(
                    "trains.xlsx",

                    new ByteArrayResource(
                            excelFile.readAllBytes()
                    )
            );

            mailSender.send(message);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to send email",
                    e
            );
        }
    }
}