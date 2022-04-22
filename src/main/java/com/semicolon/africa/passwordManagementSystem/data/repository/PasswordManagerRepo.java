package com.semicolon.africa.passwordManagementSystem.data.repository;

import com.semicolon.africa.passwordManagementSystem.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PasswordManagerRepo extends MongoRepository<User, String> {
    User findByPassword(String password);

    User findByEmail(String email);
}
