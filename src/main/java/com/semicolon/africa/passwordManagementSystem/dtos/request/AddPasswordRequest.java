package com.semicolon.africa.passwordManagementSystem.dtos.request;


import lombok.Data;

@Data
public class AddPasswordRequest {

    private String url;
    private String name;
    private String username;
    private String password;
    private String email;
}
