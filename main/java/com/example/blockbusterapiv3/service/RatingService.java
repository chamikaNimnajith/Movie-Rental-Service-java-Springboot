package com.example.blockbusterapiv3.service;

import com.example.blockbusterapiv3.model.Customer;
import com.example.blockbusterapiv3.model.Rating;
import com.example.blockbusterapiv3.repository.CustomerRepository;
import com.example.blockbusterapiv3.repository.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RatingService {

    private RatingRepository ratingRepository;
    private CustomerRepository customerRepository;

    public Page<Object[]> topRatingMovies(int page, int size) {
        Pageable topFive = PageRequest.of(page, size);
        return ratingRepository.findTopMoviesByAverageRating(topFive);
    }

    public List<Object[]> moviesWithRatings(){
        return ratingRepository.findAllMoviesWithAvgRating();
    }

    public List<Object[]> movieRatingCount(){
        return ratingRepository.findMoviesByRatingCount();
    }

    public List<Rating> getAllRatings(){
        return ratingRepository.findAll();
    }

    public Rating addRating(Long customerId, Rating rating){
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isPresent()){
            rating.setCustomer(customer.get());
            rating.setRating(rating.getRating());
             return ratingRepository.save(rating);
        }else{
            throw new IllegalArgumentException("customer not found with Id: " + customerId);
        }
    }

    public List<Rating> saveAllRatings(List<Rating> ratings){
        return ratingRepository.saveAll(ratings);
    }

    public Rating updateRating(Long ratingId, Rating rating){
        Optional<Rating> existRating = ratingRepository.findById(ratingId);
        if(existRating.isPresent()){
            existRating.get().setRating(rating.getRating());
            return ratingRepository.save(rating);
        }else{
            throw new IllegalArgumentException("rating not found with Id: " + ratingId);
        }
    }

    public void deleteRating(Long ratingId){
        ratingRepository.deleteById(ratingId);
    }


}
