package com.semicolon.africa.passwordManagementSystem.service;

import com.semicolon.africa.passwordManagementSystem.data.model.User;
import com.semicolon.africa.passwordManagementSystem.dtos.request.*;
import com.semicolon.africa.passwordManagementSystem.dtos.response.*;

import java.util.List;

public interface PasswordService {
    CreateUserResponse createUser(CreateUserRequest userRequest);

    List<User> getAllUsers();

    LoginResponse login(LoginsRequest loginsRequest);

    AddPasswordResponse addPassword( AddPasswordRequest saveRequest);

    List<PasswordToSave> getListOfSavedPassword(String email);

    SearchUrlResponse searchUrl(SearchUrlRequest searchUrlRequest);

    void deleteAll();

    void delete(int id, String email);


    UpdateResponse update(int id, UpdatePasswordRequest updateRequest, String email);
}
