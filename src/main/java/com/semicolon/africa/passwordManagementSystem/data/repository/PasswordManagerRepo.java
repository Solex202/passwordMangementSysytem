package com.semicolon.africa.passwordManagementSystem.data.repository;

import com.semicolon.africa.passwordManagementSystem.data.model.PasswordManager;
import com.semicolon.africa.passwordManagementSystem.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository
public interface PasswordManagerRepo extends MongoRepository<User, String> {
}
