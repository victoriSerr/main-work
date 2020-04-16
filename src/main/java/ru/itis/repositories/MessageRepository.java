package ru.itis.repositories;


import ru.itis.models.Dialog;
import ru.itis.models.Message;

import java.util.List;

public interface MessageRepository extends CrudDao<Message, Long> {
    List<Message> findAllReceivedMessages(String id);
    void save(Dialog chat);

    Dialog find(Long id);
}
