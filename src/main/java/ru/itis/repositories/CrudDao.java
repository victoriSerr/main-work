package ru.itis.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T,ID> {
    Optional<T> findOne(ID id);
    List<T> findAll();
    void save(T entity);
    void update(T entity);
}