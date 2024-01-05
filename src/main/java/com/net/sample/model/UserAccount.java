package com.net.sample.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAccount {
    private int id;
    private String userId;
    private String password;
    private String name;
    private String phone;

}
