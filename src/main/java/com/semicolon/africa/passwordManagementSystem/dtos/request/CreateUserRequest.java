package com.semicolon.africa.passwordManagementSystem.dtos.request;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateUserRequest {
    private String email;
    private String password;

}
