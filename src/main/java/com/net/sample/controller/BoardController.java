package com.net.sample.controller;

import com.net.sample.model.Post;
import com.net.sample.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {

    // Autowire services to interact with Post and Postreply tables
    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> getPostDetails(@PathVariable(name = "postId") int postId) {
        Post post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }



//    @PostMapping("/posts")
//    public ResponseEntity<String> createNewPost(@RequestBody Post newPost) {
//        // Save new post and return appropriate response
//    }

    // Additional methods for handling replies, etc.
}
