package com.example.library.controller;

import com.example.library.log.Loggerr;
import com.example.library.payload.ApiResponse;
import com.example.library.payload.BookTypeDto;
import com.example.library.service.BookTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/type")
public class BooksTypeController {

    private final BookTypeService bookTypeService;


    @PreAuthorize(value = "hasAuthority('ADD_USER')")
    @PostMapping("/add")
    public ApiResponse addBookType(@Valid @RequestBody BookTypeDto bookTypeDto){
        Loggerr.log();
        return bookTypeService.addBookType(bookTypeDto);
    }


    @PreAuthorize(value = "hasAuthority('ADD_USER')")
    @PostMapping("/edit/{id}")
    public ApiResponse editBookType(@Valid @PathVariable Long id, @RequestBody BookTypeDto bookTypeDto){
        Loggerr.log();
        return bookTypeService.editBookType(id,bookTypeDto);
    }


    @PreAuthorize(value = "hasAuthority('VIEW_BOOK')")
    @GetMapping("/all")
    public HttpEntity<?> getBookType(){
        Loggerr.log();
        return bookTypeService.getAllBookType();
    }



    @PreAuthorize(value = "hasAuthority('ADD_USER')")
    @PostMapping("/delete/{id}")
    public ApiResponse deleteBookType(@Valid @PathVariable Long id){
        Loggerr.log();
        return bookTypeService.deleteById(id);
    }


}
