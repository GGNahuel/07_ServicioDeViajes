package com.nahuelgDev.journeyjoy.services;

import static com.nahuelgDev.journeyjoy.utilities.Verifications.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailsService {
  @Autowired
  private JavaMailSender mailSender;

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
