package ru.itis.repositories.jpa;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.models.FileInfo;
import ru.itis.repositories.FileRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Component
public class FileRepositoryJpa implements FileRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<FileInfo> findFile(String nameInStorage) {
        TypedQuery<FileInfo> query = entityManager.createQuery("select f from FileInfo f where f.nameInStorage = :name", FileInfo.class);
        query.setParameter("name", nameInStorage);
        FileInfo fileInfo = query.getSingleResult();
        return Optional.ofNullable(fileInfo);
    }

    @Override
    public Optional<FileInfo> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<FileInfo> findAll() {
        return null;
    }

    @Override
    @Transactional
    public void save(FileInfo entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(FileInfo entity) {

    }
}
