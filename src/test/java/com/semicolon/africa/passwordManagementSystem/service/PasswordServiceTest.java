package com.semicolon.africa.passwordManagementSystem.service;

import com.semicolon.africa.passwordManagementSystem.data.repository.PasswordManagerRepo;
import com.semicolon.africa.passwordManagementSystem.dtos.request.CreateUserRequest;
import com.semicolon.africa.passwordManagementSystem.dtos.request.LoginsRequest;
import com.semicolon.africa.passwordManagementSystem.dtos.request.AddPasswordRequest;
import com.semicolon.africa.passwordManagementSystem.dtos.request.SearchUrlRequest;
import com.semicolon.africa.passwordManagementSystem.dtos.response.CreateUserResponse;
import com.semicolon.africa.passwordManagementSystem.dtos.response.LoginResponse;
import com.semicolon.africa.passwordManagementSystem.dtos.response.SearchUrlResponse;
import com.semicolon.africa.passwordManagementSystem.exception.CannotAddPasswordException;
import com.semicolon.africa.passwordManagementSystem.exception.InvalidPasswordException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PasswordServiceTest {

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private PasswordManagerRepo repo;

//    @BeforeEach
//    public void setup(){
//        passwordService = new PasswordServiceImpl(repo);
//    }

//    @Order(1)
    @Test
    public void testThatUserCanBeCreated(){
        //given
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setPassword("deoalaD1@$!*Dee");
        userRequest.setEmail("lota@gmail.com");

        passwordService.createUser(userRequest);

        assertThat(passwordService.getAllUsers().size(), is(1));
    }

//    @Order(2)
    @Test
    public void testThatCreationCanGetResponse(){
        //given
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setPassword("deoalaD1@$!*Dee");
        userRequest.setEmail("loita@gmail.com");

        CreateUserResponse response = passwordService.createUser(userRequest);
        assertThat(response.getMsg(),is("user created"));

    }

//    @Order(3)
    @Test
    public void testThatUserCanLogin(){
        //given
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setPassword("deoalaD9@$!*Dee");
        userRequest.setEmail("lotaD@gmail.com");
        passwordService.createUser(userRequest);

        LoginsRequest loginsRequest = new LoginsRequest();
        loginsRequest.setPassword("deoalaD9@$!*Dee");
        LoginResponse loginResponse = passwordService.login(loginsRequest);
        assertThat(loginResponse.getMsg(), is("login successful"));

    }

//    @Order(4)
    @Test
    public void testThatUserCanBeCreatedWhenPasswordIsGreaterThan8AndContainsChars(){
        //given
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setPassword("deoy");
        userRequest.setEmail("lota@gmail.com");

        assertThrows(InvalidPasswordException.class, ()->passwordService.createUser(userRequest));
    }


//    @Order(5)
    @Test
    public void testThatPasswordManagerCanAddPassword(){

        //given
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setPassword("deoalaD10@$!*Dee");
        userRequest.setEmail("lotaE@gmail.com");

        passwordService.createUser(userRequest);

        LoginsRequest loginsRequest = new LoginsRequest();
        loginsRequest.setPassword("deoalaD10@$!*Dee");
        LoginResponse loginResponse = passwordService.login(loginsRequest);
        assertThat(loginResponse.getMsg(), is("login successful"));

       AddPasswordRequest saveRequest = new AddPasswordRequest();
       saveRequest.setUrl("www.facebook.com");
       saveRequest.setUsername("solomon christian");
       saveRequest.setPassword("#solomon234");
       saveRequest.setName("facebook");
       saveRequest.setEmail("lotaE@gmail.com");

       passwordService.addPassword(saveRequest);

       assertThat(passwordService.getListOfSavedPassword(userRequest.getEmail()).size(), is(1));
    }

//    @Order(6)
    @Test
    public void testThatUserCanAddAnotherPassword() {
        //given
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setPassword("deoalaD1440@$!*Dee");
        userRequest.setEmail("lotachukwu@gmail.com");

        passwordService.createUser(userRequest);

        LoginsRequest loginsRequest = new LoginsRequest();
        loginsRequest.setPassword("deoalaD1440@$!*Dee");
        LoginResponse loginResponse = passwordService.login(loginsRequest);
        assertThat(loginResponse.getMsg(), is("login successful"));

        AddPasswordRequest saveRequest = new AddPasswordRequest();
        saveRequest.setUrl("www.facebook.com");
        saveRequest.setUsername("femi awolowo");
        saveRequest.setPassword("#*42winnerSer");
        saveRequest.setName("facebook");
        saveRequest.setEmail("lotachukwu@gmail.com");

        passwordService.addPassword(saveRequest);

        AddPasswordRequest request = new AddPasswordRequest();
        request.setUrl("www.instagram.com");
        request.setUsername("femz_man");
        request.setPassword("@Femifemo^41");
        request.setName("instagram");
        request.setEmail("lotachukwu@gmail.com");

        passwordService.addPassword(request);

        assertThat(passwordService.getListOfSavedPassword(userRequest.getEmail()).size(), is(2));
    }

//    @Order(7)
    @Test
    public void testThatUserCanSearchFor_A_Url(){
        //given
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setPassword("dejiDeji@1234");
        userRequest.setEmail("lotachukwu@gmail.com");

        passwordService.createUser(userRequest);

        LoginsRequest loginsRequest = new LoginsRequest();
        loginsRequest.setPassword("dejiDeji@1234");
        LoginResponse loginResponse = passwordService.login(loginsRequest);
        assertThat(loginResponse.getMsg(), is("login successful"));

        AddPasswordRequest saveRequest = new AddPasswordRequest();
        saveRequest.setUrl("www.facebook.com");
        saveRequest.setUsername("femi awolowo");
        saveRequest.setPassword("#*42winnerSer");
        saveRequest.setName("facebook");
        saveRequest.setEmail("lotachukwu@gmail.com");

        passwordService.addPassword(saveRequest);

        AddPasswordRequest request = new AddPasswordRequest();
        request.setUrl("www.instagram.com");
        request.setUsername("femz_man");
        request.setPassword("@Femifemo^41");
        request.setName("instagram");
        request.setEmail("lotachukwu@gmail.com");

        passwordService.addPassword(request);

        assertThat(passwordService.getListOfSavedPassword(userRequest.getEmail()).size(), is(2));

        SearchUrlRequest searchUrlRequest = new SearchUrlRequest();
        searchUrlRequest.setUrl("www.instagram.com");
        searchUrlRequest.setEmail("lotachukwu@gmail.com");

        SearchUrlResponse searchUrlResponse = passwordService.searchUrl(searchUrlRequest);
        assertThat(searchUrlResponse.getUsername(), is("femz_man"));
        assertThat(searchUrlResponse.getPassword(), is("@Femifemo^41"));
    }

//    @Order(8)
    @Test
    public void testThatPasswordManagerCannotAddPasswordWithoutLoggingIn(){

        //given
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setPassword("deoalaD10@$!*Dee");
        userRequest.setEmail("lotaE@gmail.com");

        passwordService.createUser(userRequest);

        AddPasswordRequest saveRequest = new AddPasswordRequest();
        saveRequest.setUrl("www.facebook.com");
        saveRequest.setUsername("solomon christian");
        saveRequest.setPassword("#solomon234");
        saveRequest.setName("facebook");
        saveRequest.setEmail("lotaE@gmail.com");

        assertThrows(CannotAddPasswordException.class, () ->passwordService.addPassword(saveRequest));
    }

    @AfterEach
    void tearDown(){

        passwordService.deleteAll();
//        repo.deleteAll();

    }


}