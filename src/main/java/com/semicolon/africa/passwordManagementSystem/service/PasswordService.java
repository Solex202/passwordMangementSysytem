package com.semicolon.africa.passwordManagementSystem.service;

import com.semicolon.africa.passwordManagementSystem.data.model.User;
import com.semicolon.africa.passwordManagementSystem.dtos.request.*;
import com.semicolon.africa.passwordManagementSystem.dtos.response.AddPasswordResponse;
import com.semicolon.africa.passwordManagementSystem.dtos.response.CreateUserResponse;
import com.semicolon.africa.passwordManagementSystem.dtos.response.LoginResponse;
import com.semicolon.africa.passwordManagementSystem.dtos.response.SearchUrlResponse;

import java.util.List;

public interface PasswordService {
    CreateUserResponse createUser(CreateUserRequest userRequest);

    List<User> getAllUsers();

    LoginResponse login(LoginsRequest loginsRequest);

    AddPasswordResponse addPassword( AddPasswordRequest saveRequest);

    List<PasswordToSave> getListOfSavedPassword(String email);

    SearchUrlResponse searchUrl(SearchUrlRequest searchUrlRequest);

    void deleteAll();
}
