package com.example.MovieFeignClient.controller;

import com.example.MovieFeignClient.model.Movie;
import com.example.MovieFeignClient.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/all")
    public Movie[] getAllMovies(){
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public Movie getMovie(@PathVariable int id){
        return movieService.getMovie(id);
    }

    @PostMapping("/exportToCSV")
    public String exportToCSV(@RequestParam String filepath){
        try {
            movieService.exportToCSV(filepath);
            return "exportToCSV successful";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
