package ru.itis.repositories.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.models.AppUser;
import ru.itis.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

//@Component
public class UserRepositoryHibernate implements UserRepository {

//    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<AppUser> findByLogin(String login) {
        Session session = sessionFactory.openSession();

        Query<AppUser> user = session.createQuery("from AppUser u where u.login = :login", AppUser.class ).setParameter("login", login);

        return Optional.of(user.getSingleResult());

    }

    @Override
    public Optional<AppUser> findByLink(String link) {
        Session session = sessionFactory.openSession();

        Query<AppUser> user = session.createQuery("from AppUser u where u.link = :link", AppUser.class ).setParameter("link", link);

//        List<AppUser> list = user.list();
//        System.out.println(list.get(0));
        return Optional.of(user.getSingleResult());
    }

    @Override
    public Optional<AppUser> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<AppUser> findAll() {
        return null;
    }

    @Override
    public void save(AppUser entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
    }

    @Override
    public void update(AppUser entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(entity);
        transaction.commit();
    }
}
