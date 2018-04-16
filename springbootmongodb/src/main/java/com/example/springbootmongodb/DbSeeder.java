package com.example.springbootmongodb;

import com.example.springbootmongodb.models.Address;
import com.example.springbootmongodb.models.Hotel;
import com.example.springbootmongodb.models.Review;
import com.example.springbootmongodb.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

// This interface provides access to application arguments as string array.
// Let's see the example code for more clarity.
@Component
public class DbSeeder implements CommandLineRunner {

    private HotelRepository repository;

    @Autowired
    public DbSeeder(HotelRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        Hotel marriot = new Hotel(
                "Marriot",
                130,
                new Address("Paris","France"),
                Arrays.asList(
                        new Review("John",8,false),
                        new Review("Mary",7,true)
                )
        );

        Hotel ibis = new Hotel(
                "Ibis",
                130,
                new Address("Bucharest","Romenia"),
                Arrays.asList(
                        new Review("Teddy",8,false),
                        new Review("Fred",7,true)
                )
        );

        Hotel sofitel  = new Hotel(
                "Sofitel",
                130,
                new Address("Paris","France"),
                Arrays.asList(
                        new Review("Alpacino",8,false),
                        new Review("Denniro",7,true)
                )
        );

        this.repository.deleteAll();

        List<Hotel> hotels = Arrays.asList(marriot,ibis,sofitel);
        hotels.forEach(h -> this.repository.save(h));
    }
}
