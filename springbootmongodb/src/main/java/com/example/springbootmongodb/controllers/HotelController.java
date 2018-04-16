package com.example.springbootmongodb.controllers;

import com.example.springbootmongodb.models.Hotel;
import com.example.springbootmongodb.models.QHotel;
import com.example.springbootmongodb.repositories.HotelRepository;

import com.querydsl.core.types.dsl.BooleanExpression;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/hotels")
@Api(value = "HotelAPi", produces = MediaType.APPLICATION_JSON_VALUE)
public class HotelController{

    private HotelRepository hotelRepository;

    @Autowired
    public HotelController(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @GetMapping(path="/all")
    public List<Hotel> getAll(){
        return this.hotelRepository.findAll();
    }

    @PostMapping
    public void insert(@RequestBody Hotel hotel){
        this.hotelRepository.save(hotel);
    }

    @PutMapping
    public void update(@RequestBody Hotel hotel){
        this.hotelRepository.save(hotel);
    }

    @DeleteMapping(path="/{id}")
    public void delete(@PathVariable("id") String id){
        Hotel hotel = this.hotelRepository.findById(id).get();
        this.hotelRepository.delete(hotel);
    }

    @ApiOperation("Gets the Hotel with specific Id")
    @ApiResponses(
            value={
                    @ApiResponse(code=200,message="OK", response = Hotel.class),
                    @ApiResponse(code=404,message="Resource Not Found")
            }
    )
    @GetMapping(path="/{id}")
    public Hotel getById(
            @ApiParam(value = "Id do Hotel", required = true, example = "1")
            @PathVariable("id") String id
    ){
          return this.hotelRepository.findById(id).orElseThrow(() -> new RuntimeException("Não Existe Hotel com esse Id !"));
    }

    @GetMapping(path="/price/{maxPrice}")
    public List<Hotel> getByPricePerNight(@PathVariable("maxPrice") int maxPrice){
        return this.hotelRepository.findByPricePerNightLessThan(maxPrice);
    }

    @GetMapping("/address/{city}")
    public List<Hotel> getByCity(@PathVariable("city") String city){
        return this.hotelRepository.findByCity(city);
    }

   @GetMapping("/country/{country}")
    public List<Hotel> getByCountry(@PathVariable("country") String country){
        // Create a query class
        QHotel qHotel = new QHotel("hotel");
        // using the query class we can create the filters
        BooleanExpression filterByCountry = qHotel.address.country.eq(country);
        // we can then pass the filters to the findAll() method
        return (List<Hotel>)this.hotelRepository.findAll(filterByCountry);
    }

    @GetMapping("/recommended")
    public List<Hotel> getRecommended(){
        final int maxPrice = 100;
        final int minRating = 7;
        // Create a query class
        QHotel qHotel = new QHotel("hotel");
        // using the query class we can create the filters
        BooleanExpression filterByPrice  = qHotel.pricePerNight.lt(maxPrice);
        BooleanExpression filterByRating = qHotel.reviews.any().rating.gt(minRating);
        // we can then pass the filters to the findAll() method
        return (List<Hotel>) this.hotelRepository.findAll(filterByPrice.and(filterByRating));
    }
}
