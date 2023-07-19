package com.example.library.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    @NotNull(message = "name  bo'sh bo'lmasin")
    private String name;

    @NotNull(message = "sureName  bo'sh bo'lmasin")
    private String username;

    @NotNull(message = "email  bo'sh bo'lmasin")
    private String email;

    @NotNull(message = "parol  bo'sh bo'lmasin")
    private String password;

    @NotNull(message = "parol takrori bo'sh bo'lmasin")
    private String prePassword;

}
