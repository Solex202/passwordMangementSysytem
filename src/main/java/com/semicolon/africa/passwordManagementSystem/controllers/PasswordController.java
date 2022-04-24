package com.semicolon.africa.passwordManagementSystem.controllers;


import com.semicolon.africa.passwordManagementSystem.dtos.ApiResponse;
import com.semicolon.africa.passwordManagementSystem.dtos.request.CreateUserRequest;
import com.semicolon.africa.passwordManagementSystem.exception.InvalidPasswordException;
import com.semicolon.africa.passwordManagementSystem.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class PasswordController {

    @Autowired
    PasswordService passwordService;

    @PostMapping("/createUser")
    public ResponseEntity<?> response(@RequestBody CreateUserRequest request){
        try{

        return new ResponseEntity<>(passwordService.createUser(request), HttpStatus.OK);
        }catch(InvalidPasswordException ex){
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
