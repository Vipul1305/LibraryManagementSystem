package com.backend.LibraryManagementSystem.Controller;

import com.backend.LibraryManagementSystem.DTO.BookDto;
import com.backend.LibraryManagementSystem.DTO.BookRequestDto;
import com.backend.LibraryManagementSystem.DTO.BookResponseDto;
import com.backend.LibraryManagementSystem.Entity.Book;
import com.backend.LibraryManagementSystem.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/add")
    public BookResponseDto addBook(@RequestBody BookRequestDto bookRequestDto){
        return bookService.addBook(bookRequestDto);
    }
}
