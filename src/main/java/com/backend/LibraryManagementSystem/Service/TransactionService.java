package com.backend.LibraryManagementSystem.Service;

import com.backend.LibraryManagementSystem.DTO.IssueBookRequestDto;
import com.backend.LibraryManagementSystem.DTO.TransactionBookResponseDto;
import com.backend.LibraryManagementSystem.DTO.ReturnBookRequestDto;
import com.backend.LibraryManagementSystem.Entity.Book;
import com.backend.LibraryManagementSystem.Entity.LibraryCard;
import com.backend.LibraryManagementSystem.Entity.Transaction;
import com.backend.LibraryManagementSystem.Enum.CardStatus;
import com.backend.LibraryManagementSystem.Enum.IssueOrReturnOperation;
import com.backend.LibraryManagementSystem.Enum.TransactionStatus;
import com.backend.LibraryManagementSystem.Repository.BookRepository;
import com.backend.LibraryManagementSystem.Repository.LibraryCardRepository;
import com.backend.LibraryManagementSystem.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    LibraryCardRepository libraryCardRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    private JavaMailSender emailSender;
    public TransactionBookResponseDto issueBook(IssueBookRequestDto issueBookRequestDto) throws Exception{

        // Create Transaction Object
        Transaction transaction = new Transaction();
        transaction.setTranstionNumber(String.valueOf(UUID.randomUUID()));
        transaction.setIssueOrReturnOperation(IssueOrReturnOperation.ISSUE);

        //check if card is valid or not
        LibraryCard libraryCard;
        try{
            libraryCard = libraryCardRepository.findById(issueBookRequestDto.getCardId()).get();
        }catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid Card ID");
            transactionRepository.save(transaction);
            throw new Exception("Invalid Card ID");
        }
        //check if book is present or not
        Book book;
        try {
            book = bookRepository.findById(issueBookRequestDto.getBookId()).get();
        }catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid Book ID");
            transactionRepository.save(transaction);
            throw new Exception("Invalid Book ID");
        }
        // both and card and book are valid
        transaction.setBook(book);
        transaction.setCard(libraryCard);


        if (libraryCard.getStatus()!= CardStatus.ACTIVATED){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Library Card is Not Active");
            transactionRepository.save(transaction);
            throw new Exception("Library Card is Not Active");
        }
        if (book.isIssued()){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Book Already Issued");
            transactionRepository.save(transaction);
            throw new Exception("Book Already Issued");
        }

        // I can issue the book
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setMessage("Book Issued");

        book.setIssued(true);
        book.setCard(libraryCard);
        book.getTransactionList().add(transaction);

        libraryCard.getTransactionList().add(transaction);
        libraryCard.getBookList().add(book);



        libraryCardRepository.save(libraryCard);// will save book and tranaction also

        // Prepare Response Dto
        TransactionBookResponseDto transactionBookResponseDto = new TransactionBookResponseDto();
        transactionBookResponseDto.setTranstionNumber(transaction.getTranstionNumber());
        transactionBookResponseDto.setBookName(book.getTittle());
        transactionBookResponseDto.setTransactionStatus(transaction.getTransactionStatus());
        transactionBookResponseDto.setMassage(transaction.getMessage());


        //sending email with attachment;
        String text = "Congrats !! " + libraryCard.getStudent().getName() + ".\nYou have Successfully returned "+book.getTittle()+" book.";

        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("vipulsingh.cg@gmail.com");
        helper.setTo(libraryCard.getStudent().getEmail());
        helper.setSubject("Issued Book Notification");
        helper.setText(text);

        FileSystemResource file
                = new FileSystemResource(new File("src/main/resources/image/Aap.jpg"));
        helper.addAttachment("Invoice", file);

        emailSender.send(message);


        return transactionBookResponseDto;
    }
    public TransactionBookResponseDto returnBook(ReturnBookRequestDto returnBookRequestDto) throws Exception{
        //transection
        Transaction transaction = new Transaction();
        transaction.setTranstionNumber(String.valueOf(UUID.randomUUID()));
        transaction.setIssueOrReturnOperation(IssueOrReturnOperation.RETURN);

        Book book;
        try {
            book = bookRepository.findById(returnBookRequestDto.getBookId()).get();
        }catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid Book ID");
            transactionRepository.save(transaction);
            throw new Exception("Invalid Book ID");
        }
        if (!book.isIssued()){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Book Never Issued");
            transactionRepository.save(transaction);
            throw new Exception("Book Never Issued");
        }

        LibraryCard card = book.getCard();
        //book is valid
        book.setIssued(false);
        book.setCard(null);

        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setMessage("Book Returned");
        transaction.setBook(book);
        transaction.setCard(card);

        //book.getTransactionList().add(transaction); //get save two time in transction
        card.getTransactionList().add(transaction);
        card.getBookList().remove(book);

        libraryCardRepository.save(card);

        //response

        TransactionBookResponseDto transactionBookResponseDto = new TransactionBookResponseDto();
        transactionBookResponseDto.setTranstionNumber(transaction.getTranstionNumber());
        transactionBookResponseDto.setBookName(book.getTittle());
        transactionBookResponseDto.setTransactionStatus(transaction.getTransactionStatus());
        transactionBookResponseDto.setMassage(transaction.getMessage());

        //sending email message
        String text = "Congrats !! " + card.getStudent().getName() + ".\nYou have Successfully returned "+book.getTittle()+" book.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("vipulsingh.cg@gmail.com");
        message.setTo(card.getStudent().getEmail());
        message.setSubject("Return Book Notification");
        message.setText(text);
        emailSender.send(message);

        return transactionBookResponseDto;
    }
    public List<String> getAllSuccessTxnsByCardId(int cardId){
        List<Transaction> transactionList = transactionRepository.getAllSuccessfullTxnsWithCardNo(cardId);
        List<String> list = new ArrayList<>();
        for(Transaction t: transactionList){
            list.add(t.getTranstionNumber());
        }
        return list;
    }

}
