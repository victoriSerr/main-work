package ru.itis.repositories.jpa;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class ChatRepositoryJpa {

    @PersistenceContext
    private EntityManager entityManager;
}
