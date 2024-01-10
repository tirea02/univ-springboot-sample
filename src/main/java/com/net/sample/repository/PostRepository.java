package com.net.sample.repository;

import com.net.sample.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Post> getAllPosts() {
        return jdbcTemplate.query(
                "SELECT p.id, p.title, p.content, p.date, p.viewCount, p.img_file, p.userId as userAccountId, u.userId " +
                        "FROM post p " +
                        "JOIN userAccount u ON p.userId = u.id " +
                        "ORDER BY p.date DESC", // Ensure this is correctly ordering the posts
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

    public Post update(Post post) {
        String updateSql = "UPDATE post SET title = ?, content = ?, date = ?, viewCount = ?, img_file = ?, userId = ? WHERE id = ?";

        jdbcTemplate.update(updateSql,
                post.getTitle(),
                post.getContent(),
                post.getDate(),
                post.getViewCount(),
                post.getImgFile(),
                post.getUserAccountId(),
                post.getId()
        );

        return post;
    }

    public Post save(Post post) {
        String sql = "INSERT INTO post (title, content, date, viewCount, img_file, userId ) VALUES (?, ?, ?, ?, ?, ?)";

        // Ensure the order of parameters matches the SQL query
        jdbcTemplate.update(sql,
                post.getTitle(),
                post.getContent(),
                post.getDate(),
                post.getViewCount(),
                post.getImgFile(),
                post.getUserAccountId()
        );

        // Retrieve the last inserted ID
        int lastInsertedId = getLastInsertedId();

        // Set the ID of the Post object (assuming the ID is auto-generated)
        post.setId(lastInsertedId);

        return post;
    }

    private int getLastInsertedId() {

        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    public void deletePostById(int postId) {
        String sql = "DELETE FROM post WHERE id = ?";
        jdbcTemplate.update(sql, postId);
    }



}
