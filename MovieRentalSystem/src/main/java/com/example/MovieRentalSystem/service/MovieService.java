package com.example.MovieRentalSystem.service;

import com.example.MovieRentalSystem.model.Movie;
import com.example.MovieRentalSystem.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Value("${upload.dir}")
    String uploadDir;

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public Movie addMovie(String title, String genre, LocalDate releaseDate, MultipartFile photo) throws IOException {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setGenre(genre);
        movie.setReleaseDate(releaseDate);

        String filename = UUID.randomUUID() + "_" + photo.getOriginalFilename();
        Path filepath = Paths.get(uploadDir + filename);
        photo.transferTo(filepath);
        movie.setMovieCoverImagePath(filepath.toString());

        return movieRepository.save(movie);
    }

    public void exportToCSV(String dataFilePath) throws IOException {
        List<Movie> movies = getMovies();

        try (FileWriter fileWriter = new FileWriter(dataFilePath, true)) {
            File file = new File(dataFilePath);
            if (file.length() == 0) {
                fileWriter.append("Id, Title, Genre, ReleaseDate, movieCoverImagePath ");
                fileWriter.append("\n");
            }

            for (Movie movie : movies) {
                fileWriter.append(movie.getId() + "," + movie.getTitle() + "," + movie.getGenre() + "," + movie.getReleaseDate() + "," + movie.getMovieCoverImagePath());
                fileWriter.append("\n");
            }
        }
    }

    public void importToDB(String dataFilePath) throws IOException {
        File file = new File(dataFilePath);
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null){
                String [] data = line.split(",");
                if(data.length == 5){
                    Movie movie = new Movie(Long.parseLong(data[0]), data[1], data[2], LocalDate.parse(data[3]), data[4]);
                    movieRepository.save(movie);
                }

            }
        }
    }

    public List<Movie> filterByGenre(String genre){
        List<Movie> movies = getMovies();
        Function<String, List<Movie>> filterMovieByGenre = filterGenre -> movies.stream().filter(movie -> movie.getGenre().equalsIgnoreCase(genre)).toList();
        return filterMovieByGenre.apply(genre);
    }


}

