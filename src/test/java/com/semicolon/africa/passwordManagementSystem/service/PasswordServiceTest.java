package com.semicolon.africa.passwordManagementSystem.service;

import com.semicolon.africa.passwordManagementSystem.dtos.request.CreateUserRequest;
import com.semicolon.africa.passwordManagementSystem.dtos.response.CreateUserResponse;
import com.semicolon.africa.passwordManagementSystem.exception.InvalidPasswordException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PasswordServiceTest {

    @Autowired
    private PasswordService passwordService;


    @Test
    public void testThatUserCanBeCreated(){
        //given
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setPassword("deoalaD1@$!*Dee");
        userRequest.setEmail("lota@gmail.com");

        passwordService.createUser(userRequest);

        assertThat(passwordService.getAllUsers().size(), is(1));
    }

    @Test
    public void testThatCreationCanGetResponse(){
        //given
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setPassword("deoalaD1@$!*Dee");
        userRequest.setEmail("lota@gmail.com");

        CreateUserResponse response = passwordService.createUser(userRequest);
        assertThat(response.getMsg(),is("user created"));

    }

    @Test
    public void testThatUserCanBeCreatedWhenPasswordIsGreaterThan8AndContainsChars(){
        //given
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setPassword("deoy");
        userRequest.setEmail("lota@gmail.com");

        assertThrows(InvalidPasswordException.class, ()->passwordService.createUser(userRequest));
    }

    @Test
    public void testThatPasswordManagerCanAddPassword(){

    }


}