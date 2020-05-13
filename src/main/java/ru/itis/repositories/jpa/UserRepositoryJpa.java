package ru.itis.repositories.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.models.AppUser;
import ru.itis.repositories.DialogsRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryJpa implements ru.itis.repositories.UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DialogsRepository dialogsRepository;

    @Override
    @Transactional
    public Optional<AppUser> findByLogin(String login) {
        TypedQuery<AppUser> query =
                entityManager.createQuery("select distinct u from AppUser u where u.login = :login", AppUser.class);

//        left join fetch u.dialogs left join fetch u.dialogs1
//        System.out.println(query);
        query.setParameter("login", login);
        AppUser user = query.getSingleResult();
        user.setDialogs(dialogsRepository.find(user.getId()));
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<AppUser> findByLink(String link) {
        TypedQuery<AppUser> query = entityManager.createQuery("select u from AppUser  u where u.link = :link", AppUser.class);
        query.setParameter("link", link);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public Optional<AppUser> findOne(Long aLong) {
        return Optional.ofNullable(entityManager.find(AppUser.class, aLong));
    }

    @Override
    public List<AppUser> findAll() {
        return null;
    }


    @Override
    @Transactional
    public void save(AppUser entity) {
        entityManager.persist(entity);
    }

    @Override
    @Transactional
    public void update(AppUser entity) {
        entityManager.merge(entity);
    }
}
