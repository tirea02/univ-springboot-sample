package com.net.sample.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginCredentials {
    private String username;
    private String password;


    //lombok 미설치시 다음과 같이 사용
//
//    // Default constructor
//    public LoginCredentials() {
//    }
//
//    // Constructor with fields
//    public LoginCredentials(String username, String password) {
//        this.username = username;
//        this.password = password;
//    }
//
//    // Getters and setters
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

}
