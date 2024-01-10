package com.net.sample.controller;

import com.net.sample.dto.NewPost;
import com.net.sample.model.Post;
import com.net.sample.model.PostReply;
import com.net.sample.model.UserAccount;
import com.net.sample.repository.PostReplyRepository;
import com.net.sample.repository.PostRepository;
import com.net.sample.service.PostReplyService;
import com.net.sample.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {

    // Autowire services to interact with Post and PostpostReply tables
    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostReplyRepository postReplyRepository;

    @Autowired
    private PostReplyService postReplyService;

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postRepository.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> getPostDetails(@PathVariable(name = "postId") int postId) {
//        Post post = postRepository.getPostById(postId);
        Post post = postService.incrementViewCount(postId);
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

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable int postId) {
        postService.removePost(postId);
        return ResponseEntity.ok("Post deleted successfully");
    }


    @PostMapping("/posts/{postId}/replies")
    public ResponseEntity<?> createPostReply(@PathVariable int postId, @RequestBody String content, HttpSession session) {
        UserAccount loggedInUser = (UserAccount) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        // Create and save the postReply
        PostReply postReply = new PostReply();
        postReply.setUserAccountId(loggedInUser.getId());
        postReply.setPostId(postId);
        postReply.setContent(content);
        postReply.setDate(LocalDateTime.now());

        // Save postReply using your repository
        postReplyService.addReply(postReply);

        return ResponseEntity.ok(postReply);
    }

    @GetMapping("/posts/{postId}/getReplies")
    public ResponseEntity<List<PostReply>> getRepliesForPost(@PathVariable int postId) {
        System.out.println(postId);
        List<PostReply> replies = postReplyRepository.getRepliesByPostId(postId);
        System.out.println(replies.toString());
        return ResponseEntity.ok(replies);
    }

    @DeleteMapping("post/replies/{replyId}")
    public ResponseEntity<?> deleteReply(@PathVariable int replyId) {
        postReplyService.removeReply(replyId);
        return ResponseEntity.ok("Reply deleted successfully");
    }








}
