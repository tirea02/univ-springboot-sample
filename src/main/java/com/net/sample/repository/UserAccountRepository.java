package com.net.sample.repository;

import com.net.sample.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserAccountRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean existsByUserId(String userId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM userAccount WHERE userId = ?",
                new Object[]{userId},
                Integer.class);
        return count != null && count > 0;
    }

    public UserAccount save(UserAccount userAccount) {
        jdbcTemplate.update(
                "INSERT INTO userAccount (userId, password, name, phone) VALUES (?, ?, ?, ?)",
                userAccount.getUserId(), userAccount.getPassword(), userAccount.getName(), userAccount.getPhone());
        return userAccount; // In real application, you might want to return the saved entity with ID
    }

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
