package ru.itis.controllers;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.MessageDto;
import ru.itis.models.AppUser;
import ru.itis.models.Dialog;
import ru.itis.models.Message;
import ru.itis.services.MessageService;
import ru.itis.services.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MessageController {

    private static final Map<String, List<MessageDto>> messages = new HashMap<>();

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;


    @SneakyThrows
    @GetMapping(value = "/messages/{dialog-id:.+}")
    public ResponseEntity<List<MessageDto>> getMessages(@PathVariable("dialog-id") String dialogId) {

//        System.out.println(dialogId);
//        System.out.println(messages);

        System.out.println(messages + " in get");


        synchronized (messages.get(dialogId)) {
            // если нет сообщений уходим в ожидание
            if (messages.get(dialogId).isEmpty()) {
                messages.get(dialogId).wait();
            }
        }

        List<MessageDto> response = new ArrayList<>(messages.get(dialogId));

        System.out.println(response + " response " + SecurityContextHolder.getContext().getAuthentication().getName());

        messages.get(dialogId).clear();

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/messages/{dialog-id:.+}")
    public ResponseEntity<MessageDto> receiveMessage(@RequestBody MessageDto message, @PathVariable("dialog-id") String id) {

        String dialogId = message.getDialogId().toString();


//        System.out.println(message);

        if (!messages.containsKey(dialogId)) {
            System.out.println("new dialog");
            // добавляем эту страницу в Map-у страниц
            messages.put(dialogId, new ArrayList<>());
        }
        // полученное сообщение добавляем для всех открытых страниц нашего приложения
        // перед тем как положить сообщение для какой-либо страницы
        // мы список сообщений блокируем


        synchronized (messages.get(dialogId)) {
            if (!message.getText().equals("Login")) {
                message.setDialogId((dialogId));
                messageService.save(message);
                // добавляем сообщение
                messages.get(dialogId).add(message);

                System.out.println(messages + " in post");
                // говорим, что другие потоки могут воспользоваться этим списком
                messages.get(dialogId).notifyAll();
            }
        }


        return ResponseEntity.ok().build();
    }
}
