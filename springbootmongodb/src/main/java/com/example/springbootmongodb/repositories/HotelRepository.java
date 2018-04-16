package com.example.springbootmongodb.repositories;

import com.example.springbootmongodb.models.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository // Para Executar os filtros da query dsl, precisamos extender QuerydslPredicateExecutor
public interface HotelRepository extends MongoRepository<Hotel,String>, QuerydslPredicateExecutor<Hotel> {
    public Optional<Hotel> findById(String id);
    List<Hotel> findByPricePerNightLessThan(int max);

    // Query que não é TypeSafe, só saberemos se a mesma esta correta em tempo de execução...
    @Query(value = "{address.city : ?0 }")
    List<Hotel> findByCity(String city);
}
