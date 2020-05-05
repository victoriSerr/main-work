package ru.itis.controllers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Controller
public class DialogsController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @GetMapping(value = "/messages")
    public ModelAndView getMessages(@RequestParam("receiverId") Long id) {

        System.out.println(id);
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = userService.findUserByLogin(auth.getName());
//        user.getDialogs().addAll(user.getDialogs1());

        Collection<Dialog> dialogs = user.getDialogs();

        System.out.println(dialogs);

//        System.out.println(user.getId());
//        userService.findMessages(user);
//
//        Set<String> dialogs = user.getMessages().keySet();
//
        modelAndView.setViewName("dialogs");
        modelAndView.addObject("messages", dialogs);

        return modelAndView;
    }

    @GetMapping(value = "/chat/{dialog-id:.+}")
    public ModelAndView getChatPage(@PathVariable("dialog-id") Long dialogId) {
        ModelAndView modelAndView = new ModelAndView();

        Dialog dialog = messageService.find(dialogId);
        List<MessageDto> messages = new ArrayList<>();

        for (Message m : dialog.getMessages()) {
            MessageDto messageDto = MessageDto.builder()
                    .dialogId(m.getDialog().getId().toString())
                    .text(m.getText())
                    .build();
            messages.add(messageDto);
        }
        modelAndView.addObject("messages", messages);

        modelAndView.addObject("dialogId", dialogId);
        modelAndView.setViewName("chat");
        return modelAndView;
    }

    @PostMapping(value = "/messages")
    public String createDialog(@RequestParam Long id) {

        System.out.println(id);
        AppUser userTo = userService.findUser(id);

        System.out.println(userTo);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser userFrom = userService.findUserByLogin(auth.getName());
        Dialog dialog = Dialog.builder()
                .userFrom(userFrom)
                .userTo(userTo)
                .build();

        messageService.save(dialog);

        return "redirect:/chat/" + dialog.getId();
    }

}
