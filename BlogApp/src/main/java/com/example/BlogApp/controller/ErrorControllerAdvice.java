package com.example.BlogApp.controller;

import com.example.BlogApp.exception.PostNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ErrorControllerAdvice {
    @ExceptionHandler
    @ResponseBody
    public ResponseEntity handlePostNotFoundException(PostNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.TEXT_PLAIN)
                .body(ex.getMessage());
    }
}
