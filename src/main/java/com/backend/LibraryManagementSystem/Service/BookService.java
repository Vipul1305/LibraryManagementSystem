package com.backend.LibraryManagementSystem.Service;

import com.backend.LibraryManagementSystem.DTO.BookRequestDto;
import com.backend.LibraryManagementSystem.DTO.BookResponseDto;
import com.backend.LibraryManagementSystem.Entity.Author;
import com.backend.LibraryManagementSystem.Entity.Book;
import com.backend.LibraryManagementSystem.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    AuthorRepository authorRepository;

    public BookResponseDto addBook(BookRequestDto bookRequestDto) {
        //find author
        Author author = authorRepository.findById(bookRequestDto.getAuthorId()).get();

        Book book = new Book();
        book.setTittle(bookRequestDto.getTittle());
        book.setPrice(bookRequestDto.getPrice());
        book.setAuthor(author);

        //store in author list
        author.getBookList().add(book);
        authorRepository.save(author); // will save both book and author bcoz of bidirectional mapping

        //create a response also
        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setTittle(book.getTittle());
        bookResponseDto.setPrice(book.getPrice());

        return bookResponseDto;



//        Author author;
//        try {
//            int authorId = book.getAuthor().getId();
//            author = authorRepository.findById(authorId).get();
//        } catch (Exception e) {
//            return "Author Not Found";
//        }
//        List<Book> bookList = author.getBookList();
//        bookList.add(book);
//        author.setBookList(bookList);
//
//        authorRepository.save(author);
//        return "BooK Added";

    }
}
