package com.example.MovieFeignClient.service;

import com.example.MovieFeignClient.interfaces.MovieInterface;
import com.example.MovieFeignClient.model.Movie;
import com.example.MovieFeignClient.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieInterface movieInterface;

    public MovieService(MovieInterface movieInterface) {
        this.movieInterface = movieInterface;
    }

    public Movie[] getAllMovies(){
        return movieInterface.getAllMovies();
    }

    public Movie getMovie(int id){
        return movieInterface.getMovie(id);
    }
}
