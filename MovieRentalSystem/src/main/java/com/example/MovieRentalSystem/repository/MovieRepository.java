package com.example.MovieRentalSystem.repository;

import com.example.MovieRentalSystem.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
