package ru.itis.repositories.jpa;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.models.Animal;
import ru.itis.repositories.AnimalRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
//        Query query = entityManager.createNativeQuery("select * from animal", Animal.class);
        TypedQuery<Animal> query1 = entityManager.createQuery("select a from Animal a", Animal.class);
        return query1.getResultList();
    }

    @Override
    @Transactional
    public void save(Animal entity) {
        entityManager.persist(entity);
    }

    @Override
    @Transactional
    public void update(Animal entity) {
        entityManager.merge(entity);
    }

    @Override
    public List<Animal> getOrgAnimals(Long id) {
        TypedQuery<Animal> query1 = entityManager.createQuery("select a from Animal a where a.organisation.id = :id", Animal.class);
        query1.setParameter("id", id);
        return query1.getResultList();
    }
}
