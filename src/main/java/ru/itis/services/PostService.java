package ru.itis.services;

import ru.itis.dto.PostDto;
import ru.itis.models.Post;

import java.util.List;

public interface PostService {
    Post find(Long id);
    void save(PostDto post);
    List<Post> fingOrganisationPosts(Long id);
}
