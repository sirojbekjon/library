package com.example.library.controller;


import com.example.library.log.Loggerr;
import com.example.library.payload.ApiResponse;
import com.example.library.payload.LibDto;
import com.example.library.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/library")
public class LibraryController {

    private final LibraryService libraryService;

    @PreAuthorize(value = "hasAuthority('ADD_ROLE')")
    @PostMapping("/add")
    public HttpEntity<?> addLibrary(@Valid @RequestBody LibDto libDto ){
        Loggerr.log();
        ApiResponse apiResponse = libraryService.addLib(libDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    @PreAuthorize(value = "hasAuthority('ADD_ROLE')")
    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> deleteLib(@PathVariable Long id){
        Loggerr.log();
        ApiResponse apiResponse = libraryService.deleteLib(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    @PreAuthorize(value = "hasAuthority('ADD_ROLE')")
    @PutMapping("/edit/{edit_id}")
    public HttpEntity<?> editLib(@PathVariable Long edit_id,@RequestBody LibDto libDto){
        Loggerr.log();
        ApiResponse apiResponse = libraryService.editLib(edit_id,libDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    @PreAuthorize(value = "hasAuthority('VIEW_BOOK')")
    @GetMapping("/view/{libId}")
    public HttpEntity<?> getLibById(@Valid @PathVariable Long libId){
        Loggerr.log();
        return libraryService.getLibById(libId);
    }



    @PreAuthorize(value = "hasAuthority('VIEW_BOOK')")
    @GetMapping("/view/all")
    public HttpEntity<?> getAllLib(){
        Loggerr.log();
        return libraryService.getAll();
    }

}
