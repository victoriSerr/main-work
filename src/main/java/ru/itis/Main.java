package ru.itis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.itis.config.RootConfig;
import ru.itis.repositories.*;

import javax.mail.MessagingException;

public class Main {
    public static void main(String[] args) throws MessagingException {
//        MailServiceImpl sender = new MailServiceImpl("viktori.serr@gmail.com", "2960RtS!3082H");
//        sender.send("theme", "text",  "viktori.serr@gmail.com");

//        ArrayList<String> list = new ArrayList<>();
//        list.add("viktori.serr@gmail.com");
//        MailSender sender = new MailSender("viktori.serr@gmail.com", "2960RtS!3082H",
//                list, "theme", "text");
//        sender.send();
        ApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
        UserRepository userRepository = context.getBean(UserRepository.class);
        FileRepository fileRepository = context.getBean(FileRepository.class);
        OrganisationRepository organisationRepository = context.getBean(OrganisationRepository.class);
        AnimalRepository animalRepository = context.getBean(AnimalRepository.class);
        MessageRepository messageRepository = context.getBean(MessageRepository.class);

        System.out.println(context.getBean("springSessionRepositoryFilter"));

    }
}
