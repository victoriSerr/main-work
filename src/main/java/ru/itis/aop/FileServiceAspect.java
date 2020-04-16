package ru.itis.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.itis.models.File;
import ru.itis.models.Mail;
import ru.itis.services.MailService;
import ru.itis.services.UserService;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class FileServiceAspect {


    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Before(value = "execution(* ru.itis.services.FileService.save(..))")
    public void before(JoinPoint joinPoint) {
        Map<String, String> model = new HashMap<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        String email = userService.findUserByLogin(auth.getName()).getEmail();

        File file = (File) joinPoint.getArgs()[0];
//        String email = (String)joinPoint.getArgs()[1];
        model.put("login", email);
        model.put("content", "use button down below to download your file");
        model.put("link", "http://localhost:8080/files/" + file.getNameInStorage());

        Mail mail = Mail.builder()
                .subject("Check File")
                .to(email)
                .model(model)
                .from("viktori.serr@gmail.com")
                .build();

        mailService.sendEmail(mail);
    }
}
