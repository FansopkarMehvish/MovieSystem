package com.example.MovieFeignClient.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//id:    number
//title:    string
//year:    number
//genre:    array
//rating:    number
//director:    string
//actors:    array
//plot:    string
//poster:    string
//trailer:    string
//runtime:    number
//awards:    string
//country:    string
//language:    string
//boxOffice:    string
//production:    string
//website:    string

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Movie {
    @Id
    private int id;
    private String title;
    private int year;
    private String [] genre;
    private int rating;
    private String director;
    private String [] actors;
    private String plot;
    private String poster;
    private String trailer;
    private int runtime;
    private String awards;
    private String country;
    private String language;
    private String boxOffice;
    private String production;
    private String website;
}
