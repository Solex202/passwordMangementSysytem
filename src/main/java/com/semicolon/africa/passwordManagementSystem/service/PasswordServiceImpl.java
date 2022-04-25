package com.semicolon.africa.passwordManagementSystem.service;

import com.semicolon.africa.passwordManagementSystem.data.model.User;
import com.semicolon.africa.passwordManagementSystem.data.repository.PasswordManagerRepo;
import com.semicolon.africa.passwordManagementSystem.dtos.request.*;
import com.semicolon.africa.passwordManagementSystem.dtos.response.*;
import com.semicolon.africa.passwordManagementSystem.exception.CannotAddPasswordException;
import com.semicolon.africa.passwordManagementSystem.exception.InvalidPasswordException;
import com.semicolon.africa.passwordManagementSystem.exception.UrlNotFoundException;
import com.semicolon.africa.passwordManagementSystem.exception.UserNotFoundException;
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
        else {
            throw new UserNotFoundException("please create an account");
        }
    }

    @Override
    public AddPasswordResponse addPassword(AddPasswordRequest saveRequest) {
            User newUser = passwordManagerRepo.findByEmail(saveRequest.getEmail());
        if(!newUser.isLoginStatus()) {
            throw new CannotAddPasswordException("please log in");
        }
            else {

            PasswordToSave passwordToSave = new PasswordToSave();
            passwordToSave.setId(getListOfSavedPassword(saveRequest.getEmail()).size() + 1);
            passwordToSave.setPassword(saveRequest.getPassword());
            passwordToSave.setName(saveRequest.getName());
            passwordToSave.setUsername(saveRequest.getUsername());
            passwordToSave.setUrl(saveRequest.getUrl());

            newUser.getRegisteredPassword().add(passwordToSave);

            log.info(passwordToSave.getId() +"========>");

            passwordManagerRepo.save(newUser);
            AddPasswordResponse response = new AddPasswordResponse();
            response.setMessage("password registered");
            return response;
        }
    }

    @Override
    public List<PasswordToSave> getListOfSavedPassword(String email) {
        User newUser = passwordManagerRepo.findByEmail(email);
        return newUser.getRegisteredPassword();
    }

    @Override
    public SearchUrlResponse searchUrl(SearchUrlRequest searchUrlRequest) {
       User user= passwordManagerRepo.findByEmail(searchUrlRequest.getEmail());

       List<PasswordToSave> allPassword = user.getRegisteredPassword();
        SearchUrlResponse response = new SearchUrlResponse();
       allPassword.forEach(password->{
           System.out.println(password.getUrl() +"=====>" + searchUrlRequest.getUrl()) ;
           if (password.getUrl().equals(searchUrlRequest.getUrl())){
               response.setPassword(password.getPassword());
               response.setUsername(password.getUsername());
           }
//           else{
//
//           throw new UrlNotFoundException("Not found");
//           }
       });
       return response;
    }

    @Override
    public void deleteAll() {
        passwordManagerRepo.deleteAll();
    }

    @Override
    public void delete(int id, String email) {
        User newUser = passwordManagerRepo.findByEmail(email);
        List <PasswordToSave> listOfPassword = newUser.getRegisteredPassword();

        ListIterator<PasswordToSave> passwords = listOfPassword.listIterator();
        while (passwords.hasNext()) {
            if(passwords.next().getId() == id){
                listOfPassword.remove(passwords.next());
                break;
            }
        }
        passwordManagerRepo.save(newUser);
//        if(listOfPassword.contains(listOfPassword.get(id))){
//            listOfPassword.remove(listOfPassword.get(id));
//            passwordManagerRepo.save(newUser);
//        }
    }

    @Override
    public UpdateResponse update(int id, UpdatePasswordRequest updateRequest, String email) {
        User newUser = passwordManagerRepo.findByEmail(email);
        List<PasswordToSave> listOfPassword = newUser.getRegisteredPassword();
        ListIterator<PasswordToSave> passwords = listOfPassword.listIterator();
        UpdateResponse response = new UpdateResponse();
        while(passwords.hasNext()){
            if(passwords.next().getId() == id){
                passwords.next().setUsername(updateRequest.getUsername());
                passwordManagerRepo.save(newUser);
//                 response.setUsername(updateRequest.getUsername());
                 response.setMsg("password updated");
            }
        }

        return response;
    }



//    @Override
//    public UpdateResponse update(UpdatePasswordRequest updateRequest) {
//        User newUser = passwordManagerRepo.findByEmail(updateRequest.getEmail());
//        List<PasswordToSave> listOfPassword = newUser.getRegisteredPassword();
//        log.info(listOfPassword.toString());
////        ListIterator<PasswordToSave> passwords = listOfPassword.listIterator();
////        while (passwords.hasNext()) {
////           if(passwords.next().getUsername().equals(updateRequest)){
////               listOfPassword.up
////           }
////        }
//        return null;
//    }


}
