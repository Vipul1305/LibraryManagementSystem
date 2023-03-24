package com.backend.LibraryManagementSystem.Controller;

import com.backend.LibraryManagementSystem.DTO.BookDto;
import com.backend.LibraryManagementSystem.Service.LibraryCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/librarycard")
public class LibraryCardController {

    @Autowired
    LibraryCardService libraryCardService;

    @GetMapping("/book_by_card")
    public List<BookDto> getALlBookByCardId(@RequestParam("id") int cardId){

        return libraryCardService.getALlBookByCardId(cardId);

    }
}
