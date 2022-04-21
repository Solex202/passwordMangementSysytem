package com.semicolon.africa.passwordManagementSystem;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan("com.semicolon.africa.passwordManagementSystem")
public class PasswordMain {
    public static void main(String[] args) {
        SpringApplication.run(PasswordMain.class,args);
    }

}