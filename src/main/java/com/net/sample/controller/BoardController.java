package com.net.sample.controller;

import com.net.sample.dto.NewPost;
import com.net.sample.model.Post;
import com.net.sample.model.UserAccount;
import com.net.sample.repository.PostRepository;
import com.net.sample.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {

    // Autowire services to interact with Post and Postreply tables
    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postRepository.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> getPostDetails(@PathVariable(name = "postId") int postId) {
        Post post = postRepository.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    @PostMapping("/createPost")
    public ResponseEntity<?> createPost(@RequestBody NewPost postDto, HttpSession session) {
        System.out.println(postDto.toString());

        UserAccount loggedInUser = (UserAccount) session.getAttribute("loggedInUser");
        Integer userId = loggedInUser.getId();
        if (userId == null) {
            // User is not logged in or session has expired
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        // Now use the userId to create the post
        Post newPost = postService.createPost(postDto, userId);
        return ResponseEntity.ok(newPost);
    }



//    @PostMapping("/posts")
//    public ResponseEntity<String> createNewPost(@RequestBody Post newPost) {
//        // Save new post and return appropriate response
//    }

    // Additional methods for handling replies, etc.
}
