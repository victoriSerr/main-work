package ru.itis.repositories.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.models.File;
import ru.itis.repositories.FileRepository;

import java.util.List;
import java.util.Optional;

//@Component
public class FileRepositoryHibernate implements FileRepository {

//    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public Optional<File> findFile(String name) {
        Session session = sessionFactory.openSession();
        Query<File> query = session.createQuery("from File f where f.nameInStorage = :name", File.class).setParameter("name", name);
        return Optional.of(query.getSingleResult());
    }

    @Override
    public Optional<File> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<File> findAll() {
        return null;
    }

    @Override
    public void save(File entity) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(entity);
        transaction.commit();
    }

    @Override
    public void update(File entity) {

    }
}
