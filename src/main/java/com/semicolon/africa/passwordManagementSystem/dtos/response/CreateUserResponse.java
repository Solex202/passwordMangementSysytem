package com.semicolon.africa.passwordManagementSystem.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponse {
    private String email;
    private String password;
    private String msg;
}
