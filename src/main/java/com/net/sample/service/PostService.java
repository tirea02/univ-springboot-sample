package com.net.sample.service;

import com.net.sample.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Post> getAllPosts() {
        return jdbcTemplate.query(
                "SELECT p.id, p.title, p.content, p.date, p.viewCount, p.img_file, p.userId as userAccountId, u.userId FROM post p JOIN userAccount u ON p.userId = u.id ORDER BY p.date DESC",
                (rs, rowNum) -> new Post(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("date").toLocalDateTime(),
                        rs.getInt("viewCount"),
                        rs.getString("img_file"),
                        rs.getInt("userAccountId"),
                        rs.getString("userId")
                )
        );
    }

    public Post getPostById(int postId) {
        return jdbcTemplate.queryForObject(
                "SELECT p.id, p.title, p.content, p.date, p.viewCount, p.img_file, p.userId as userAccountId, u.userId FROM post p JOIN userAccount u ON p.userId = u.id WHERE p.id = ?",
                new Object[]{postId},
                (rs, rowNum) ->
                        new Post(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("content"),
                                rs.getTimestamp("date").toLocalDateTime(),
                                rs.getInt("viewCount"),
                                rs.getString("img_file"),
                                rs.getInt("userAccountId"),
                                rs.getString("userId")
                        )
        );
    }
}
