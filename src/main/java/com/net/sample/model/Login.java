package com.net.sample.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Login {
    private String userID;
    private String pwd;
    private String name;
    private String phone;

}
