package com.example.springbootmongodbclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelClient client;

    public List<Hotel> findAll(){
        return client.findAll();
    }

    public void save(Hotel hotel){
        client.save(hotel);
    }

    public Hotel findById(String id){
        return client.findById(id);
    }

    public void delete(String id){
        client.delete(id);
    }
}
