package com.net.sample.repository;

import com.net.sample.model.PostReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostReplyRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<PostReply> rowMapper = (rs, rowNum) -> {
        PostReply reply = new PostReply();
        reply.setId(rs.getInt("id"));
        reply.setUserAccountId(rs.getInt("userAccountId"));
        reply.setUserId(rs.getString("userId"));
        reply.setPostId(rs.getInt("postId"));
        reply.setContent(rs.getString("content"));
        reply.setDate(rs.getTimestamp("date").toLocalDateTime());
        return reply;
    };

    public List<PostReply> getRepliesByPostId(int postId) {
        String sql = "SELECT pr.*, ua.userId FROM postreply pr " +
                "JOIN userAccount ua ON pr.userAccountId = ua.id " +
                "WHERE pr.postId = ?";

        return jdbcTemplate.query(sql, new Object[]{postId}, rowMapper);
    }

    public void saveReply(PostReply reply) {
        String sql = "INSERT INTO postreply (userAccountId, postId, content, date) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, reply.getUserAccountId(), reply.getPostId(), reply.getContent(), reply.getDate());
    }

    public void deleteReplyById(int replyId) {
        String sql = "DELETE FROM postreply WHERE id = ?";
        jdbcTemplate.update(sql, replyId);
    }


}