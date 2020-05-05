package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.dto.SignUpDto;
import ru.itis.models.AppUser;
import ru.itis.models.Mail;
import ru.itis.models.Message;
import ru.itis.models.Role;
import ru.itis.repositories.MessageRepository;
import ru.itis.repositories.UserRepository;

import java.util.*;

@Component
public class UserService {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private PasswordEncoder encoder;

    public List<AppUser> findAll() {
        return userRepository.findAll();
    }

    public void signUp(SignUpDto form) {

        String rawPassword = form.getPassword();
        String hashPassword = encoder.encode(rawPassword);


        AppUser appUser = AppUser.builder()
                .email(form.getEmail())
                .login(form.getLogin())
                .hashPassword(hashPassword)
                .isConfirmed(false)
                .role(Role.USER)
                .build();

        String link = "http://localhost:8080/confirmation/" + encoder.encode(Integer.toString(appUser.hashCode())).replace("/", "");
        appUser.setLink(link);

        userRepository.save(appUser);

        Map<String, String> model = new HashMap<>();

        model.put("login", appUser.getLogin());
        model.put("link", link);
        model.put("content", "Click the button to finish registration");

        Mail mail = Mail.builder()
                .subject("Registration")
                .to(form.getEmail())
                .model(model)
                .from("viktori.serr@gmail.com")
                .build();

        Thread thread = new Thread(() -> mailService.sendEmail(mail));
        thread.start();


    }


    public AppUser findUserByLink(String link) {
        Optional<AppUser> userCandidate = userRepository.findByLink(link);
        if (userCandidate.isPresent()) {
            return userCandidate.get();
        }
        throw new AccessDeniedException("User not found");
    }

    public AppUser findUserByLogin(String login) {
        Optional<AppUser> userCandidate = userRepository.findByLogin(login);

        if (userCandidate.isPresent()) {
            return userCandidate.get();
        }
        throw new AccessDeniedException("User not found");
    }

    public AppUser findUser(Long id) {
        Optional<AppUser> userCandidate = userRepository.findOne(id);
        if (userCandidate.isPresent()) {
            return userCandidate.get();
        }
        throw new AccessDeniedException("User not found");
    }

    public void update(AppUser appUser) {
        userRepository.update(appUser);
    }


    public void setModeratorRole(AppUser user) {
        user.setRole(Role.MODERATOR);
        update(user);


        Authentication auth = new UsernamePasswordAuthenticationToken(user.getLogin(), user.getHashPassword(),
                Collections.singleton(new SimpleGrantedAuthority("MODERATOR")));

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public void findMessages(AppUser user) {

//        List<Message> allMessages = messageRepository.findAllReceivedMessages(user.getLogin());
//
//        System.out.println(allMessages.size() + "count of messages");
//        for (Message m : allMessages) {
//            if (user.getMessages().containsKey(m.getFrom_user())) {
//                user.getMessages().get(m.getFrom_user()).add(m);
//            } else {
//                user.getMessages().put(m.getFrom_user(), List.of(m));
//            }
//        }

    }

}
