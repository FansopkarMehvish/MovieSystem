package com.example.MovieFeignClient.service;

import com.example.MovieFeignClient.interfaces.MovieInterface;
import com.example.MovieFeignClient.model.Movie;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

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

    public void exportToCSV(String dataFilePath) throws IOException {
        Movie [] movies = getAllMovies();

        try (FileWriter fileWriter = new FileWriter(dataFilePath, true)) {
            File file = new File(dataFilePath);
            if (file.length() == 0) {
                fileWriter.append("Id, Title, Year, Genre, Rating");
                fileWriter.append("\n");
            }

            for (Movie movie : movies) {
                fileWriter.append(String.valueOf(movie.getId()))
                        .append(",")
                        .append(movie.getTitle())
                        .append(",")
                        .append(String.valueOf(movie.getYear()))
                        .append(",")
                        .append(Arrays.stream(movie.getGenre()).findFirst().get())
                        .append(",")
                        .append(String.valueOf(movie.getRating()))
                        .append("\n");
            }
        }
    }
}
