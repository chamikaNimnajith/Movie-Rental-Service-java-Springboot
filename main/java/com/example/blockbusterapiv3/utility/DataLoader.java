package com.example.blockbusterapiv3.utility;

import com.example.blockbusterapiv3.model.*;
import com.example.blockbusterapiv3.service.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataLoader {
    private final MovieService movieService;
    private final CustomerService customerService;
    private final RatingService ratingService;
    private final Random random = new Random();

    public DataLoader(MovieService movieService,
                      CustomerService customerService,
                      RatingService ratingService) {
        this.movieService = movieService;
        this.customerService = customerService;
        this.ratingService = ratingService;
    }

    @PostConstruct
    public void loadData() {
        // Load movies if empty
        List<Movie> movies = movieService.getAllMovies();
        if (movies.isEmpty()) {
            movies = new ArrayList<>();
            movies.add(new Movie("The Shawshank Redemption", 1994, MovieGenre.DRAMA));
            movies.add(new Movie("The Godfather", 1972, MovieGenre.DRAMA));
            movies.add(new Movie("The Dark Knight", 2008, MovieGenre.ACTION));
            movies.add(new Movie("Pulp Fiction", 1994, MovieGenre.ACTION));
            movies.add(new Movie("Forrest Gump", 1994, MovieGenre.DRAMA));
            movies.add(new Movie("Inception", 2010, MovieGenre.ACTION));
            movies.add(new Movie("Fight Club", 1999, MovieGenre.DRAMA));
            movies.add(new Movie("The Matrix", 1999, MovieGenre.ACTION));
            movies.add(new Movie("The Lord of the Rings: The Return of the King", 2003, MovieGenre.ACTION));
            movies.add(new Movie("Interstellar", 2014, MovieGenre.SCI_FI));
            movieService.addMovies(movies);
        }

        // Load customers if empty
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            customers = new ArrayList<>();

            Customer john = new Customer("John", "Doe", "john.doe@example.com");
            john.setRegistrationDate(LocalDate.of(2021, 1, 15));
            john.setAddress(new Address("Colombo", "12345"));
            customers.add(john);

            Customer jane = new Customer("Jane", "Smith", "jane.smith@example.com");
            jane.setRegistrationDate(LocalDate.of(2020, 5, 20));
            jane.setAddress(new Address("Kandy", "54321"));
            customers.add(jane);

            Customer alice = new Customer("Alice", "Johnson", "alice.johnson@example.com");
            alice.setRegistrationDate(LocalDate.of(2019, 3, 10));
            alice.setAddress(new Address("Galle", "67890"));
            customers.add(alice);

            Customer bob = new Customer("Bob", "Brown", "bob.brown@example.com");
            bob.setRegistrationDate(LocalDate.of(2022, 7, 25));
            bob.setAddress(new Address("Colombo", "12345"));
            customers.add(bob);

            customerService.saveAllCustomers(customers);
        }

        // Load ratings if empty
        if (ratingService.getAllRatings().isEmpty()) {
            List<Rating> ratings = new ArrayList<>();

            // Each customer rates 3-5 random movies
            for (Customer customer : customers) {
                int ratingsToAdd = 3 + random.nextInt(3); // 3-5 ratings per customer
                List<Movie> moviesToRate = new ArrayList<>(movies);

                for (int i = 0; i < ratingsToAdd; i++) {
                    if (moviesToRate.isEmpty()) break;

                    Movie movie = moviesToRate.remove(random.nextInt(moviesToRate.size()));
                    int score = 1 + random.nextInt(10); // Random rating 1-10

                    Rating rating = new Rating();
                    rating.setRating(score);
                    rating.setMovie(movie);
                    rating.setCustomer(customer);
                    ratings.add(rating);
                }
            }

            ratingService.saveAllRatings(ratings);
        }
    }
}