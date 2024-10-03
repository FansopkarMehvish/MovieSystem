package com.example.MovieRentalSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MovieRentalSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieRentalSystemApplication.class, args);
	}

}
