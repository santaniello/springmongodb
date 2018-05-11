package com.example.felipe.springmongodbaggregate.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WarehouseSummary {
    private String warehouse;
    private List<String> productIds;
    private float averagePrice;
    private float totalRevenue;

}
