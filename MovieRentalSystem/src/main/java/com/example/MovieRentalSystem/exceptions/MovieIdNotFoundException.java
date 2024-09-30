package com.example.MovieRentalSystem.exceptions;

public class MovieIdNotFoundException extends RuntimeException{
    public MovieIdNotFoundException(String s){
        super(s);
    }
}
