package com.semicolon.africa.passwordManagementSystem.service;

import com.semicolon.africa.passwordManagementSystem.data.model.User;
import com.semicolon.africa.passwordManagementSystem.data.repository.PasswordManagerRepo;
import com.semicolon.africa.passwordManagementSystem.dtos.request.*;
import com.semicolon.africa.passwordManagementSystem.dtos.response.*;
import com.semicolon.africa.passwordManagementSystem.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ListIterator;

@Service
@Slf4j
public class PasswordServiceImpl implements PasswordService{

    @Autowired
    private PasswordManagerRepo passwordManagerRepo;

    @Override
    public CreateUserResponse createUser(CreateUserRequest userRequest) {
        System.out.println(userRequest.getEmail());
        if(userAlreadyExist(userRequest.getEmail())) throw new UserAlreadyExistsException("user with email" + userRequest.getEmail() + " already exist, create another account");
        if(!passwordIsValid(userRequest.getPassword())) throw new InvalidPasswordException("invalid password");

        User newUser = new User();
        newUser.setPassword(userRequest.getPassword());
        log.info("from here ----->{}", newUser.getPassword());
        newUser.setEmail(userRequest.getEmail());

        User savedUser = passwordManagerRepo.save(newUser);
        log.info(String.valueOf(newUser));

        CreateUserResponse response = new CreateUserResponse();
        response.setPassword(savedUser.getPassword());
        response.setEmail(savedUser.getEmail());
        response.setMsg("user created");
        return response;
    }

    private boolean userAlreadyExist(String email) {
        log.info("user-->{}", passwordManagerRepo.findByEmail(email));
        return  passwordManagerRepo.findByEmail(email) != null;
    }

    private boolean passwordIsValid(String password) {
       String isValid = "^(?=.*[0-9])"
               + "(?=.*[a-z])(?=.*[A-Z])"
               + "(?=.*[@#$%^&+=])"
               + "(?=\\S+$).{8,20}$";

       return password.matches(isValid);
    }

    @Override
    public List<User> getAllUsers() {
        return passwordManagerRepo.findAll();
    }

    @Override
    public LoginResponse login(LoginsRequest loginsRequest) {
        User newUser = passwordManagerRepo.findByEmail(loginsRequest.getEmail());
        if(newUser != null){
            newUser.setLoginStatus(true);
            passwordManagerRepo.save(newUser);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setMsg("login successful");
            return loginResponse;
        }

            throw new UserNotFoundException("please create an account to log in");
    }

