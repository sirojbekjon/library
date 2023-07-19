package com.example.library.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {


    @NotNull(message = "email  bo'sh bo'lmasin")
    private String username;

    @NotNull(message = "parol  bo'sh bo'lmasin")
    private String password;


}
