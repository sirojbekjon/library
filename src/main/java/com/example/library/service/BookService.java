package com.example.library.service;

import com.example.library.entity.Books;
import com.example.library.entity.BooksType;
import com.example.library.payload.ApiResponse;
import com.example.library.payload.BookDto;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BooksTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BooksTypeRepository booksTypeRepository;


    public ApiResponse addBook(BookDto bookDto) {
        try {
            Optional<BooksType> optionalBooksType = booksTypeRepository.findById(bookDto.getBookTypeId());
            if (optionalBooksType.isPresent()){
            Books book = new Books(
                    bookDto.getTitle(),
                    bookDto.getAuthor(),
                    bookDto.getYear(),
                    bookDto.getPages(),
                    optionalBooksType.get()
                    );
                bookRepository.save(book);
                return new ApiResponse("Book has saved",true);
            }
        }catch (Exception e){
            e.getMessage();
        }
        return new ApiResponse("Book has not saved",false);

    }

    public ApiResponse deleteById(Long id) {
        try{
            bookRepository.deleteById(id);
            Optional<Books> byId = bookRepository.findById(id);
            if (!byId.isPresent()){
                return new ApiResponse("book has deleted",true);
            }
        }
        catch (Exception e){
            e.getMessage();
        }
        return new ApiResponse("book has not deleted",false);
    }

    public ApiResponse editBook(BookDto bookDto, Long id) {
        try
        {
            Optional<Books> optionalBooks = bookRepository.findById(id);
            Optional<BooksType> optionalBooksType = booksTypeRepository.findById(bookDto.getBookTypeId());

            if (optionalBooks.isPresent() && optionalBooksType.isPresent()){
                Books books = optionalBooks.get();
                books.setTitle(bookDto.getTitle());
                books.setAuthor(bookDto.getAuthor());
                books.setYear(bookDto.getYear());
                books.setPages(bookDto.getPages());
                books.setBooksType(optionalBooksType.get());
                bookRepository.save(books);
                return new ApiResponse("book has edited",true);
            }
        }
        catch (Exception e){
            e.getMessage();
        }
        return new ApiResponse("book has not edited",false);
    }

    public HttpEntity<?> getAllBook() {
        try {
            List<Books> booksList = bookRepository.findAll();
            return ResponseEntity.status(200).body(booksList);
        }catch (Exception e){
            e.getMessage();
        }
        return ResponseEntity.status(404).body("Not Found");

    }

    public HttpEntity<?> getBookById(Long id) {
        try {
            Optional<Books> optionalBooks = bookRepository.findById(id);
            if (optionalBooks.isPresent()){
                Books book = optionalBooks.get();
                return ResponseEntity.status(200).body(book);
            }
        }
        catch (Exception e){
            e.getMessage();
        }
        return ResponseEntity.status(404).body("Not found");
    }
}
