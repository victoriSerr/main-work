package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.PostDto;
import ru.itis.models.Post;
import ru.itis.services.PostService;

import java.time.LocalDate;
import java.util.Date;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/new-post")
    public ResponseEntity<Object> savePost(@RequestBody PostDto post) {
        System.out.println(post);
        postService.save(post);
        return ResponseEntity.ok().build();
    }
}
