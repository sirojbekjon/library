package com.example.library.service;

import com.example.library.entity.FileUpload;
import com.example.library.repository.FileUploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileUploadService {

    @Autowired
    FileUploadRepository fileUploadRepository;

    public static final String UPLOAD_URL ="src/main/resources/static/upload";

    public ResponseEntity<?> upload(MultipartFile file) {
        if (file != null) {
            FileUpload fileUpload = new FileUpload();
            fileUpload.setOriginalName(file.getOriginalFilename());
            fileUpload.setContentType(file.getContentType());
            fileUpload.setSize(file.getSize());

            String originalName = file.getOriginalFilename();
            String[] split = originalName.split("\\.");
            String name = UUID.randomUUID().toString() + "." + split[split.length - 1];

            fileUpload.setName(name);
            FileUpload savedFile = fileUploadRepository.save(fileUpload);

            Path fileToSave = Paths.get(UPLOAD_URL, name);
            try (InputStream inputStream = file.getInputStream()) {
                Files.createDirectories(fileToSave.getParent());
                Files.copy(inputStream, fileToSave, StandardCopyOption.REPLACE_EXISTING);
                inputStream.close();
                return ResponseEntity.status(HttpStatus.OK).body(savedFile);
            } catch (IOException e) {
                // Handle the exception appropriately
                e.printStackTrace();
                throw new RuntimeException(e.getCause());
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file provided");
        }
    }

    public ResponseEntity<?> deleteById(Long id) {
        Optional<FileUpload> optionalFileUpload = fileUploadRepository.findById(id);
        if (optionalFileUpload.isPresent()) {
            FileUpload fileUpload = optionalFileUpload.get();
            Path path = Paths.get(UPLOAD_URL, fileUpload.getName());

            try {
                Files.delete(path);
                fileUploadRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body("File deleted");
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete the file");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }
    }

    public ResponseEntity<?> getFileUpload(Long id, HttpServletResponse response) throws IOException {
        Optional<FileUpload> optionalFileUpload = fileUploadRepository.findById(id);
        if (optionalFileUpload.isPresent()) {
            FileUpload fileUpload = optionalFileUpload.get();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileUpload.getOriginalName() + "\"");
            response.setContentType(fileUpload.getContentType());
            try (FileInputStream fileInputStream = new FileInputStream(UPLOAD_URL + "/" + fileUpload.getName())) {
                fileInputStream.close(); //hatolik berganda ko'rish zarur
                FileCopyUtils.copy(fileInputStream, response.getOutputStream());
            }
            return ResponseEntity.status(HttpStatus.OK).body("File retrieved");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }
    }

}
