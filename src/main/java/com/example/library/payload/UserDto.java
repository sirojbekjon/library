package com.example.library.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull(message = "name  bo'sh bo'lmasin")
    private String name;

    @NotNull(message = "sureName  bo'sh bo'lmasin")
    private String username;

    @NotNull(message = "email  bo'sh bo'lmasin")
    private String email;

    @NotNull(message = "password  bo'sh bo'lmasin")
    private String password;

    @NotNull(message = "password bosh bo'lmasligi kerak")
    private String prePassword;

    @NotNull(message = "role bo'sh bo'lmasligi kerak")
    private Long role;

}
