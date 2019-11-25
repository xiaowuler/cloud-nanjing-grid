package com.pingchuan.mongodb.dao.impl;

import com.pingchuan.dto.real.Element;
import com.pingchuan.mongodb.dao.RealSearchDao;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.Field;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.bind;

@Repository
public class RealSearchDaoImpl implements RealSearchDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Element> findRealNJGridsByArea(List<AggregationOperation> aggregationOperations) {

        Fields locationFields = Fields.fields("real_time", "element_code", "area_code", "area_name");
        aggregationOperations.add(Aggregation.project(locationFields).and("location").nested(bind("loc", "loc").and("grid_code").and("value")));
        aggregationOperations.add(Aggregation.group(locationFields).push("location").as("locations"));

        Fields realFields = Fields.fields("element_code", "area_code", "area_name");
        aggregationOperations.add(Aggregation.project(realFields).and("element_code").as("_id").and("real").nested(bind("real_time", "real_time").and("locations")));
        aggregationOperations.add(Aggregation.group("_id", "area_code", "area_name").push("real").as("reals"));

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        List<Element> elements = mongoTemplate.aggregate(aggregation, "elements", Element.class).getMappedResults();
        return elements;
    }

    @Override
    public List<Element> findRealNJGridsByLocation(List<AggregationOperation> aggregationOperations) {

        Fields realFields = Fields.fields("element_code", "area_code", "area_name", "grid_code", "loc");
        aggregationOperations.add(Aggregation.project(realFields).and("real").nested(bind("real_time", "real_time").and("value")));
        aggregationOperations.add(Aggregation.group(realFields).push("real").as("reals"));

        Fields locationFields = Fields.fields("element_code");
        aggregationOperations.add(Aggregation.project(locationFields).and("location").nested(bind("grid_code", "grid_code").and("loc").and("area_code").and("area_name").and("reals")));
        aggregationOperations.add(Aggregation.group(locationFields).push("location").as("locations"));

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        List<Element> elements = mongoTemplate.aggregate(aggregation, "elements", Element.class).getMappedResults();
        return elements;
    }
}
