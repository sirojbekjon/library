package com.example.library.payload;

import com.example.library.entity.Department;
import com.example.library.entity.Managment;
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
    private String sureName;

    @NotNull(message = "LastName  bo'sh bo'lmasin")
    private String lastname;

    @NotNull(message = "username  bo'sh bo'lmasin")
    private String username;

    @NotNull(message = "telefon nomer  bo'sh bo'lmasin")
    private String phone;

    @NotNull(message = "Boshqarma nomi  bo'sh bo'lmasin")
    private Long managment;

    @NotNull(message = "Bo'lim nomi  bo'sh bo'lmasin")
    private Long department;


    @NotNull(message = "parol  bo'sh bo'lmasin")
    private String password;

    @NotNull(message = "parol takrori bo'sh bo'lmasin")
    private String prePassword;

}
