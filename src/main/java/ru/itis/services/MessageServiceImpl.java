package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import ru.itis.dto.MessageDto;
import ru.itis.models.AppUser;
import ru.itis.models.Dialog;
import ru.itis.models.Message;
import ru.itis.repositories.DialogsRepository;
import ru.itis.repositories.MessageRepository;

import java.sql.Date;
import java.util.Optional;

@Component
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private DialogsRepository dialogsRepository;

    @Override
    public void save(MessageDto messageDto) {

        Dialog dialog = dialogsRepository.findOne(Long.valueOf(messageDto.getDialogId())).get();
        Message message = Message.builder()
                .text(messageDto.getText())
                .date(new Date(new java.util.Date().getTime()))
                .dialog(dialog)
                .fromLogin(messageDto.getLogin())
                .build();

        messageRepository.save(message);
    }

    @Override
    public void save(Dialog dialog) {
        dialogsRepository.save(dialog);
    }

    @Override
    public Dialog findOne(Long id) {
        Optional<Dialog> dialog;
        try {
            dialog = dialogsRepository.findOne(id);
            return dialog.get();
        } catch (Exception e) {
            throw new AccessDeniedException("No messages");
        }
    }

    @Override
    public Dialog find(Long idFrom, Long idTo) {
        Optional<Dialog> dialog;

        try {
            dialog = dialogsRepository.find(idFrom, idTo);
            return dialog.get();
        } catch (Exception e) {
            return null;
        }

    }
}
