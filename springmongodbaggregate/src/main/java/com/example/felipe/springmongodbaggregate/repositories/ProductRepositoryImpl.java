package com.example.felipe.springmongodbaggregate.repositories;

import com.example.felipe.springmongodbaggregate.models.Product;
import com.example.felipe.springmongodbaggregate.models.WarehouseSummary;
import com.example.felipe.springmongodbaggregate.models.WarehouseSummaryWithDates;
import com.example.felipe.springmongodbaggregate.utils.DataUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.query.Criteria.where;

public class ProductRepositoryImpl implements ProductRepositoryCustom {
    
    private final MongoTemplate mongoTemplate;
    
    @Autowired
    public ProductRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /******************************************************************************************************
     *************************   EXEMPLO 1 ****************************************************************
     ******************************************************************************************************/

    @Override
    public List<WarehouseSummaryWithDates> aggregateWithDates(String warehouse,String dateInit, String dateEnd) {
        Criteria matcher = where("warehouse").is(warehouse)
                .andOperator(where("createdDate").gte(DataUtils.toMongoDate(dateInit))
                        .andOperator(where("createdDate").lt(DataUtils.toMongoDate(dateEnd))));
        MatchOperation matchOperation = match(matcher);

        // Observação: Os mesmos campos que estiverem no ProjectOperation devem estar no GroupOperation senão dá erro...
        ProjectionOperation projectionOperation = project("warehouse","price","createdDate")
                .and("product").previousOperation();
        GroupOperation groupOperation = group("warehouse","price","createdDate");
        return mongoTemplate.aggregate(Aggregation.newAggregation(
                matchOperation,
                groupOperation,
                projectionOperation
        ), Product.class, WarehouseSummaryWithDates.class).getMappedResults();

    }

    /******************************************************************************************************
     *************************   EXEMPLO 2 ****************************************************************
     Exemplo:  https://xpadro.com/2016/04/data-aggregation-with-spring-data-mongodb-and-spring-boot.html
     ******************************************************************************************************/

    @Override
    public List<WarehouseSummary> aggregate(float minPrice, float maxPrice) {
        MatchOperation matchOperation = getMatchOperation(minPrice, maxPrice);
        GroupOperation groupOperation = getGroupOperation();
        ProjectionOperation projectionOperation = getProjectOperation();

        return mongoTemplate.aggregate(Aggregation.newAggregation(
                matchOperation,
                groupOperation,
                projectionOperation
        ), Product.class, WarehouseSummary.class).getMappedResults();
    }

    private MatchOperation getMatchOperation(float minPrice, float maxPrice) {
        Criteria priceCriteria = where("price").gt(minPrice).andOperator(where("price").lt(maxPrice));
        return match(priceCriteria);
    }

    private GroupOperation getGroupOperation() {
        return group("warehouse")
                .last("warehouse").as("warehouse")
                .addToSet("id").as("productIds")
                .avg("price").as("averagePrice")
                .sum("price").as("totalRevenue");
    }


    private ProjectionOperation getProjectOperation() {
        return project("productIds", "averagePrice", "totalRevenue")
                .and("warehouse").previousOperation();
    }
}
