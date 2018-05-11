package com.example.felipe.springmongodbaggregate.respositories;

import com.example.felipe.springmongodbaggregate.models.Product;
import com.example.felipe.springmongodbaggregate.models.WarehouseSummary;
import com.example.felipe.springmongodbaggregate.models.WarehouseSummaryWithDates;
import com.example.felipe.springmongodbaggregate.repositories.ProductRepository;
import com.example.felipe.springmongodbaggregate.utils.DataUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.felipe.springmongodbaggregate.utils.DataUtils.*;
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
        productRepository.save(new Product("NW1", "Norwich", 3.0f,toDate("2018-01-01")));
        productRepository.save(new Product("LN1", "London", 25.0f,toDate("2018-01-01")));
        productRepository.save(new Product("LN2", "London", 35.0f,toDate("2018-01-03")));
        productRepository.save(new Product("LV1", "Liverpool", 15.2f,toDate("2018-01-01")));
        productRepository.save(new Product("MN1", "Manchester", 45.5f,toDate("2018-01-01")));
        productRepository.save(new Product("LV2", "Liverpool", 23.9f,toDate("2018-01-01")));
        productRepository.save(new Product("LN3", "London", 55.5f,toDate("2018-10-01")));
        productRepository.save(new Product("LD1", "Leeds", 87.0f,toDate("2018-01-01")));
    }


    @Test
    public void aggregateProducts() {
        List<WarehouseSummary> warehouseSummaries = productRepository.aggregate(5.0f, 70.0f);
        warehouseSummaries.forEach(s-> System.out.println(s.toString()));


        assertEquals(3, warehouseSummaries.size());
        WarehouseSummary liverpoolProducts = getLiverpoolProducts(warehouseSummaries);
        assertEquals(39.1, liverpoolProducts.getTotalRevenue(), 0.01);
        assertEquals(19.55, liverpoolProducts.getAveragePrice(), 0.01);
    }

    @Test
    public void aggregateProductsWithDates() {
        List<WarehouseSummaryWithDates> warehouseSummaries = productRepository.aggregateWithDates("London","2018-01-01T00:00:00.0Z","2018-02-01T00:00:00.0Z");
        System.out.println(warehouseSummaries.size());
        warehouseSummaries.forEach(s-> System.out.println(s.toString()));
        assertEquals(2, warehouseSummaries.size());
    }

    private WarehouseSummary getLiverpoolProducts(List<WarehouseSummary> warehouseSummaries) {
        return warehouseSummaries.stream().filter(product -> "Liverpool".equals(product.getWarehouse())).findAny().get();
    }


}
