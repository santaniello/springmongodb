package com.example.felipe.springmongodbaggregate.respositories;

import com.example.felipe.springmongodbaggregate.models.Product;
import com.example.felipe.springmongodbaggregate.models.WarehouseSummary;
import com.example.felipe.springmongodbaggregate.repositories.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AggregationTest {

    @Autowired
    private ProductRepository productRepository;

    @Before
    public void setUp() {
        productRepository.deleteAll();
        productRepository.save(new Product("NW1", "Norwich", 3.0f));
        productRepository.save(new Product("LN1", "London", 25.0f));
        productRepository.save(new Product("LN2", "London", 35.0f));
        productRepository.save(new Product("LV1", "Liverpool", 15.2f));
        productRepository.save(new Product("MN1", "Manchester", 45.5f));
        productRepository.save(new Product("LV2", "Liverpool", 23.9f));
        productRepository.save(new Product("LN3", "London", 55.5f));
        productRepository.save(new Product("LD1", "Leeds", 87.0f));
    }


    @Test
    public void aggregateProducts() {
        List<WarehouseSummary> warehouseSummaries = productRepository.aggregate(5.0f, 70.0f);

        assertEquals(3, warehouseSummaries.size());
        WarehouseSummary liverpoolProducts = getLiverpoolProducts(warehouseSummaries);
        assertEquals(39.1, liverpoolProducts.getTotalRevenue(), 0.01);
        assertEquals(19.55, liverpoolProducts.getAveragePrice(), 0.01);
    }

    private WarehouseSummary getLiverpoolProducts(List<WarehouseSummary> warehouseSummaries) {
        return warehouseSummaries.stream().filter(product -> "Liverpool".equals(product.getWarehouse())).findAny().get();
    }
}
