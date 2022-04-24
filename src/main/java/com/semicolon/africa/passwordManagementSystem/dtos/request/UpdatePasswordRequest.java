package com.semicolon.africa.passwordManagementSystem.dtos.request;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatePasswordRequest {
    private int id;
    private String url;
    private String name;
    private String username;
    private String password;
    private String email;
}
