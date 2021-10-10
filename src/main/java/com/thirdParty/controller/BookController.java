package com.thirdParty.controller;

import com.thirdParty.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = {"/third-party/book"})
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity bookDetail(@RequestParam(name = "id") int id){
        return ResponseEntity.ok().body(bookService.bookDetail(id));
    }

}
