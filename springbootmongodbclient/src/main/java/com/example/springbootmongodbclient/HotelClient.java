package com.example.springbootmongodbclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@FeignClient(url="http://localhost:8080/hotels/", name="hotels")
public interface HotelClient {

    @GetMapping(path = "all")
    public List<Hotel> findAll();

    @PostMapping
    public void save(Hotel hotel);

    @GetMapping(path = "{id}")
    public Hotel findById(@PathVariable("id") String id);

    @DeleteMapping(path = "{id}")
    public void delete(@PathVariable("id") String id);

}
