package com.example.MovieRentalSystem.controller;

import com.example.MovieRentalSystem.exceptions.FileConversionException;
import com.example.MovieRentalSystem.exceptions.MovieIdNotFoundException;
import com.example.MovieRentalSystem.exceptions.MoviesNotFoundException;
import com.example.MovieRentalSystem.model.Movie;
import com.example.MovieRentalSystem.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getMovies() {
        List<Movie> movies = movieService.getMovies();

        if (movies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(movies, HttpStatus.OK);
        }
    }

    @PostMapping("/addMovie")
    public ResponseEntity<?> addMovie(@RequestParam String title,
                                      @RequestParam int year,
                                      @RequestParam String genre,
                                      @RequestParam int rating,
                                      @RequestParam MultipartFile photo) {
        try {
            Movie movie = movieService.addMovie(title, year, genre, rating, photo);
            return new ResponseEntity<>(movie, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>("File error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable Long id,
                                         @RequestParam String title,
                                         @RequestParam int year,
                                         @RequestParam String genre,
                                         @RequestParam int rating,
                                         @RequestParam MultipartFile photo) {
        try {
            Movie updatedMovie = movieService.updateMovie(id, title, year, genre, rating, photo);
            return new ResponseEntity<>(updatedMovie, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("File error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable Long id){
        return movieService.deleteMovie(id);
    }

    @PostMapping("/importFromFeignCSV")
    public ResponseEntity<String> importFromFeignCSV(@RequestParam String dataFilePath) {
        try {
            movieService.importFromFeignCSV(dataFilePath);
            return new ResponseEntity<>("Import to DB successful", HttpStatus.OK);
        } catch (FileConversionException e) {
            return new ResponseEntity<>("Error importing from CSV: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/exportToCSV")
    public ResponseEntity<String> exportToCSV(@RequestParam String filepath) {
        try {
            movieService.exportToCSV(filepath);
            return new ResponseEntity<>("Export to CSV successful", HttpStatus.OK);
        } catch (FileConversionException e) {
            return new ResponseEntity<>("Error exporting to CSV: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/importToDB")
    public ResponseEntity<String> importToDB(@RequestParam String dataFilePath) {
        try {
            movieService.importToDB(dataFilePath);
            return new ResponseEntity<>("Import to DB successful", HttpStatus.OK);
        } catch (FileConversionException e) {
            return new ResponseEntity<>("Error importing from CSV: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/filterByGenre")
    public List<Movie> filterByGenre(@RequestParam String genre) {
        return movieService.filterByGenre(genre);
    }

    @PostMapping("/filterByRating")
    public List<Movie> filterByRating(@RequestParam int rating) {
        return movieService.filterByRating(rating);
    }

    @GetMapping("/sortByRating")
    public ResponseEntity<List<Movie>> sortByRating() {
        List<Movie> sortedMovies = movieService.sortMoviesByRating();

        if (sortedMovies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(sortedMovies, HttpStatus.OK);
        }
    }

    @GetMapping("/sortByYear")
    public ResponseEntity<List<Movie>> sortByYear() {
        List<Movie> sortedMovies = movieService.sortMoviesByYear();

        if (sortedMovies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(sortedMovies, HttpStatus.OK);
        }
    }

}
