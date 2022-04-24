package com.semicolon.africa.passwordManagementSystem.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {

    private boolean isSuccessful;
    private String message;

}
