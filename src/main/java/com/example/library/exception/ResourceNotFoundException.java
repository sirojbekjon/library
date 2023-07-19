package com.example.library.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@AllArgsConstructor
public class ResourceNotFoundException extends RuntimeException{
    private String resourceName; //LAVOZIM TABLE TOPILMADI
    private String resourceField; //NAME
    private Object  object; //USER,ADMIN,1,500....

}
