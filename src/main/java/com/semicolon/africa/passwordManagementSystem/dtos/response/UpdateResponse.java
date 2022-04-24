package com.semicolon.africa.passwordManagementSystem.dtos.response;


import lombok.Data;

@Data
public class UpdateResponse {

    private String msg;
    private String url;
    private String name;
    private String username;
    private String password;
    private String email;
}
