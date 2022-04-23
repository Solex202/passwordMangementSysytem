package com.semicolon.africa.passwordManagementSystem.dtos.request;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class PasswordToSave {
    private int id;
    private String url;
    private String name;
    private String username;
    private String password;
}
