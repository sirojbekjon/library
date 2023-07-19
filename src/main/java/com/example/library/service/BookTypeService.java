package com.example.library.service;

import com.example.library.entity.BooksType;
import com.example.library.entity.Library;
import com.example.library.payload.ApiResponse;
import com.example.library.payload.BookTypeDto;
import com.example.library.repository.BooksTypeRepository;
import com.example.library.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookTypeService {

    private final BooksTypeRepository booksTypeRepository;
    private final LibraryRepository libraryRepository;

    public ApiResponse addBookType(BookTypeDto bookTypeDto) {
        try{
            boolean byName = booksTypeRepository.existsByName(bookTypeDto.getName());
            Optional<Library> optionalLibrary = libraryRepository.findById(bookTypeDto.getLibid());
            if (!byName && optionalLibrary.isPresent()){
                BooksType booksType = new BooksType(
                        bookTypeDto.getName(),
                        optionalLibrary.get()
                );
                booksTypeRepository.save(booksType);
                return new ApiResponse("BookType has saved",true);
            }

        }
        catch (Exception e){
            e.getMessage();
        }
        return new ApiResponse("bookType has not saved",false);

    }

    public ApiResponse deleteById(Long id) {
        try {
            Optional<BooksType> byId = booksTypeRepository.findById(id);
            if (byId.isPresent()){
                booksTypeRepository.deleteById(id);
                return new ApiResponse("booktype has deleted",true);
            }
        }catch (Exception e){
            e.getMessage();
        }return new ApiResponse("bookType has not deleted",false) ;
            }

    public ApiResponse editBookType(Long id, BookTypeDto bookTypeDto) {
        try{
            Optional<BooksType> optionalBooksType = booksTypeRepository.findById(id);
            Optional<Library> optionalLibrary = libraryRepository.findById(bookTypeDto.getLibid());
            if (optionalBooksType.isPresent() && optionalLibrary.isPresent()){
                BooksType booksType = optionalBooksType.get();
                     booksType.setName(bookTypeDto.getName());
                     booksType.setLibrary(optionalLibrary.get());
                booksTypeRepository.save(booksType);
                return new ApiResponse("BookType has edited",true);
            }
        }
        catch (Exception e){
            e.getMessage();
        }
        return new ApiResponse("bookType has not edited",false);
    }

    public HttpEntity<?> getAllBookType() {
    try
    {   List<BooksType> booksTypeList = booksTypeRepository.findAll();
        return ResponseEntity.status(200).body(booksTypeList);
    }
    catch (Exception e){
        e.getMessage();
    }
    return ResponseEntity.status(409).body("Not Found");
    }
}
