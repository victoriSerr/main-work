package ru.itis.repositories;

import ru.itis.models.File;

import java.util.Optional;

public interface FileRepository extends CrudDao<File, Long> {

    Optional<File> findFile(String name);

}
