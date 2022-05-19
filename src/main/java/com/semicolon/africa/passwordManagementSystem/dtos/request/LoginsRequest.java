package com.semicolon.africa.passwordManagementSystem.dtos.request;

import lombok.*;

@Data

@NoArgsConstructor
//@Builder
public class LoginsRequest {

    private String password;
    private String email;
}