    @Override
    public AddPasswordResponse addPassword(AddPasswordRequest saveRequest) {
            User newUser = passwordManagerRepo.findByEmail(saveRequest.getEmail());
            if(!newUser.isLoginStatus()) {
                throw new CannotAddPasswordException("oops! Cannot add password,please log in");
            }

            PasswordToSave passwordToBeSaved = new PasswordToSave();
            passwordToBeSaved.setId(getListOfSavedPassword(saveRequest.getEmail()).size() + 1);
            passwordToBeSaved.setPassword(saveRequest.getPassword());
            passwordToBeSaved.setName(saveRequest.getName());
            passwordToBeSaved.setUsername(saveRequest.getUsername());
            passwordToBeSaved.setUrl(saveRequest.getUrl());

            newUser.getRegisteredPassword().add(passwordToBeSaved);

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

//    @Override
//    public SearchUrlResponse searchUrl(SearchUrlRequest searchUrlRequest) {
//       User user= passwordManagerRepo.findByEmail(searchUrlRequest.getEmail());
//
//       List<PasswordToSave> allPassword = getListOfSavedPassword(searchUrlRequest.getEmail());
//       SearchUrlResponse response = new SearchUrlResponse();
//
//       allPassword.forEach(password->{
//               System.out.println(searchUrlRequest.getUrl() + "----> " + password.getUrl());
//           if(password.getUrl().equals(searchUrlRequest.getUrl())){
//               response.setPassword(password.getPassword());
//               response.setUsername(password.getUsername());
//           } else throw new UrlNotFoundException("Not found");
//
//       });
//       return response;
//    }

    @Override
    public SearchUrlResponse searchUrl(SearchUrlRequest searchUrlRequest) {

        List<PasswordToSave> allPassword = getListOfSavedPassword(searchUrlRequest.getEmail());
        SearchUrlResponse response = new SearchUrlResponse();

        final PasswordToSave[] passwordToSave = new PasswordToSave[1];

        allPassword.forEach(password->{
            if(password.getUrl().equals(searchUrlRequest.getUrl())){
                passwordToSave[0] =password;
            }
        });
        if (passwordToSave[0] == null){
            throw new UrlNotFoundException("url Not found");
        }
        response.setPassword(passwordToSave[0].getPassword());
        response.setUsername(passwordToSave[0].getUsername());
        return response;
    }

    @Override
    public void deleteAll() {
        passwordManagerRepo.deleteAll();
    }

    @Override
    public DeletePasswordResponse delete(String email, int id) {
        User newUser = passwordManagerRepo.findByEmail(email);
        List <PasswordToSave> listOfPassword = newUser.getRegisteredPassword();
        DeletePasswordResponse response = new DeletePasswordResponse();


        ListIterator<PasswordToSave> passwords = listOfPassword.listIterator();
        while (passwords.hasNext()) {
            if(passwords.next().getId() == id){
                listOfPassword.remove(passwords.next());
                response.setMsg("password deleted");
                break;
            }
        }
        passwordManagerRepo.save(newUser);

        return response;
    }

    @Override
    public UpdateResponse update(int id, String email, UpdatePasswordRequest updateRequest) {
        User newUser = passwordManagerRepo.findByEmail(email);
        List<PasswordToSave> listOfPassword = newUser.getRegisteredPassword();
        UpdateResponse response = new UpdateResponse();
       listOfPassword.forEach((password)->{
           if(password.getId() == id){

              if(updateRequest.getUsername() != null) password.setUsername(updateRequest.getUsername());
              if(updateRequest.getPassword() != null) password.setPassword(updateRequest.getPassword());
               response.setMsg("Updated");
               response.setUsername(password.getUsername());
               response.setUrl(password.getUrl());
               response.setName(password.getName());
               response.setPassword(password.getPassword());

               passwordManagerRepo.save(newUser);
           }
       });

        return response;
    }
    //    @Test
//    void testThatUserCanEditPassword() {
//        //given
//        AddUserRequest request = AddUserRequest
//                .builder()
//                .firstName("adeola")
//                .lastName("oladeji")
//                .email("adeolaoladeji@gmail.com")
//                .password("deedeji123")
//                .confirmPassword("deedeji123")
//                .gender(Gender.FEMALE)
//                .build();
//        //when
//        userService.createUser(request);
//
//        //given
//        AddUserRequest request2 = AddUserRequest
//                .builder()
//                .firstName("mercy")
//                .lastName("chioma")
//                .email("mercy@gmail.com")
//                .password("mercySaidNo")
//                .confirmPassword("mercySaidNo")
//                .gender(Gender.FEMALE)
//                .build();
//
//        userService.createUser(request2);
//
//        assertThat(userService.getAllUser().size(), is(2));
//
//        UpdateProfileRequest updateRequest = new UpdateProfileRequest();
//        updateRequest.setPassword("mercy1234");
//
//        String response = userService.updateUser(request2.getEmail(), updateRequest);
//        System.out.println(userService.getAllUser());
//
//        FindUserResponse response2 = userService.findUser(request2.getEmail());
//
//        //assert
//        assertThat(response, is("profile updated"));
//        assertThat(response2.getFirstName(),is("mercy"));
//        assertThat(response2.getLastName(),is("chioma"));
//        assertThat(response2.getPassword(),is("mercy1234"));
//        assertThat(response2.getEmail(),is("mercy@gmail.com"));
//    }

}
