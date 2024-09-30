package com.example.MovieRentalSystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FileConversionException.class)
    public ResponseEntity<String> handleFileConversionException(FileConversionException fileConversionException) {
        return new ResponseEntity<>(fileConversionException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MoviesNotFoundException.class)
    public ResponseEntity<String> handleMovieException(MoviesNotFoundException moviesNotFoundException) {
        return new ResponseEntity<>(moviesNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MovieIdNotFoundException.class)
    public ResponseEntity<String> handleMovieIdNotFoundException(MovieIdNotFoundException movieIdNotFoundException) {
        return new ResponseEntity<>(movieIdNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}