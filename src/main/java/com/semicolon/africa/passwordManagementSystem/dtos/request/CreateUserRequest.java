package com.semicolon.africa.passwordManagementSystem.dtos.request;


import lombok.Data;

@Data
public class CreateUserRequest {

    private String email;
    private String password;

}
