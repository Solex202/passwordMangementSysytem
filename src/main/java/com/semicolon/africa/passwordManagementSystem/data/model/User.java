package com.semicolon.africa.passwordManagementSystem.data.model;

import com.semicolon.africa.passwordManagementSystem.dtos.request.PasswordToSave;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;


@Data
public class User {
    @Id
    private String id;
    private String email;
    private String password;
    List<PasswordToSave> registeredPassword = new ArrayList<>();
//    private  String registeredPassword;
}
