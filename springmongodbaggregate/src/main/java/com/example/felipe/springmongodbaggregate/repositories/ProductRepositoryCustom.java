package com.example.felipe.springmongodbaggregate.repositories;

import com.example.felipe.springmongodbaggregate.models.WarehouseSummary;

import java.util.List;

public interface ProductRepositoryCustom {
    
    List<WarehouseSummary> aggregate(float minPrice, float maxPrice);
}