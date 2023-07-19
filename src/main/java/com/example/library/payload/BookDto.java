package com.example.library.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BookDto {

    @NotNull
    private String title;

    private String author;

    private Integer year;

    private Integer pages;

    @NotNull
    private Long bookTypeId;


}
