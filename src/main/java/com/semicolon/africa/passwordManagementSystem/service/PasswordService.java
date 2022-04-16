package com.semicolon.africa.passwordManagementSystem.service;

import com.semicolon.africa.passwordManagementSystem.data.model.User;
import com.semicolon.africa.passwordManagementSystem.dtos.request.CreateUserRequest;
import com.semicolon.africa.passwordManagementSystem.dtos.request.LoginsRequest;
import com.semicolon.africa.passwordManagementSystem.dtos.response.CreateUserResponse;
import com.semicolon.africa.passwordManagementSystem.dtos.response.LoginResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface PasswordService {
    CreateUserResponse createUser(CreateUserRequest userRequest);

    List<User> getAllUsers();

    LoginResponse login(LoginsRequest loginsRequest);

//    List<User> user = new ArrayList<>();
}
