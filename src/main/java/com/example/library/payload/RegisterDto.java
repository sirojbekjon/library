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

    @NotNull(message = "username  bo'sh bo'lmasin")
    private String username;

    @NotNull(message = "telefon nomer  bo'sh bo'lmasin")
    private String phone;

    private Long managment;

    private Long department;

    @NotNull(message = "parol  bo'sh bo'lmasin")
    private String password;
}
