package com.modzo.mail;

import com.samskivert.mustache.Mustache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mustache.MustacheResourceTemplateLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class MailApplication {

    private static final Logger logger = LoggerFactory.getLogger(MailApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class, args);
    }

    @Bean
    CommandLineRunner command(JavaMailSender javaMailSender,
                              ResourceLoader resourceLoader,
                              Mustache.Compiler compiler,
                              MustacheResourceTemplateLoader templateLoader) {
        return (args) -> sendMail(javaMailSender, resourceLoader, compiler, templateLoader);

    }

    private void sendMail(JavaMailSender javaMailSender,
                          ResourceLoader resourceLoader,
                          Mustache.Compiler compiler,
                          MustacheResourceTemplateLoader templateLoader) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setFrom("from@mymail.com");
            helper.setTo("to@myMail.com");
            helper.setSubject("Sample message");
            helper.addAttachment("test_attachment.pdf",
                    resourceLoader.getResource("classpath:/static/test_attachment.pdf"));
            helper.setText(body(compiler, templateLoader), true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e.getMessage());
        }

        logger.info("Mail sent!");
    }

    private String body(Mustache.Compiler compiler,
                        MustacheResourceTemplateLoader templateLoader) {
        Map<String, Object> model = new HashMap<>();
        model.put("time", LocalDateTime.now().toString());

        try {
            Reader template = templateLoader.getTemplate("mail-message");
            return compiler.compile(template).execute(model);
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
}
