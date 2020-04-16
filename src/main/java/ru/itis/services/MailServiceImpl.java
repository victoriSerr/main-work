package ru.itis.services;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import ru.itis.models.Mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

@Component
public class MailServiceImpl implements MailService {
    @Autowired
    @Qualifier("username")
    private String username;

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private FreeMarkerConfigurer freemarkerConfig;


    @Override
    public void sendEmail(Mail mail) {
        try {
            Template template = freemarkerConfig.getConfiguration().getTemplate("mail.ftl");


            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mail.getModel());


            MimeMessage message = sender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            message.setSubject(mail.getSubject(), "UTF-8");

            helper.setTo(mail.getTo());
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(new InternetAddress(username));


            sender.send(message);


        } catch (MessagingException | IOException | TemplateException e) {
            throw new IllegalArgumentException(e);
        }

    }
}
