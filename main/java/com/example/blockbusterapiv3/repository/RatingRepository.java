package com.example.blockbusterapiv3.repository;

import com.example.blockbusterapiv3.model.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Long> {

    @Query("""
    SELECT m, AVG(r.rating) as avgRating 
    FROM movies m JOIN m.ratings r 
    GROUP BY m 
    ORDER BY avgRating DESC 
    """)
    Page<Object[]> findTopMoviesByAverageRating(Pageable pageable);

    @Query("""
    SELECT m.title, AVG(r.rating) 
    FROM movies m JOIN m.ratings r 
    GROUP BY m.title
    """)
    List<Object[]> findAllMoviesWithAvgRating();


    @Query("""
    SELECT m.title, COUNT(r) as ratingCount 
    FROM movies m JOIN m.ratings r 
    GROUP BY m.title 
    ORDER BY ratingCount DESC
    """)
    List<Object[]> findMoviesByRatingCount();
}
