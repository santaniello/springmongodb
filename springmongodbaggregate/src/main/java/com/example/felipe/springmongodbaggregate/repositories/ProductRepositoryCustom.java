package com.example.felipe.springmongodbaggregate.repositories;

import com.example.felipe.springmongodbaggregate.models.WarehouseSummary;
import com.example.felipe.springmongodbaggregate.models.WarehouseSummaryWithDates;

import java.util.List;

public interface ProductRepositoryCustom {
    
    List<WarehouseSummary> aggregate(float minPrice, float maxPrice);
    List<WarehouseSummaryWithDates> aggregateWithDates(String warehouse,String dateInit,String dateEnd);
}