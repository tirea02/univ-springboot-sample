package com.net.sample.service;

import com.net.sample.model.UserAccount;
import com.net.sample.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    public UserAccount createUserAccount(UserAccount userAccount) throws Exception {
        // Check if a user with the same userId already exists
        if (userAccountRepository.existsByUserId(userAccount.getUserId())) {
            // Handle the case where user already exists
            throw new Exception("A user with this userId already exists.");
        }

        // Save the new user account
        return userAccountRepository.save(userAccount);
    }

}
