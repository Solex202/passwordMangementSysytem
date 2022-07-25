package com.semicolon.africa.passwordManagementSystem.controllers;

import com.semicolon.africa.passwordManagementSystem.data.model.User;
import com.semicolon.africa.passwordManagementSystem.dtos.ApiResponse;
import com.semicolon.africa.passwordManagementSystem.dtos.request.*;
import com.semicolon.africa.passwordManagementSystem.exception.CannotAddPasswordException;
import com.semicolon.africa.passwordManagementSystem.exception.InvalidPasswordException;
import com.semicolon.africa.passwordManagementSystem.exception.UrlNotFoundException;
import com.semicolon.africa.passwordManagementSystem.exception.UserNotFoundException;
import com.semicolon.africa.passwordManagementSystem.service.PasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1/user")
public class PasswordController {

    @Autowired
    PasswordService passwordService;

    @PostMapping("/createUser")
    public ResponseEntity<?> response(@RequestBody CreateUserRequest request){
        log.info("request->{}", request);
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

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return passwordService.getAllUsers();
    }

    @DeleteMapping("/deleteUrl/{email}/{id}")
    public ResponseEntity<?> delete(@PathVariable String email, int id){

        return new ResponseEntity<>(passwordService.delete(email, id), HttpStatus.OK);
    }

    @PostMapping("/addPassword")
    public  ResponseEntity<?> addPassword(@RequestBody AddPasswordRequest request){
        try {
            return new ResponseEntity<>(passwordService.addPassword(request), HttpStatus.OK);
        }catch(CannotAddPasswordException ex){
            return  new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/searchUrl")
    public ResponseEntity<?> searchPassword(@RequestBody SearchUrlRequest request){
        try{
            return new ResponseEntity<>(passwordService.searchUrl(request), HttpStatus.OK);
        }catch (UrlNotFoundException ex){
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(path = "updateUrl/{email}/{id}")
        public ResponseEntity<?> updateUrl(@PathVariable  int id,@PathVariable String email, @RequestBody UpdatePasswordRequest request){
        return new ResponseEntity<>(passwordService.update(id,email, request), HttpStatus.OK);

    }

}
