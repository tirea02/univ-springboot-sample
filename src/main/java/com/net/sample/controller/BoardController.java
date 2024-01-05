package com.net.sample.controller;

import com.net.sample.model.Guest;
import com.net.sample.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {

    // Autowire services to interact with guest and guestreply tables
    @Autowired
    private GuestService guestService;

    @GetMapping("/posts")
    public ResponseEntity<List<Guest>> getAllPosts() {
        List<Guest> posts = guestService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Guest> getPostDetails(@PathVariable int postId) {
        Guest post = guestService.getPostById(postId);
        return ResponseEntity.ok(post);
    }


//    @PostMapping("/posts")
//    public ResponseEntity<String> createNewPost(@RequestBody Guest newPost) {
//        // Save new post and return appropriate response
//    }

    // Additional methods for handling replies, etc.
}
