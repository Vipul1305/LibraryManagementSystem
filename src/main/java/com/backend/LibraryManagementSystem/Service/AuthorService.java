package com.backend.LibraryManagementSystem.Service;

import com.backend.LibraryManagementSystem.DTO.AuthorRequestDto;
import com.backend.LibraryManagementSystem.DTO.AuthorResponseDto;
import com.backend.LibraryManagementSystem.Entity.Author;
import com.backend.LibraryManagementSystem.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    public AuthorResponseDto addAuthor(AuthorRequestDto authorRequestDto){
        Author author = new Author();
        author.setName(authorRequestDto.getName());
        author.setAge(authorRequestDto.getAge());
        author.setMobNo(authorRequestDto.getMobNo());
        author.setEmail(authorRequestDto.getEmail());
        Author updatedAuthor = authorRepository.save(author);

        AuthorResponseDto authorResponseDto = new AuthorResponseDto();
        authorResponseDto.setId(updatedAuthor.getId());
        authorResponseDto.setName(updatedAuthor.getName());
        authorResponseDto.setEmail(updatedAuthor.getEmail());

        return authorResponseDto;
    }

}
