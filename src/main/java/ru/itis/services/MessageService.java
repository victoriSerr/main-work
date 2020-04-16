package ru.itis.services;

import ru.itis.dto.MessageDto;
import ru.itis.models.Dialog;
import ru.itis.models.Message;

public interface MessageService {
    void save(MessageDto message);
    void save(Dialog dialog);
    Dialog find(Long id);
}
