package ru.itis.repositories.jpa;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.models.AppUser;
import ru.itis.models.Post;
import ru.itis.repositories.PostRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Component
public class PostRepositoryJpa implements PostRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Post> findOne(Long aLong) {

        return Optional.ofNullable(entityManager.find(Post.class, aLong));
    }

    @Override
    public List<Post> findAll() {
        return null;
    }

    @Override
    @Transactional
    public void save(Post entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(Post entity) {

    }

    @Override
    public List<Post> findOrgPosts(Long id) {
        TypedQuery<Post> query =
                entityManager.createQuery("select p from Post p where p.organisation.id = :id", Post.class);
        query.setParameter("id", id);
        return query.getResultList();
    }
}
