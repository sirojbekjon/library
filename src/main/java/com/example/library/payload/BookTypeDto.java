package com.example.library.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BookTypeDto {

    @NotNull
    private String name;

    @NotNull
    private Long libid;
}
