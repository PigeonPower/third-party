package com.thirdParty.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/response"})
public class ResponseController {

    @GetMapping
    public ResponseEntity responseDetail(){
        return ResponseEntity.ok().body("Success Response from 3rd Party.");
    }

}
