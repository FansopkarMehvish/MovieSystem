package com.example.MovieRentalSystem.service;

import com.example.MovieRentalSystem.exceptions.FileConversionException;
import com.example.MovieRentalSystem.exceptions.MovieIdNotFoundException;
import com.example.MovieRentalSystem.exceptions.MoviesNotFoundException;
import com.example.MovieRentalSystem.model.Movie;
import com.example.MovieRentalSystem.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.IntFunction;

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

    public Movie addMovie(String title, int year, String genre, int rating, MultipartFile photo) throws IOException {
        Movie movie = new Movie();

        movie.setTitle(title);
        movie.setGenre(genre);
        movie.setYear(year);
        movie.setRating(rating);

        String filename = UUID.randomUUID() + "_" + photo.getOriginalFilename();
        Path filepath = Paths.get(uploadDir + filename);
        photo.transferTo(filepath);
        movie.setMovieCoverImagePath(filepath.toString());

        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, String title, int year, String genre, int rating, MultipartFile photo) throws IOException{
        if(movieRepository.findById(id).isPresent()) {
            Movie movie = new Movie();
            movie.setId(id);
            movie.setTitle(title);
            movie.setGenre(genre);
            movie.setYear(year);
            movie.setRating(rating);

            String filename = UUID.randomUUID() + "_" + photo.getOriginalFilename();
            Path filepath = Paths.get(uploadDir + filename);
            photo.transferTo(filepath);
            movie.setMovieCoverImagePath(filepath.toString());

            return movieRepository.save(movie);
        }
        throw new MovieIdNotFoundException("Movie not found with id "+id);
    }

    public String deleteMovie(Long id) {
        if(movieRepository.findById(id).isPresent()) {
            movieRepository.deleteById(id);
            return "Movie deleted";
        }
        throw new MovieIdNotFoundException("Movie not found with id "+id);
    }

    public void importFromFeignCSV(String filepath) {
        File file = new File(filepath);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                Movie movie = new Movie(Long.parseLong(data[0]), data[1], Integer.parseInt(data[2]), data[3], Integer.parseInt(data[4]), null);
                movieRepository.save(movie);
            }
        } catch (IOException e) {
            throw new FileConversionException("File could not be imported from CSV");
        }
    }

    public void exportToCSV(String dataFilePath) {
        List<Movie> movies = getMovies();

        try (FileWriter fileWriter = new FileWriter(dataFilePath, true)) {
            File file = new File(dataFilePath);
            if (file.length() == 0) {
                fileWriter.append("Id, Title, Year, Genre, Rating, movieCoverImagePath ");
                fileWriter.append("\n");
            }

            for (Movie movie : movies) {
                fileWriter.append(String.valueOf(movie.getId()))
                        .append(",")
                        .append(movie.getTitle())
                        .append(",")
                        .append(String.valueOf(movie.getYear()))
                        .append(",")
                        .append(movie.getGenre())
                        .append(",")
                        .append(String.valueOf(movie.getRating()))
                        .append(",")
                        .append(movie.getMovieCoverImagePath())
                        .append("\n");
            }
        } catch (IOException e) {
            throw new FileConversionException("File could not be exported to CSV");
        }
    }

    public void importToDB(String dataFilePath) {
        File file = new File(dataFilePath);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    Movie movie = new Movie(Long.parseLong(data[0]), data[1], Integer.parseInt(data[2]), data[3], Integer.parseInt(data[4]), data[5]);
                    movieRepository.save(movie);
                }
            }
        } catch (Exception e) {
            throw new FileConversionException("File could not be imported from CSV");
        }
    }

    public List<Movie> filterByGenre(String genre) {
        List<Movie> movies = getMovies();

        Function<String, List<Movie>> filterMovieByGenre = filterGenre ->
                movies.stream()
                        .filter(movie -> Optional.ofNullable(movie.getGenre())
                                .map(g -> g.equalsIgnoreCase(filterGenre))
                                .orElse(false))
                        .toList();

        return Optional.ofNullable(genre)
                .map(filterMovieByGenre)
                .filter(filteredMovies -> !filteredMovies.isEmpty())
                .orElseThrow(() -> new MoviesNotFoundException("No movies found for the specified genre:" + genre));
    }

    public List<Movie> filterByRating(int rating) {
        List<Movie> movies = getMovies();
        Function<Integer, List<Movie>> filterMovieByRating = filterGenre -> movies.stream()
                .filter(movie -> movie.getRating() == rating)
                .toList();
        return Optional.ofNullable(rating)
                .map(filterMovieByRating)
                .filter(filteredMovies -> !filteredMovies.isEmpty())
                .orElseThrow(() -> new MoviesNotFoundException("No movies found for the specified rating:" + rating));
    }

    public List<Movie> sortMoviesByRating() {
        return getMovies().stream()
                .sorted(Comparator.comparing(Movie::getRating))
                .toList();
    }

    public List<Movie> sortMoviesByYear() {
        return getMovies().stream()
                .sorted(Comparator.comparing(Movie::getYear).reversed())
                .toList();
    }
}

