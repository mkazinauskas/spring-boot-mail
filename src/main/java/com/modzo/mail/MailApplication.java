package com.modzo.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootApplication
public class MailApplication {

    private static final Logger logger = LoggerFactory.getLogger(MailApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class, args);
    }

    @Bean
    CommandLineRunner command(JavaMailSender javaMailSender) {
        return (args) -> {
                sendMail(javaMailSender);
        };
    }

    private void sendMail(JavaMailSender javaMailSender) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("from@someuser.com");
        mail.setTo("to@mail.com");
        mail.setSubject("Test Mail");
        mail.setText("Mail body");
        javaMailSender.send(mail);
        logger.info("Mail sent!");
    }
}
