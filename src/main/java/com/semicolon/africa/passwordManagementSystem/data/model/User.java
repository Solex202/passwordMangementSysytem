package com.semicolon.africa.passwordManagementSystem.data.model;

import com.semicolon.africa.passwordManagementSystem.dtos.request.PasswordToSave;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class User {
    private String email;
    private String password;
    List<PasswordToSave> passwordToSave = new ArrayList<>();
}
