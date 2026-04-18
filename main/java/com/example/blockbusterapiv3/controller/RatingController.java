package com.example.blockbusterapiv3.controller;

import com.example.blockbusterapiv3.model.Movie;
import com.example.blockbusterapiv3.model.Rating;
import com.example.blockbusterapiv3.service.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/ratings")
public class RatingController {

    private RatingService ratingService;

    @GetMapping("/top")
    public Page<Object[]> topRatingMovies(@RequestParam int page, @RequestParam int size){
        return ratingService.topRatingMovies(page,size);
    }

    @GetMapping("/avarages")
    public List<Object[]> moviesWithRatings(){
        return ratingService.moviesWithRatings();
    }

    @GetMapping
    public List<Rating> getAllRatings(){
        return ratingService.getAllRatings();
    }

    @GetMapping("/most-rated")
    public List<Object[]> movieRatingsCount(){
        return ratingService.movieRatingCount();
    }

    @PostMapping
    public Rating addRating(@RequestParam Long customerId, @RequestBody Rating rating){
        return ratingService.addRating(customerId,rating);
    }

    @PutMapping("/{id}")
    public Rating updateRating(@PathVariable("id") Long ratingId, @RequestBody Rating rating){
        return ratingService.updateRating(ratingId,rating);
    }

    @DeleteMapping("/{id}")
    public void deleteRating(@PathVariable("id") Long ratingId){
        ratingService.deleteRating(ratingId);
    }


}
