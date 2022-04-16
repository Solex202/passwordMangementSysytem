package com.semicolon.africa.passwordManagementSystem.dtos.request;


import lombok.Data;

@Data
public class SavePasswordRequest {

    private String url;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
