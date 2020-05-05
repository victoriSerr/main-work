package ru.itis.repositories.jpa;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.models.Organisation;
import ru.itis.repositories.OrganisationRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Component
public class OrganisationRepositoryJpa implements OrganisationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<Organisation> findOne(Long aLong) {
        Organisation o1 = entityManager.find(Organisation.class, aLong);
        return Optional.of(o1);
    }

    @Override
    public List<Organisation> findAll() {
//        Query query = entityManager.createNativeQuery("select * from organisation", Organisation.class);
        TypedQuery<Organisation> query1
                = entityManager.createQuery("select o from Organisation o", Organisation.class);

        return query1.getResultList();
    }

    @Override
    @Transactional
    public void save(Organisation entity) {

        entityManager.persist(entity);
    }

    @Override
    public void update(Organisation entity) {

    }
}
