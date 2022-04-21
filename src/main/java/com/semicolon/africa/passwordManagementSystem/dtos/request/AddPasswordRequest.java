package com.semicolon.africa.passwordManagementSystem.dtos.request;


import lombok.Data;

@Data
public class SavePasswordRequest {

    private String url;
    private String name;
    private String username;
    private String password;
}
