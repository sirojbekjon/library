package com.example.library.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartmentDto {

    private String name;
    private Long managment;
}
