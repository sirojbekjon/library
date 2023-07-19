package com.example.library.controller;


import com.example.library.entity.User;
import com.example.library.log.Loggerr;
import com.example.library.payload.ApiResponse;
import com.example.library.payload.UserDto;
import com.example.library.payload.UserMe;
import com.example.library.repository.UserRepository;
import com.example.library.security.CurrentUser;
import com.example.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    private final UserService userService;

    @PreAuthorize(value = "hasAuthority('EDIT_ROLE')")
    @PutMapping("/edit")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto){
        Loggerr.log();
       ApiResponse apiResponse = userService.editUser(userDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_ROLE')")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@Valid @PathVariable Long userId){
        Loggerr.log();
        ApiResponse apiResponse = userService.deleteUser(userId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority('VIEW_ROLE')")
    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getUser(@Valid @PathVariable Long user_id){
        Loggerr.log();
        return userService.getUser(user_id);
    }

    @PreAuthorize(value = "hasAuthority('ADD_ROLE')")
    @GetMapping("/all")
    public HttpEntity<?> getAllUser(){
        Loggerr.log();
       return userService.getAllUsers();
    }

    @PreAuthorize(value = "hasAuthority('VIEW_BOOK')")
    @GetMapping("/userme")
    public HttpEntity<?> getUserMe(@Valid @CurrentUser User user){
        Loggerr.log();
        try {
            UserMe userMe = new UserMe(
                    user.getName(),
                    user.getUsername(),
                    user.getEmail()
            );
            return ResponseEntity.status(202).body(userMe);
        }catch (Exception e){
            e.getMessage();
        }
        return ResponseEntity.status(404).body("Not Found");
    }
}
