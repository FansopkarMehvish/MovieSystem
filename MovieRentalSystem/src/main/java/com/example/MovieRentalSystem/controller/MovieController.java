package com.example.MovieRentalSystem.controller;

import com.example.MovieRentalSystem.model.Movie;
import com.example.MovieRentalSystem.service.MovieService;
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
                          @RequestParam int year,
                          @RequestParam String genre,
                          @RequestParam int rating,
                          @RequestParam MultipartFile photo){
        try {
            return movieService.addMovie(title, year, genre, rating, photo );
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

    @PostMapping("/importFromFeignCSV")
    public String importFromFeignCSV(@RequestParam String dataFilePath){
        movieService.importFromFeignCSV(dataFilePath);
        return "importToDB successful";
    }

    @PostMapping("/filterByGenre")
    public List<Movie> filterByGenre(@RequestParam String genre){
        return movieService.filterByGenre(genre);
    }

    @PostMapping("/filterByRating")
    public List<Movie> filterByRating(@RequestParam int rating){
        return movieService.filterByRating(rating);
    }

    @GetMapping("/sortByRating")
    public List<Movie> sortByRating() {
        return movieService.sortMoviesByRating();
    }

    @GetMapping("/sortByYear")
    public List<Movie> sortByYear() {
        return movieService.sortMoviesByYear();
    }


}
