package com.net.sample.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginCredentials {
    private String username;
    private String password;

}
