package ru.itis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.userdetails.User;
import ru.itis.config.AppConfiguration;
import ru.itis.models.*;
import ru.itis.repositories.*;
import ru.itis.services.MailServiceImpl;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws MessagingException {
//        MailServiceImpl sender = new MailServiceImpl("viktori.serr@gmail.com", "2960RtS!3082H");
//        sender.send("theme", "text",  "viktori.serr@gmail.com");

//        ArrayList<String> list = new ArrayList<>();
//        list.add("viktori.serr@gmail.com");
//        MailSender sender = new MailSender("viktori.serr@gmail.com", "2960RtS!3082H",
//                list, "theme", "text");
//        sender.send();
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        UserRepository userRepository = context.getBean(UserRepository.class);
        FileRepository fileRepository = context.getBean(FileRepository.class);
        OrganisationRepository organisationRepository = context.getBean(OrganisationRepository.class);
        AnimalRepository animalRepository = context.getBean(AnimalRepository.class);
        MessageRepository messageRepository = context.getBean(MessageRepository.class);


//        File file = File.builder()
//                .storageUrl("sadf")
//                .suffix("dsf")
//                .nameInStorage("a")
//                .realName("sdf")
//                .size(13L)
//                .build();
//
//        fileRepository.save(file);
//
//        System.out.println(fileRepository.findFile("a").get());
        AppUser user1 = AppUser.builder()
                .email("asd")
                .hashPassword("asd")
                .isConfirmed(true)
                .link("sdf")
                .login("1")
                .role(Role.ADMIN)
                .build();

        AppUser user2 = AppUser.builder()
                .email("asd")
                .hashPassword("asd")
                .isConfirmed(true)
                .link("sdf")
                .login("2")
                .role(Role.ADMIN)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);


        Organisation organisation = Organisation.builder()
                .address("asd")
                .description("asd")
                .name("asd")
                .phoneNumber("asd")
                .build();


        Animal animal = Animal.builder()
                .description("ad")
                .name("asd")
                .organisation(organisation)
                .build();


        Dialog dialog = Dialog.builder()
                .userFrom(user1)
                .userTo(user2)
                .build();


//
        messageRepository.save(dialog);
//
        Message message = Message.builder()
                .dialog(dialog)
                .date(new Date(new java.util.Date().getTime()))
                .text("hello")
                .build();



        messageRepository.save(message);


        AppUser appUser = userRepository.findByLogin("2").get();
        AppUser appUser1 = userRepository.findOne(1L).get();

        Dialog dialog1 = messageRepository.find(1L);

        System.out.println(dialog1);

        System.out.println(appUser);

//        System.out.println(appUser.getDialogs());

//        System.out.println(userRepository.findAll());



//        organisationRepository.save(organisation);
//        animalRepository.save(animal);
//        System.out.println(organisation);
//        System.out.println(animal);


//        organisation = organisationRepository.findOne(1L).get();

//        AppUser user1 = userRepository.findByLogin("a").get();
//        organisation.setModerator(user1);

//        AppUser user = userRepository.findByLogin("a").get();
//        System.out.println(user);
//        user.setEmail("dddddddddd");
//        userRepository.update(user);

//        List<Animal> list = animalRepository.findAll();
//
//        for (Animal a : list) {
//            System.out.println(a);
//        }

    }
}
