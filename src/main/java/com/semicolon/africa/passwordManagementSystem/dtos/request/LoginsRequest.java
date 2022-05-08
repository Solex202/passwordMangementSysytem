package com.semicolon.africa.passwordManagementSystem.dtos.request;


import lombok.*;

@Data
@Builder
//@AllArgsConstructor
//@RequiredArgsConstructor
@NoArgsConstructor
public class LoginsRequest {

    private String password;
    private String email;
}
