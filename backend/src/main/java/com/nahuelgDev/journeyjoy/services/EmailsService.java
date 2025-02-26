package com.nahuelgDev.journeyjoy.services;

import static com.nahuelgDev.journeyjoy.utilities.Verifications.checkFieldsHasContent;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.nahuelgDev.journeyjoy.utilities.Verifications.Field;

@Service
public class EmailsService {
  JavaMailSender mailSender = new JavaMailSenderImpl();

  public void sendEmail(String to, String subject, String body) {
    checkFieldsHasContent(new Field("destinatario", to), new Field("asunto", subject), new Field("contenido", body));
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(subject);
    message.setText(body);
    message.setFrom("nahuel.gg.dev@gmail.com");

    mailSender.send(message);
  }
}
