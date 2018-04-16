package com.example.springbootmongodbclient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
public class Hotel {
    private String id;
    private String name;
    // Indexa o preco de forma acendente...
    private int pricePerNight;
    private Address address;
    private List<Review> reviews;


    public Hotel(String name, int pricePerNight, Address address, List<Review> reviews) {
        this.name = name;
        this.pricePerNight = pricePerNight;
        this.address = address;
        this.reviews = reviews;
    }
}
