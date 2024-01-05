package com.net.sample.service;

import com.net.sample.model.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GuestService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Guest> getAllPosts() {
        return jdbcTemplate.query(
                "SELECT g_num, userID, g_name, g_title, g_content, g_wdate, g_hit, g_img_file FROM guest ORDER BY g_wdate DESC",
                (rs, rowNum) -> new Guest(
                        rs.getInt("g_num"),
                        rs.getString("userID"),
                        rs.getString("g_name"),
                        rs.getString("g_title"),
                        rs.getString("g_content"),
                        rs.getTimestamp("g_wdate").toLocalDateTime(),
                        rs.getInt("g_hit"),
                        rs.getString("g_img_file")
                )
        );
    }

    public Guest getPostById(int postId) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM guest WHERE g_num = ?",
                new Object[]{postId},
                (rs, rowNum) ->
                        new Guest(
                                rs.getInt("g_num"),
                                rs.getString("userID"),
                                rs.getString("g_name"),
                                rs.getString("g_title"),
                                rs.getString("g_content"),
                                rs.getTimestamp("g_wdate").toLocalDateTime(),
                                rs.getInt("g_hit"),
                                rs.getString("g_img_file")
                        )
        );
    }
}


