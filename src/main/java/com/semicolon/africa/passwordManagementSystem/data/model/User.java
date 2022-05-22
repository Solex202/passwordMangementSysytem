package com.semicolon.africa.passwordManagementSystem.data.model;

import com.semicolon.africa.passwordManagementSystem.dtos.request.PasswordToSave;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;
    private String email;
    private String password;
    List<PasswordToSave> registeredPassword = new ArrayList<>();

    private boolean loginStatus ;
}
