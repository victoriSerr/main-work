package ru.itis.repositories.jpa;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.models.Dialog;
import ru.itis.models.Message;
import ru.itis.repositories.MessageRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Component
public class MessageRepositoryJpa implements MessageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<Message> findOne(Long aLong) {

        Message message = entityManager.find(Message.class, aLong);
        return Optional.ofNullable(message);

    }

    @Override
    @Transactional
    public List<Message> findAll() {

        TypedQuery<Message> query = entityManager.createQuery("select m from Message m", Message.class);
        return query.getResultList();

    }

    @Override
    @Transactional
    public List<Message> findAllReceivedMessages(String login) {

        TypedQuery<Message> query = entityManager.createQuery("select m from Message m where m.to_user = :login", Message.class);
        query.setParameter("login", login);
        return query.getResultList();

    }



    @Override
    @Transactional
    public void save(Message entity) {
        entityManager.persist(entity);
    }

    @Override
    @Transactional
    public void update(Message entity) {
        entityManager.merge(entity);
    }


    @Override
    @Transactional
    public Dialog find(Long id) {
        return entityManager.find(Dialog.class, id);
    }

    @Override
    @Transactional
    public void save(Dialog chat) {
        entityManager.persist(chat);
    }
}
