package com.semicolon.africa.passwordManagementSystem.dtos.request;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequest {

    private String email;
    private String password;

}
