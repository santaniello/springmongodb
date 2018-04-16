package com.example.springbootmongodbclient;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootmongodbclientApplicationTests {

    @Autowired
    protected HotelService service;

    @Test
    public void testFindAllHotelsWithSuccess() {
        List<Hotel> hotels = service.findAll();
        hotels.forEach(System.out::println);
    }

    @Test
    public void testSaveHotelsWithSuccess() {
        Hotel hotel = new Hotel(
                "ZUZU",
                130,
                new Address("Paris","France"),
                Arrays.asList(
                        new Review("John",8,false),
                        new Review("Mary",7,true)
                )
        );
        service.save(hotel);
    }

    @Test
    public void testFindByIdHotelsWithSuccess() {
        Hotel hotel = new Hotel(
                 "555",
                "ZUZU",
                130,
                new Address("Paris","France"),
                Arrays.asList(
                        new Review("John",8,false),
                        new Review("Mary",7,true)
                )
        );
        service.save(hotel);
        Hotel hotelReturned = service.findById(hotel.getId());
        Assert.assertNotNull("O Hotel não pode ser nulo!",hotelReturned);
    }

    @Test
    public void testDeleteHotelsWithSuccess() {
        Hotel hotel = new Hotel(
                "5555",
                "ZUZU",
                130,
                new Address("Paris","France"),
                Arrays.asList(
                        new Review("John",8,false),
                        new Review("Mary",7,true)
                )
        );
       service.save(hotel);
       service.delete(hotel.getId());
       Assert.assertNull("O Hotel deve ser nulo!",service.findById(hotel.getId()));
    }

}
