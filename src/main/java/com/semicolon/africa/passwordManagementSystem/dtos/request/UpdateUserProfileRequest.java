package com.semicolon.africa.passwordManagementSystem.dtos.request;

import lombok.Data;

@Data
public class UpdateUserProfileRequest {

    private String password;
    private String email;
}
