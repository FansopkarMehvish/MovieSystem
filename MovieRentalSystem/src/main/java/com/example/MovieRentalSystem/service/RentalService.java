package com.example.MovieRentalSystem.service;

import com.example.MovieRentalSystem.exceptions.MovieIdNotFoundException;
import com.example.MovieRentalSystem.model.Movie;
import com.example.MovieRentalSystem.model.RentalRecord;
import com.example.MovieRentalSystem.repository.MovieRepository;
import com.example.MovieRentalSystem.repository.RentalRecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RentalService {

    private final RentalRecordRepository rentalRecordRepository;
    private final MovieRepository movieRepository;

    public RentalService(RentalRecordRepository rentalRecordRepository, MovieRepository movieRepository) {
        this.rentalRecordRepository = rentalRecordRepository;
        this.movieRepository = movieRepository;
    }

    public RentalRecord rentMovie(Long movieId, String userEmail, int rentalDays) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if (movieOptional.isPresent()) {
            Movie movie = movieOptional.get();

            RentalRecord rental = new RentalRecord();
            rental.setMovie(movie);
            rental.setUserEmail(userEmail);
            rental.setRentalDate(LocalDate.now());
            rental.setReturnDate(LocalDate.now().plusDays(rentalDays));

            return rentalRecordRepository.save(rental);
        } else {
            throw new MovieIdNotFoundException("Movie not found with id: " + movieId);
        }
    }
}
