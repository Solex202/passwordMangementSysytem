package com.semicolon.africa.passwordManagementSystem.exception;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException(String message){
        super(message);
    }
}
