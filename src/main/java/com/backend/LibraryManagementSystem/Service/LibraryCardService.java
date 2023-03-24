package com.backend.LibraryManagementSystem.Service;

import com.backend.LibraryManagementSystem.DTO.BookDto;
import com.backend.LibraryManagementSystem.Entity.Book;
import com.backend.LibraryManagementSystem.Entity.LibraryCard;
import com.backend.LibraryManagementSystem.Repository.LibraryCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryCardService {

    @Autowired
    LibraryCardRepository libraryCardRepository;

    public List<BookDto> getALlBookByCardId(int cardId){
        LibraryCard card = libraryCardRepository.findById(cardId).get();
        List<Book> bookList = card.getBookList();
        List<BookDto> bookDtoList =new ArrayList<>();
        for(Book book: bookList){
            BookDto bookDto = BookDto.builder()
                    .price(book.getPrice())
                    .genre(book.getGenre())
                    .isIssued(book.isIssued())
                    .tittle(book.getTittle())
                    .build();
            bookDtoList.add(bookDto);
        }
        return bookDtoList;
    }
}
