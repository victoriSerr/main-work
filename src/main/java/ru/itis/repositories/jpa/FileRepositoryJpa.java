package ru.itis.repositories.jpa;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.models.AppUser;
import ru.itis.models.File;
import ru.itis.repositories.FileRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Component
public class FileRepositoryJpa implements FileRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<File> findFile(String nameInStorage) {
        Query query = entityManager.createNativeQuery("select * from file where nameinstorage = ?1", File.class).setParameter(1, nameInStorage);
        File file = (File) query.getSingleResult();
        return Optional.ofNullable(file);
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
    @Transactional
    public void save(File entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(File entity) {

    }
}
