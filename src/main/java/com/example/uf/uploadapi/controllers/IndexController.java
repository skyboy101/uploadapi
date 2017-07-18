package com.example.uf.uploadapi.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class IndexController {

    @GetMapping(value ="/", produces= "application/json")
    public ResponseEntity<?> index() {
        return new ResponseEntity<>(Collections.singletonMap("message", "Upload File Demo REST API Application"), new HttpHeaders(), HttpStatus.OK);
    }

}
