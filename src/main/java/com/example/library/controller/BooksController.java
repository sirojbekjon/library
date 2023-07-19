package com.example.library.controller;

import com.example.library.log.Loggerr;
import com.example.library.payload.ApiResponse;
import com.example.library.payload.BookDto;
import com.example.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BooksController {

    private final BookService bookService;


    @PreAuthorize(value = "hasAuthority('ADD_USER')")
    @PostMapping("/add")
    public ApiResponse addBook(@Valid @RequestBody BookDto bookDto){
        Loggerr.log();
        return bookService.addBook(bookDto);
    }

    @PreAuthorize(value = "hasAuthority('ADD_USER')")
    @PutMapping("/edit/{id}")
    public ApiResponse editBook(@Valid @PathVariable Long id, @RequestBody BookDto bookDto){
        Loggerr.log();
        return bookService.editBook(bookDto,id);
    }

    @PreAuthorize(value = "hasAuthority('VIEW_BOOK')")
    @GetMapping("/all")
    public HttpEntity<?> getAllBook(){
        Loggerr.log();
        return bookService.getAllBook();
    }

    @PreAuthorize(value = "hasAuthority('VIEW_BOOK')")
    @GetMapping("/view/{id}")
    public HttpEntity<?> getAllBook(@Valid @PathVariable Long id){
        Loggerr.log();
        return bookService.getBookById(id);
    }

    @PreAuthorize(value = "hasAuthority('ADD_USER')")
    @DeleteMapping("/delete/{id}")
    public ApiResponse deleteBook(@Valid @PathVariable Long id){
        Loggerr.log();
       return bookService.deleteById(id);
    }

}
