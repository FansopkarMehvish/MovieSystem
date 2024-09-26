package com.example.MovieFeignClient.interfaces;

import com.example.MovieFeignClient.model.Movie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "movieInterface", url = "${feign.url}")
public interface MovieInterface {

    @GetMapping
    Movie[] getAllMovies();

    @GetMapping("/{id}")
    Movie getMovie(@PathVariable int id);

}
