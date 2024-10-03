package com.example.MovieRentalSystem.controller;

import com.example.MovieRentalSystem.model.RentalRecord;
import com.example.MovieRentalSystem.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/rentMovie")
    public ResponseEntity<RentalRecord> rentMovie(@RequestParam Long movieId, @RequestParam String userEmail, @RequestParam int rentalDays) {
        RentalRecord rental = rentalService.rentMovie(movieId, userEmail, rentalDays);
        return new ResponseEntity<>(rental, HttpStatus.CREATED);
    }
}

