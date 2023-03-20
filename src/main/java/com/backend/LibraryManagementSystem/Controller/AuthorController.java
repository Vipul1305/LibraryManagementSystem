package com.backend.LibraryManagementSystem.Controller;

import com.backend.LibraryManagementSystem.DTO.AuthorRequestDto;
import com.backend.LibraryManagementSystem.DTO.AuthorResponseDto;
import com.backend.LibraryManagementSystem.Entity.Author;
import com.backend.LibraryManagementSystem.Service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    AuthorService authorService;

    @PostMapping("/add")
    public AuthorResponseDto addAuthor(@RequestBody AuthorRequestDto authorRequestDto){
        return authorService.addAuthor(authorRequestDto);
    }
}
