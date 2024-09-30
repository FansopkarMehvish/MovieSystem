package com.example.MovieRentalSystem.exceptions;

public class MoviesNotFoundException extends RuntimeException{
    public MoviesNotFoundException(String s){
        super(s);
    }
}
