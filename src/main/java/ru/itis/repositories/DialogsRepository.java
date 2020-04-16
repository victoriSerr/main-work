package ru.itis.repositories;

import ru.itis.models.Dialog;

import java.util.List;

public interface DialogsRepository extends CrudDao<Dialog, Long> {

    List<Dialog> find(Long id);
}
