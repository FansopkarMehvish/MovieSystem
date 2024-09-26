package com.example.MovieFeignClient.repository;

import com.example.MovieFeignClient.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
