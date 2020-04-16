package ru.itis.repositories.jpa;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.models.Animal;
import ru.itis.repositories.AnimalRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Component
public class AnimalRepositoryJpa implements AnimalRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Animal> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public List<Animal> findAll() {
        Query query = entityManager.createNativeQuery("select * from animal", Animal.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void save(Animal entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(Animal entity) {

    }
}
