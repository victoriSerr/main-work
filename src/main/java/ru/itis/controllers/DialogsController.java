package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.MessageDto;
import ru.itis.models.AppUser;
import ru.itis.models.Dialog;
import ru.itis.models.Message;
import ru.itis.services.MessageService;
import ru.itis.services.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@PreAuthorize("isAuthenticated()")
public class DialogsController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @GetMapping(value = "/messages")
    public ModelAndView getMessages() {

//        System.out.println(id);
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = userService.findUserByLogin(auth.getName());

//        System.out.println(user.getDialogs());
        List<Dialog> dialogs = user.getDialogs();

        System.out.println(dialogs);

        modelAndView.setViewName("dialogs");
        modelAndView.addObject("dialogs", dialogs);

        return modelAndView;
    }

    @GetMapping(value = "/messages/{dialog-id:.+}")
    public ModelAndView getChatPage(@PathVariable("dialog-id") Long dialogId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView();

        Dialog dialog = messageService.findOne(dialogId);

        if (!dialog.getUserFrom().getLogin().equals(auth.getName()) && !dialog.getUserTo().getLogin().equals(auth.getName())) {
            System.out.println(dialog.getUserFrom().getLogin());
            System.out.println(dialog.getUserTo().getLogin());
            System.out.println(auth.getName());
            modelAndView.setViewName("redirect:/profile");
        } else {
            modelAndView.setViewName("chat");
        }
        List<MessageDto> messages = new ArrayList<>();

        for (Message m : dialog.getMessages()) {
            MessageDto messageDto = MessageDto.builder()
                    .dialogId(m.getDialog().getId().toString())
                    .text(m.getText())
                    .login(m.getFromLogin())
                    .build();
            messages.add(messageDto);
        }
        modelAndView.addObject("ims", messages);

        modelAndView.addObject("dialogId", dialogId);
        modelAndView.addObject("login", auth.getName());
        return modelAndView;
    }

    @GetMapping(value = "/im")
    public String createDialog(@RequestParam("receiverId") Long id) {

        System.out.println(id);
        AppUser userTo = userService.findUser(id);

        System.out.println(userTo);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser userFrom = userService.findUserByLogin(auth.getName());
        if (userTo.getId().equals(userFrom.getId())) {
            return "redirect:/profile";
        }
        Dialog dialog = messageService.find(userFrom.getId(), userTo.getId());
        if (dialog == null) {
            dialog = Dialog.builder()
                    .userFrom(userFrom)
                    .userTo(userTo)
                    .build();
            messageService.save(dialog);
        }

        return "redirect:/messages/" + dialog.getId();
    }

}
