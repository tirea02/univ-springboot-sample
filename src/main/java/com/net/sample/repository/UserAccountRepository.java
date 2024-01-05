package com.net.sample.repository;

import com.net.sample.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserAccountRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserAccount getUserByUserId(String userId) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM userAccount WHERE userId = ?",
                new Object[]{userId},
                (rs, rowNum) ->
                        new UserAccount(
                                rs.getInt("id"),
                                rs.getString("userId"),
                                rs.getString("password"),
                                rs.getString("name"),
                                rs.getString("phone")
                        )
        );
    }
}
