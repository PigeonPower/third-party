package com.thirdParty.service;

import com.thirdParty.controller.model.BookRequest;
import com.thirdParty.entity.Book;
import com.thirdParty.enums.BookStatus;
import com.thirdParty.repository.BookRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    Logger logger = LogManager.getLogger(BookService.class);

    public Book bookCreation(BookRequest bookRequest) {

        Book book = new Book();
        book.setName(bookRequest.getName());

        book = bookRepository.save(book);

        logger.debug("Book Response : {}", book);
        logger.debug("Book Creation Completed");
        return book;
    }

    public List<Book> bookListing(Integer pageNo, Integer pageSize) {

        Pageable paging = PageRequest.of(pageNo, pageSize);

        logger.debug("Book Listing Completed");
        return bookRepository.findAll(paging).getContent();
    }

    public Book bookDetail(int id) {

        if (!bookRepository.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book is not found");
        }

        Book book = bookRepository.findById(id).get();

        logger.debug("Book Response : {}", book);
        logger.debug("Book Retrieve Completed");
        return book;
    }

    public Book bookUpdate(int id, BookRequest bookRequest) {

        if (!bookRepository.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book is not found");
        }

        Book book = bookRepository.findById(id).get();

        book.setName(bookRequest.getName());
        book.setModifiedDate(new Date());

        book = bookRepository.save(book);

        logger.debug("Book Response : {}", book);
        logger.debug("Book Update Completed");
        return book;
    }

    public void bookDeletion(int id) {

        if (!bookRepository.findById(id).isPresent()) {
            logger.info("test");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book is not found");
        }

        logger.debug("Book Delete Completed");
        bookRepository.deleteById(id);
    }

    public Book bookBorrow(int id) {
        if (!bookRepository.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book is not found");
        }
        Book book = bookRepository.findById(id).get();
        if (!BookStatus.AVAILABLE.equals(book.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST ,"Fail Borrow, Book is not available");
        }

        book = borrowReturn(book, BookStatus.BORROWED);
        book = bookRepository.save(book);

        logger.debug("Book Response : {}", book);
        logger.debug("Book Borrow Completed");
        return book;

    }

    public Book bookReturn(int id) {
        if (!bookRepository.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book is not exist");
        }
        Book book = bookRepository.findById(id).get();
        if (!BookStatus.BORROWED.equals(book.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST ,"Fail Return, Book is not borrowed");
        }

        book = borrowReturn(book, BookStatus.AVAILABLE);
        book = bookRepository.save(book);

        logger.debug("Book Response : {}", book);
        logger.debug("Book Return Completed");
        return book;
    }

    private Book borrowReturn(Book book, BookStatus bookStatus) {
        book.setStatus(bookStatus);
        book.setModifiedDate(new Date());

        return book;
    }
}
