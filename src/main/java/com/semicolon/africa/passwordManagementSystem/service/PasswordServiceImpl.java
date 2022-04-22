package com.semicolon.africa.passwordManagementSystem.service;

import com.semicolon.africa.passwordManagementSystem.data.model.User;
import com.semicolon.africa.passwordManagementSystem.data.repository.PasswordManagerRepo;
import com.semicolon.africa.passwordManagementSystem.dtos.request.*;
import com.semicolon.africa.passwordManagementSystem.dtos.response.AddPasswordResponse;
import com.semicolon.africa.passwordManagementSystem.dtos.response.CreateUserResponse;
import com.semicolon.africa.passwordManagementSystem.dtos.response.LoginResponse;
import com.semicolon.africa.passwordManagementSystem.dtos.response.SearchUrlResponse;
import com.semicolon.africa.passwordManagementSystem.exception.CannotAddPasswordException;
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

    private boolean isLoggedIn = false;

//    public PasswordServiceImpl(@Autowired PasswordManagerRepo passwordManagerRepo){
//        this.passwordManagerRepo = passwordManagerRepo;
//
//    }

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
            isLoggedIn = true;
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
        if(userNotLoggedIn()) throw new CannotAddPasswordException("please log in");

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

    private boolean userNotLoggedIn() {
        return !isLoggedIn;
    }

    @Override
    public List<PasswordToSave> getListOfSavedPassword(String email) {
        User newUser = passwordManagerRepo.findByEmail(email);
        return newUser.getRegisteredPassword();
    }

    @Override
    public SearchUrlResponse searchUrl(SearchUrlRequest searchUrlRequest) {
        List<PasswordToSave> allPassword = getListOfSavedPassword(searchUrlRequest.getEmail());
        SearchUrlResponse response = new SearchUrlResponse();
       allPassword.forEach(password->{
           if (password.getUrl().equals(searchUrlRequest.getUrl())){
               response.setPassword(password.getPassword());
               response.setUsername(password.getUsername());
           }
       });
       return response;
    }

    @Override
    public void deleteAll() {
        passwordManagerRepo.deleteAll();
    }


}
