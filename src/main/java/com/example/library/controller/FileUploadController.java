package com.example.library.controller;

import com.example.library.entity.User;
import com.example.library.log.Loggerr;
import com.example.library.security.CurrentUser;
import com.example.library.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/file")
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;


    @PostMapping("/upload")
    public HttpEntity<?> uploadFile(@RequestBody MultipartFile files, @CurrentUser User user) throws IOException {
        Loggerr.log();
        if (user.getRole().getName().equals("SUPERADMIN"))
        {return fileUploadService.upload(files);
        }else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not allowed");
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> dleteFile(@PathVariable Long id, @CurrentUser User user) throws IOException {
        Loggerr.log();
        if (user.getRole().getName().equals("SUPERADMIN")){
        return fileUploadService.deleteById(id);
        }else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not allowed");
    }

    @GetMapping("/download/{id}")
    public HttpEntity<?> getFileUpload(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Loggerr.log();
        return fileUploadService.getFileUpload(id, response);
    }
}
