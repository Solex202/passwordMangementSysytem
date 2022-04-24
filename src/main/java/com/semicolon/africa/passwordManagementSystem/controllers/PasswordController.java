package com.semicolon.africa.passwordManagementSystem.controllers;


import com.semicolon.africa.passwordManagementSystem.data.model.User;
import com.semicolon.africa.passwordManagementSystem.dtos.ApiResponse;
import com.semicolon.africa.passwordManagementSystem.dtos.request.CreateUserRequest;
import com.semicolon.africa.passwordManagementSystem.dtos.request.LoginsRequest;
import com.semicolon.africa.passwordManagementSystem.exception.InvalidPasswordException;
import com.semicolon.africa.passwordManagementSystem.exception.UserNotFoundException;
import com.semicolon.africa.passwordManagementSystem.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("/login")
    public ResponseEntity<?> response(@RequestBody LoginsRequest request){
        try{

            return new ResponseEntity<>(passwordService.login(request), HttpStatus.OK);
        }catch(UserNotFoundException ex){
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getUsers")
    public List<User> getAllUsers(){
        return passwordService.getAllUsers();
    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> delete(@PathVariable int id, String email){
//
//            return new ResponseEntity<>(passwordService.delete(id, email), HttpStatus.OK);
//
//    }
}
