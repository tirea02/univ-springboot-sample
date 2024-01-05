package com.net.sample.repository;

import com.net.sample.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Login getUserByUsername(String username) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM login WHERE userID = ?",
                new Object[]{username},
                (rs, rowNum) ->
                        new Login(
                                rs.getString("userID"),
                                rs.getString("pwd"),
                                rs.getString("name"),
                                rs.getString("phone")
                        )
        );
    }
}
