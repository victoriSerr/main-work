package ru.itis.repositories;

import ru.itis.models.Dialog;

import java.util.List;
import java.util.Optional;

public interface DialogsRepository extends CrudDao<Dialog, Long> {

    List<Dialog> find(Long id);
    Optional<Dialog> find(Long idFrom, Long idTo);
}
