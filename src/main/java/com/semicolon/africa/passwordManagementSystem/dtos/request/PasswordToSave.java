package com.semicolon.africa.passwordManagementSystem.dtos.request;


import lombok.Data;

@Data
public class PasswordToSave {
    private String url;
    private String name;
    private String username;
    private String password;
}
