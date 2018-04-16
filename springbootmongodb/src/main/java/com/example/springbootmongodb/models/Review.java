package com.example.springbootmongodb.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Review {
    private String userName;
    private int rating;
    private boolean approved;
}
