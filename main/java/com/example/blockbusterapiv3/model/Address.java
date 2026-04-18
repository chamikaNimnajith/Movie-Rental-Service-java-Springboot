package com.example.blockbusterapiv3.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;



    //Foreign key is stored in the owning side's table (Customer).
    @OneToOne(mappedBy = "address")
    // @JsonBackReference
    @JsonIgnore
    private Customer customer;

    @NotNull
    private String city;

    @NotNull
    @JsonProperty("zip_code")
    private String zipCode;

    public Address(String city, String zipCode) {
        this.city = city;
        this.zipCode = zipCode;
    }
}



//
//JSON (JavaScript Object Notation) serialization is the process of
//converting a Java object (or an object in any programming language)
//into a JSON string