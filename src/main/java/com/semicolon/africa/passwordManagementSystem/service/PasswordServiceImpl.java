package com.semicolon.africa.passwordManagementSystem.service;


import com.semicolon.africa.passwordManagementSystem.data.model.User;
import com.semicolon.africa.passwordManagementSystem.data.repository.PasswordManagerRepo;
import com.semicolon.africa.passwordManagementSystem.dtos.request.*;
import com.semicolon.africa.passwordManagementSystem.dtos.response.AddPasswordResponse;
import com.semicolon.africa.passwordManagementSystem.dtos.response.CreateUserResponse;
import com.semicolon.africa.passwordManagementSystem.dtos.response.LoginResponse;
import com.semicolon.africa.passwordManagementSystem.dtos.response.SearchUrlResponse;
import com.semicolon.africa.passwordManagementSystem.exception.InvalidPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class PasswordServiceImpl implements PasswordService{

    @Autowired
    private PasswordManagerRepo passwordManagerRepo;
    @Override
    public CreateUserResponse createUser(CreateUserRequest userRequest) {
        if(!passwordIsInvalid(userRequest.getPassword())) throw new InvalidPasswordException("invalid password");

        User newUser = new User();
        newUser.setPassword(userRequest.getPassword());
        newUser.setEmail(userRequest.getEmail());

        passwordManagerRepo.save(newUser);
        log.info(String.valueOf(newUser));

        CreateUserResponse response = new CreateUserResponse();
        response.setMsg("user created");
        return response;
    }

    private boolean passwordIsInvalid(String password) {
       String isValid = "^(?=.*[0-9])"
               + "(?=.*[a-z])(?=.*[A-Z])"
               + "(?=.*[@#$%^&+=])"
               + "(?=\\S+$).{12,20}$";

       return password.length() >= 12 && password.matches(isValid);
    }


    @Override
    public List<User> getAllUsers() {
        return passwordManagerRepo.findAll();
    }

    @Override
    public LoginResponse login(LoginsRequest loginsRequest) {
        User newUser = passwordManagerRepo.findByPassword(loginsRequest.getPassword());
        if(newUser != null){
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMsg("login successful");
        return loginResponse;

        }
        else {
            return null;
        }
    }

    @Override
    public AddPasswordResponse addPassword(AddPasswordRequest saveRequest) {
        PasswordToSave passwordToSave = new PasswordToSave();
        passwordToSave.setPassword(saveRequest.getPassword());
        passwordToSave.setName(saveRequest.getName());
        passwordToSave.setUsername(saveRequest.getUsername());
        passwordToSave.setUrl(saveRequest.getUrl());

        User newUser = passwordManagerRepo.findByEmail(saveRequest.getEmail());
        newUser.getRegisteredPassword().add(passwordToSave);

        passwordManagerRepo.save(newUser);

        AddPasswordResponse response = new AddPasswordResponse();
        response.setMessage("password registered");

        return response;
    }

    @Override
    public List<PasswordToSave> getListOfSavedPassword(String email) {
        User newUser = passwordManagerRepo.findByEmail(email);

        return newUser.getRegisteredPassword();
    }

    @Override
    public SearchUrlResponse searchUrl(SearchUrlRequest searchUrlRequest) {

        User newUser = passwordManagerRepo.findByEmail(searchUrlRequest.getEmail());
        List<PasswordToSave> allPassword = getListOfSavedPassword(searchUrlRequest.getEmail());
        SearchUrlResponse response = new SearchUrlResponse();
        for (int password = 0; password < allPassword.size(); password++) {
//            if ()

        }


        return null;
    }

//    @Override
//    public SearchUrlResponse searchUrl(String email, SearchUrlRequest searchUrlRequest) {
//
//        User newUser = passwordManagerRepo.findByEmail(email);
//        if(searchUrlRequest != null){
//            SearchUrlResponse response = new SearchUrlResponse();
//            response.setPassword(newUser.getPassword());
//            response.setEmail(newUser.getEmail());
//            return response;
//        }
//        return null;
//    }
}
