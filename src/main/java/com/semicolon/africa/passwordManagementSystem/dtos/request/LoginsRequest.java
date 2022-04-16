package com.semicolon.africa.passwordManagementSystem.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
//@AllArgsConstructor
//@RequiredArgsConstructor
@NoArgsConstructor
public class LoginsRequest {

    private String password;
    private String email;
}
