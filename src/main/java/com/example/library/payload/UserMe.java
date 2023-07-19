package com.example.library.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserMe {

    private String name;
    private String email;
    private String username;
}
