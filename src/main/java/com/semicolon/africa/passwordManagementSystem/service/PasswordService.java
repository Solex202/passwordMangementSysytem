package com.semicolon.africa.passwordManagementSystem.service;

import com.semicolon.africa.passwordManagementSystem.data.model.User;
import com.semicolon.africa.passwordManagementSystem.dtos.request.CreateUserRequest;
import com.semicolon.africa.passwordManagementSystem.dtos.response.CreateUserResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface PasswordService {
    CreateUserResponse createUser(CreateUserRequest userRequest);

    List<User> getAllUsers();

//    List<User> user = new ArrayList<>();
}
