package com.example.blockbusterapiv3.repository;

import com.example.blockbusterapiv3.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
    List<Customer> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
    List<Customer> findByRegistrationDateBetween(LocalDate start, LocalDate end);

    // Custom JPQL query: Find customers registered after a certain date
    // here c means zero or more rows
    @Query("SELECT c FROM customers c WHERE c.registrationDate > :date")
    List<Customer> findCustomersRegisteredAfter(@Param("date") LocalDate date);


    //The Underscore Means "Navigate Through Objects"
    List<Customer> findByAddress_CityIgnoreCase(String city);

    //Query to find Customers count by City
    // c represent zero or more records of customers
    @Query("SELECT a.city, COUNT(c) FROM customers c JOIN c.address a GROUP BY a.city")
    // this returning object array is because of selecting two columns
    List<Object[]> CustomerCountPerCity();
}


//java
//List<Object[]> result = Arrays.asList(
//        new Object[] { "New York", 15 },  // Row 1
//        new Object[] { "Berlin",   8  },  // Row 2
//        new Object[] { "Tokyo",    22 }   // Row 3
//);


//json
//  [
//        ["New York", 15],
//        ["Berlin", 8],
//        ["Tokyo", 22]
//    ]