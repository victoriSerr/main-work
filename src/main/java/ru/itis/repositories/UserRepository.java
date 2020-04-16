package ru.itis.repositories;

import ru.itis.models.AppUser;

import java.util.Optional;

public interface UserRepository extends CrudDao<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
    Optional<AppUser> findByLink(String link);
}
