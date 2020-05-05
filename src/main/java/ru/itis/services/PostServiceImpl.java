package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import ru.itis.dto.PostDto;
import ru.itis.models.Organisation;
import ru.itis.models.Post;
import ru.itis.repositories.PostRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Component
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;


    @Autowired
    private OrganisationService organisationService;

    @Override
    public Post find(Long id) {

        Optional<Post> postCand = postRepository.findOne(id);
        if (postCand.isPresent()) {
            return postCand.get();
        }
        throw new AccessDeniedException("Post Not Found");
    }

    @Override
    public void save(PostDto post) {
        Organisation organisation = organisationService.find(Long.valueOf(post.getOrgId()));
        Post post1 = Post.builder()
                .date(new Date(new java.util.Date().getTime()))
                .title(post.getTitle())
                .text(post.getText())
                .organisation(organisation)
                .build();
        postRepository.save(post1);
    }

    @Override
    public List<Post> fingOrganisationPosts(Long id) {
        return postRepository.findOrgPosts(id);
    }
}
