package com.example.blockbusterapiv3.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Min(1)
    @Max(10)
    private int rating;

    @ManyToOne
    @JoinColumn(name = "movieId", nullable = false)
    @JsonIgnore
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    @JsonIgnore
    private Customer customer;
}
