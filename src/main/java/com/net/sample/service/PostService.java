package com.net.sample.service;

import com.net.sample.dto.NewPost;
import com.net.sample.model.Post;
import com.net.sample.model.PostReply;
import com.net.sample.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PostRepository postRepository;



    public Post createPost(NewPost newPostDto, int userAccountId) {
        // Create a new Post entity from the DTO and userId
        Post post = new Post();
        post.setTitle(newPostDto.getTitle());
        post.setContent(newPostDto.getContent());
        post.setViewCount(0);
        post.setImgFile(newPostDto.getImgFile());
        post.setUserAccountId(userAccountId); // Set the userId of the post
        post.setDate(LocalDateTime.now()); // Set the current date and time

        // Save the post to the database
        return postRepository.save(post);
    }

    public Post incrementViewCount(Integer postId) {
        Post post = postRepository.getPostById(postId);
        if (post != null) {
            post.setViewCount(post.getViewCount() + 1);
            System.out.println(post.toString());
            postRepository.update(post);
        }
        return post;
    }

    public void removePost(int postId) {
        postRepository.deletePostById(postId);
    }



}
