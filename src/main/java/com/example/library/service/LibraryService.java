package com.example.library.service;

import com.example.library.entity.Library;
import com.example.library.payload.ApiResponse;
import com.example.library.payload.LibDto;
import com.example.library.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryService {


    private final LibraryRepository libraryRepository;

    public ApiResponse addLib(LibDto libDto) {
        try {
            boolean existsByName = libraryRepository.existsByName(libDto.getName());
            if (!existsByName) {
                Library lib = new Library(libDto.getName());
                libraryRepository.save(lib);
                return new ApiResponse("saved", true);
            }
            return new ApiResponse("Failed", false);
        }
        catch (Exception e){
            e.getMessage();
        }
        return new ApiResponse("Enternal server Error",false);
    }

    public ApiResponse deleteLib(Long id) {
        try {
            libraryRepository.deleteById(id);
            return new ApiResponse("Deleted",true);
        }
        catch (Exception e){
            e.getMessage();
        }
        return new ApiResponse("library has not deleted",false);
    }

    public ApiResponse editLib(Long edit_id, LibDto libDto) {
        try {
            Optional<Library> optionalLibrary = libraryRepository.findById(edit_id);
            if (optionalLibrary.isPresent()){
                Library library = optionalLibrary.get();
                library.setName(libDto.getName());;
                libraryRepository.save(library);
                return new ApiResponse("edited", true);
            }
        }
        catch (Exception e){
            e.getMessage();
        }
        return new ApiResponse("library has not edited",false);
    }

    public HttpEntity<?> getAll() {
        try
        {
            return  ResponseEntity.status(200).body(libraryRepository.findAll());
        }
        catch (Exception e){
            e.getMessage();
        }
        return ResponseEntity.status(404).body("External server error");
    }

    public HttpEntity<?> getLibById(Long libId) {
        try {
            Optional<Library> optionalLibrary = libraryRepository.findById(libId);
            if (optionalLibrary.isPresent()) {
                return ResponseEntity.status(200).body(optionalLibrary.get());
            }
        }catch (Exception e){
            e.getMessage();
        }
        return ResponseEntity.status(404).body("library has not found");
    }
}
