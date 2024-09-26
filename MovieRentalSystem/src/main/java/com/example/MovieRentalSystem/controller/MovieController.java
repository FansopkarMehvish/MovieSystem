package com.example.MovieRentalSystem.controller;

import com.example.MovieRentalSystem.model.Movie;
import com.example.MovieRentalSystem.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getMovies(){
        return movieService.getMovies();
    }

    @PostMapping("/addMovie")
    public Movie addMovie(@RequestParam String title,
                          @RequestParam String genre,
                          @RequestParam LocalDate releaseDate,
                          @RequestParam MultipartFile photo){
        try {
            return movieService.addMovie(title, genre, releaseDate, photo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    @PostMapping("/importToDB")
    public String importToDB(@RequestParam String dataFilePath){
        try {
            movieService.importToDB(dataFilePath);
            return "importToDB successful";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/filterByGenre")
    public List<Movie> filterByGenre(@RequestParam String genre){
        return movieService.filterByGenre(genre);
    }
}
