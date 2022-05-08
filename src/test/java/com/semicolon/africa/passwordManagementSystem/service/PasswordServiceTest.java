package com.semicolon.africa.passwordManagementSystem.service;

import com.semicolon.africa.passwordManagementSystem.data.repository.PasswordManagerRepo;
import com.semicolon.africa.passwordManagementSystem.dtos.request.*;
import com.semicolon.africa.passwordManagementSystem.dtos.response.*;
import com.semicolon.africa.passwordManagementSystem.exception.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PasswordServiceTest {

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private PasswordManagerRepo repo;

//    @BeforeEach
//    public void setup(){
//        passwordService = new PasswordServiceImpl(repo);
//    }

    @Test
    public void testThatUserCanBeCreated(){
        //given
        CreateUserRequest userRequest = CreateUserRequest.builder().password("deoalaD1@$!*Dee").email("lota@gmail.com").build();
//        userRequest.setPassword("deoalaD1@$!*Dee");
//        userRequest.setEmail("lota@gmail.com");
        passwordService.createUser(userRequest);

        assertThat(passwordService.getAllUsers().size(), is(1));
    }

    @Test
    public void testThatAnAlreadyExistingUserCannotCreateAnotherAccountWithSameEmailAndPassword_throwException(){
        //given
        CreateUserRequest userRequest = CreateUserRequest.builder().password("deoalaD1@$!*Dee").email("lota@gmail.com").build();
//        userRequest.setPassword("deoalaD1@$!*Dee");
//        userRequest.setEmail("lota@gmail.com");
        passwordService.createUser(userRequest);
        assertThat(passwordService.getAllUsers().size(), is(1));


        CreateUserRequest userRequest2 = CreateUserRequest.builder().password("deoalaD1@$!*Dee").email("lota@gmail.com").build();
//        userRequest2.setPassword("deoalaD1@$!*Dee");
//        userRequest2.setEmail("lota@gmail.com");

        assertThrows(UserAlreadyExistsException.class,()-> passwordService.createUser(userRequest2));
    }
//
    @Test
    public void testThatCreationCanGetResponse(){
        //given
        CreateUserRequest userRequest = CreateUserRequest.builder().password("deoalaD1@$!*Dee").email("loita@gmail.com").build();
//        userRequest.setPassword("deoalaD1@$!*Dee");
//        userRequest.setEmail("loita@gmail.com");

        CreateUserResponse response = passwordService.createUser(userRequest);
        assertThat(response.getMsg(),is("user created"));

    }
//
    @Test
    public void testThatUserCanLogin(){
        //given
        CreateUserRequest userRequest =CreateUserRequest.builder().password("deoalaD9@$!*Dee").email("lotaD@gmail.com").build();
//        userRequest.setPassword("deoalaD9@$!*Dee");
//        userRequest.setEmail("lotaD@gmail.com");
        passwordService.createUser(userRequest);

        LoginsRequest loginsRequest =  new LoginsRequest();
//                .builder().password("deoalaD9@$!*Dee").email("lotaD@gmail.com").build();
        loginsRequest.setPassword("deoalaD9@$!*Dee");
        loginsRequest.setEmail("lotaD@gmail.com");
        LoginResponse loginResponse = passwordService.login(loginsRequest);
        assertThat(loginResponse.getMsg(), is("login successful"));

    }
//
    @Test
    public void testThatUserCanBeCreatedWhenPasswordIsGreaterThan8AndContainsChars(){
        //given
        CreateUserRequest userRequest = CreateUserRequest.builder().password("deoy").email("lota@gmail.com").build();
//        userRequest.setPassword("deoy");
//        userRequest.setEmail("lota@gmail.com");

        assertThrows(InvalidPasswordException.class, ()->passwordService.createUser(userRequest));
    }
//
//
    @Test
    public void testThatPasswordManagerCanAddPassword(){

        //given
         CreateUserRequest userRequest = CreateUserRequest.builder().password("deoalaD10@$!*Dee").email("lotaE@gmail.com").build();
//        userRequest.setPassword("deoalaD10@$!*Dee");
//        userRequest.setEmail("lotaE@gmail.com");
        passwordService.createUser(userRequest);

        LoginsRequest loginsRequest = new LoginsRequest();
        loginsRequest.setPassword("deoalaD10@$!*Dee");
        loginsRequest.setEmail("lotaE@gmail.com");
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

    @Test
    public void testThatUserCanAddAnotherPassword() {
        //given
        CreateUserRequest userRequest = CreateUserRequest.builder().password("deoalaD1440@$!*Dee").email("lotachukwu@gmail.com").build();
//        userRequest.setPassword("deoalaD1440@$!*Dee");
//        userRequest.setEmail("lotachukwu@gmail.com");
        passwordService.createUser(userRequest);

        LoginsRequest loginsRequest = new LoginsRequest();
        loginsRequest.setPassword("deoalaD1440@$!*Dee");
        loginsRequest.setEmail("lotachukwu@gmail.com");
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
//
    @Test
    public void testThatUserCanSearchFor_A_Url(){
        //given
        CreateUserRequest userRequest = CreateUserRequest.builder().password("dejiDeji@1234").email("lotachukwuo@gmail.com").build();
//        userRequest.setPassword("dejiDeji@1234");
//        userRequest.setEmail("lotachukwuo@gmail.com");
        passwordService.createUser(userRequest);

        LoginsRequest loginsRequest = new LoginsRequest();
        loginsRequest.setPassword("dejiDeji@1234");
        loginsRequest.setEmail("lotachukwuo@gmail.com");
        LoginResponse loginResponse = passwordService.login(loginsRequest);
        assertThat(loginResponse.getMsg(), is("login successful"));

        AddPasswordRequest saveRequest = new AddPasswordRequest();
        saveRequest.setUrl("www.facebook.com");
        saveRequest.setUsername("femi awolowo");
        saveRequest.setPassword("#*42winnerSer");
        saveRequest.setName("facebook");
        saveRequest.setEmail("lotachukwuo@gmail.com");
        passwordService.addPassword(saveRequest);

        AddPasswordRequest request = new AddPasswordRequest();
        request.setUrl("www.instagram.com");
        request.setUsername("femz_man");
        request.setPassword("@Femifemo^41");
        request.setName("instagram");
        request.setEmail("lotachukwuo@gmail.com");
        passwordService.addPassword(request);

        assertThat(passwordService.getListOfSavedPassword(userRequest.getEmail()).size(), is(2));

        SearchUrlRequest searchUrlRequest = new SearchUrlRequest();
        searchUrlRequest.setUrl("www.instagram.com");
        searchUrlRequest.setEmail("lotachukwuo@gmail.com");

        SearchUrlResponse searchUrlResponse = passwordService.searchUrl(searchUrlRequest);
        assertThat(searchUrlResponse.getUsername(), is("femz_man"));
        assertThat(searchUrlResponse.getPassword(), is("@Femifemo^41"));
    }
//
    @Test
    public void testThatPasswordManagerCannotAddPasswordWithoutLoggingIn(){

        //given
        CreateUserRequest userRequest = CreateUserRequest.builder().password("deoalaD10@$!*Dee").email("lotaE@gmail.com").build();
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
//
    @Test
    public void testThatUnregisteredUserCannotLogin_throwException() {
        //given
        CreateUserRequest userRequest = CreateUserRequest.builder().password("tolu@342#LOkPE").email("tolu@gmail.com").build();
//        userRequest.setPassword("tolu@342#LOkPE");
//        userRequest.setEmail("tolu@gmail.com");

        LoginsRequest loginsRequest = new LoginsRequest();
        loginsRequest.setPassword("tolu@342#LOkPE");

        assertThrows(UserNotFoundException.class, ()-> passwordService.login(loginsRequest));
    }
//
//    @Test
//    public void testThatUserCanDeletePassword() {
//        //given
//        CreateUserRequest userRequest = new CreateUserRequest();
//        userRequest.setPassword("deoalaD1440@$!*Dee");
//        userRequest.setEmail("lotachukwu@gmail.com");
//        passwordService.createUser(userRequest);
//
//        LoginsRequest loginsRequest = new LoginsRequest();
//        loginsRequest.setPassword("deoalaD1440@$!*Dee");
//        loginsRequest.setEmail("lotachukwu@gmail.com");
//        LoginResponse loginResponse = passwordService.login(loginsRequest);
//        assertThat(loginResponse.getMsg(), is("login successful"));
//
//        AddPasswordRequest saveRequest = new AddPasswordRequest();
//        saveRequest.setUrl("www.facebook.com");
//        saveRequest.setUsername("femi awolowo");
//        saveRequest.setPassword("#*42winnerSer");
//        saveRequest.setName("facebook");
//        saveRequest.setEmail("lotachukwu@gmail.com");
//        passwordService.addPassword(saveRequest);
//
//        AddPasswordRequest request = new AddPasswordRequest();
//        request.setUrl("www.instagram.com");
//        request.setUsername("femz_man");
//        request.setPassword("@Femifemo^41");
//        request.setName("instagram");
//        request.setEmail("lotachukwu@gmail.com");
//        passwordService.addPassword(request);
//
//        assertThat(passwordService.getListOfSavedPassword(userRequest.getEmail()).size(), is(2));
//
////        passwordService.delete(1,request.getEmail());
//
//        DeletePasswordResponse deleteResponse =  passwordService.delete(request.getEmail(),1);
//
//        assertThat(passwordService.getListOfSavedPassword(userRequest.getEmail()).size(), is(1));
//    }
//
//    @Test
//    public void testThatUserCanUpdatePassword_username(){
//        //given
//        CreateUserRequest userRequest = new CreateUserRequest();
//        userRequest.setPassword("deoalaD1440@$!*Dee");
//        userRequest.setEmail("lotachukwu@gmail.com");
//        passwordService.createUser(userRequest);
//
//        LoginsRequest loginsRequest = new LoginsRequest();
//        loginsRequest.setPassword("deoalaD1440@$!*Dee");
//        loginsRequest.setEmail("lotachukwu@gmail.com");
//        LoginResponse loginResponse = passwordService.login(loginsRequest);
//        assertThat(loginResponse.getMsg(), is("login successful"));
//
//        AddPasswordRequest request = new AddPasswordRequest();
//        request.setUrl("www.instagram.com");
//        request.setUsername("femz_man");
//        request.setPassword("@Femifemo^41");
//        request.setEmail("lotachukwu@gmail.com");
//        passwordService.addPassword(request);
//
//        AddPasswordRequest saveRequest = new AddPasswordRequest();
//        saveRequest.setUrl("www.facebook.com");
//        saveRequest.setUsername("femi awolowo");
//        saveRequest.setPassword("#*42winnerSer");
//        saveRequest.setName("facebook");
//        saveRequest.setEmail("lotachukwu@gmail.com");
//        passwordService.addPassword(saveRequest);
//
//
//
//        assertThat(passwordService.getListOfSavedPassword(userRequest.getEmail()).size(), is(2));
//
//        UpdatePasswordRequest updateRequest = new UpdatePasswordRequest();
////        updateRequest.setEmail("lotachukwu@gmail.com");
//        updateRequest.setUsername("new username");
////        updateRequest.setPassword("new password");
//
//        UpdateResponse response = passwordService.update(1, updateRequest, request.getEmail());
//
//        SearchUrlRequest searchUrlRequest = new SearchUrlRequest();
//        searchUrlRequest.setUrl("www.instagram.com");
//        searchUrlRequest.setEmail("lotachukwu@gmail.com");
//
//        SearchUrlResponse searchUrlResponse = passwordService.searchUrl(searchUrlRequest);
//        assertThat(response.getMsg(),is("updated"));
//        assertThat(searchUrlResponse.getUsername(),is("new username"));
//        assertThat(searchUrlResponse.getPassword(),is("@Femifemo^41"));
//    }
//
//    @Test
//    public void testThatUserCanUpdatePassword_password(){
//        //given
//        CreateUserRequest userRequest = new CreateUserRequest();
//        userRequest.setPassword("deoalaD1440@$!*Dee");
//        userRequest.setEmail("lotachukwu@gmail.com");
//        passwordService.createUser(userRequest);
//
//        LoginsRequest loginsRequest = new LoginsRequest();
//        loginsRequest.setPassword("deoalaD1440@$!*Dee");
//        loginsRequest.setEmail("lotachukwu@gmail.com");
//        LoginResponse loginResponse = passwordService.login(loginsRequest);
//        assertThat(loginResponse.getMsg(), is("login successful"));
//
//        AddPasswordRequest saveRequest = new AddPasswordRequest();
//        saveRequest.setUrl("www.facebook.com");
//        saveRequest.setUsername("femi awolowo");
//        saveRequest.setPassword("#*42winnerSer");
//        saveRequest.setName("facebook");
//        saveRequest.setEmail("lotachukwu@gmail.com");
//        passwordService.addPassword(saveRequest);
//
//        AddPasswordRequest request = new AddPasswordRequest();
//        request.setUrl("www.instagram.com");
//        request.setUsername("femz_man");
//        request.setPassword("@Femifemo^41");
//        request.setEmail("lotachukwu@gmail.com");
//        passwordService.addPassword(request);
//
//        assertThat(passwordService.getListOfSavedPassword(userRequest.getEmail()).size(), is(2));
//
//        UpdatePasswordRequest updateRequest = new UpdatePasswordRequest();
////        updateRequest.setUsername("new username");
//        updateRequest.setPassword("new password");
//
//        UpdateResponse response = passwordService.update(2, updateRequest, request.getEmail());
//
//        SearchUrlRequest searchUrlRequest = new SearchUrlRequest();
//        searchUrlRequest.setUrl("www.instagram.com");
//        searchUrlRequest.setEmail("lotachukwu@gmail.com");
//
//        SearchUrlResponse searchUrlResponse = passwordService.searchUrl(searchUrlRequest);
//        assertThat(response.getMsg(),is("updated"));
//        assertThat(searchUrlResponse.getUsername(),is("femz_man"));
//        assertThat(searchUrlResponse.getPassword(),is("new password"));
//    }
//
//
//
//
//    @Test
//    public void testThatUrlAndEmailThatDoesntExistThrows_Exception(){
//        //given
//        CreateUserRequest userRequest = new CreateUserRequest();
//        userRequest.setPassword("mmesomaGlo!@2343");
//        userRequest.setEmail("mmesoma@gmail.com");
//        passwordService.createUser(userRequest);
//
//        LoginsRequest loginsRequest = new LoginsRequest();
//        loginsRequest.setEmail("mmesoma@gmail.com");
//        loginsRequest.setPassword("mmesomaGlo!@2343");
//        LoginResponse loginResponse = passwordService.login(loginsRequest);
//        assertThat(loginResponse.getMsg(), is("login successful"));
//
//        AddPasswordRequest saveRequest = new AddPasswordRequest();
//        saveRequest.setUrl("www.twitter.com");
//        saveRequest.setUsername("mmesobby");
//        saveRequest.setPassword("#*42winnerServe");
//        saveRequest.setName("facebook");
//        saveRequest.setEmail("mmesoma@gmail.com");
//        passwordService.addPassword(saveRequest);
//
//        AddPasswordRequest request = new AddPasswordRequest();
//        request.setUrl("www.heroku.com");
//        request.setUsername("mmeso");
//        request.setPassword("mmeso@Gee!@12");
//        request.setName("instagram");
//        request.setEmail("mmesoma@gmail.com");
//        passwordService.addPassword(request);
//
//        assertThat(passwordService.getListOfSavedPassword(userRequest.getEmail()).size(), is(2));
//
//        SearchUrlRequest searchUrlRequest = new SearchUrlRequest();
//        searchUrlRequest.setUrl("www.juno.com");
//        searchUrlRequest.setEmail("mmesoma@gmail.com");
//
//        assertThrows(UrlNotFoundException.class, ()->passwordService.searchUrl(searchUrlRequest));
//    }

    @AfterEach
    void tearDown(){

        passwordService.deleteAll();

    }


}