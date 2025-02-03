package com.nahuelgDev.journeyjoy.test_services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.nahuelgDev.journeyjoy.services.EmailsService;

@ExtendWith(MockitoExtension.class)
public class Test_EmailsService {
  
  @Mock JavaMailSender sender;

  @InjectMocks EmailsService service;

  @Test
  void sendEmail_invokesSendMethodWithRightMail() {
    String to = "to@example.com";
    String subject = "subject";
    String text = "body";

    SimpleMailMessage expected = new SimpleMailMessage();
    expected.setFrom("nahuel.gg.dev@gmail.com");
    expected.setTo(to);
    expected.setSubject(subject);
    expected.setText(text);

    service.sendEmail(to, subject, text);
    ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
    verify(sender).send(messageCaptor.capture());

    SimpleMailMessage actual = messageCaptor.getValue();
    assertEquals("nahuel.gg.dev@gmail.com", actual.getFrom());
    assertEquals(expected, actual);
  }
}
