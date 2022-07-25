package com.semicolon.africa.passwordManagementSystem.dtos.request;

import lombok.*;

@Data

@NoArgsConstructor
public class LoginsRequest {

    private String password;
    private String email;
}
