package ru.itis.repositories;

import ru.itis.models.Post;

import java.util.List;

public interface PostRepository extends CrudDao<Post, Long> {
    List<Post> findOrgPosts(Long id);
}
