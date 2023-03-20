package com.backend.LibraryManagementSystem.Controller;

import com.backend.LibraryManagementSystem.DTO.IssueBookRequestDto;
import com.backend.LibraryManagementSystem.DTO.TransactionBookResponseDto;
import com.backend.LibraryManagementSystem.DTO.ReturnBookRequestDto;
import com.backend.LibraryManagementSystem.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/issue")
    public ResponseEntity issueBook(@RequestBody IssueBookRequestDto issueBookRequestDto){
        TransactionBookResponseDto transactionBookResponseDto;
        try{
            transactionBookResponseDto = transactionService.issueBook(issueBookRequestDto);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(transactionBookResponseDto,HttpStatus.ACCEPTED);
    }
    @PostMapping("/return")
    public ResponseEntity returnBook(@RequestBody ReturnBookRequestDto returnBookRequestDto){
        TransactionBookResponseDto transactionBookResponseDto;
        try{
            transactionBookResponseDto = transactionService.returnBook(returnBookRequestDto);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(transactionBookResponseDto,HttpStatus.ACCEPTED);
    }
    @GetMapping("/get_all_txns_by_card")
    public List<String> getAllSuccessTxnsByCardId(@RequestParam("id") int cardId){
        return transactionService.getAllSuccessTxnsByCardId(cardId);
    }
}
