package ru.itis.repositories;

import ru.itis.models.FileInfo;

import java.util.Optional;

public interface FileRepository extends CrudDao<FileInfo, Long> {

    Optional<FileInfo> findFile(String name);

}
