package ru.itis.repositories.jpa;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.models.Dialog;
import ru.itis.repositories.DialogsRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Component
public class DialogsRepositoryJpa implements DialogsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Dialog> findOne(Long aLong) {
        Dialog dialog = entityManager.find(Dialog.class, aLong);
        return Optional.of(dialog);
    }

    @Override
    public List<Dialog> findAll() {
        return null;
    }

    @Override
    @Transactional
    public void save(Dialog entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(Dialog entity) {

    }

    @Override
    public List<Dialog> find(Long id) {
        TypedQuery<Dialog> query = entityManager.createQuery("select d from Dialog d where d.userFrom.id = :id or d.userTo.id = :id", Dialog.class);
        query.setParameter("id", id);
        System.out.println(query.getResultList());
        return query.getResultList();
    }

    @Override
    public Optional<Dialog> find(Long idFrom, Long idTo) {
        TypedQuery<Dialog> query = entityManager.createQuery("select d from Dialog d where (d.userFrom.id = :idFrom and d.userTo.id = :idTo) " +
                "or (d.userFrom.id = :idTo and d.userTo.id = :idFrom)", Dialog.class);
        query.setParameter("idFrom", idFrom);
        query.setParameter("idTo", idTo);
        Dialog dialog;
        try {
            dialog = query.getSingleResult();
            return Optional.of(dialog);
        } catch (NoResultException e) {

            return Optional.empty();
        }
    }
}
