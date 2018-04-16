package com.example.springbootmongodb.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "Hotels")
public class Hotel {

    @Id // No mongodb, todo id é uma string
    @ApiModelProperty(notes="Id of Hotel")
    private String id;
    @ApiModelProperty(notes="Name of Hotel")
    private String name;
    // Indexa o preco de forma acendente...
    @Indexed(direction = IndexDirection.ASCENDING)
    @ApiModelProperty(notes="Price Per Night of Hotel")
    private int pricePerNight;
    @ApiModelProperty(notes="Address of Hotel")
    private Address address;
    @ApiModelProperty(notes="Reviews of Hotel")
    private List<Review> reviews;

    public Hotel(String name, int pricePerNight, Address address, List<Review> reviews) {
        this.name = name;
        this.pricePerNight = pricePerNight;
        this.address = address;
        this.reviews = reviews;
    }
}
