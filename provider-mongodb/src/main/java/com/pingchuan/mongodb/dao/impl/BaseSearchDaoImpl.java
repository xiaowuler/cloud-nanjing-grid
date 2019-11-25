package com.pingchuan.mongodb.dao.impl;

import com.pingchuan.dto.base.AreaElement;
import com.pingchuan.mongodb.dao.BaseSearchDao;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.bind;

@Repository
public class BaseSearchDaoImpl implements BaseSearchDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<AreaElement> findNJGridsByArea(List<AggregationOperation> aggregationOperations) {
        aggregationOperations.addAll(groupByArea());
        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        List<AreaElement> areaElements = mongoTemplate.aggregate(aggregation, "elements", AreaElement.class).getMappedResults();
        return areaElements;
    }

    @Override
    public List<AreaElement> findNJGridsByLocation(List<AggregationOperation> aggregationOperations) {
        aggregationOperations.addAll(groupByLocation());
        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        List<AreaElement> areaElements = mongoTemplate.aggregate(aggregation, "elements", AreaElement.class).getMappedResults();
        return areaElements;
    }

    private List<AggregationOperation> groupByLocation(){
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        Fields forecastFields = Fields.fields("start_time", "update_time", "element_code","forecast_model", "area_code", "area_name", "grid_code", "loc");
        aggregationOperations.add(Aggregation.project(forecastFields).and("forecast").nested(bind("forecast_time", "forecast_time").and("time_effect").and("value")));
        aggregationOperations.add(Aggregation.group(forecastFields).push("forecast").as("forecasts"));

        Fields locationFields = Fields.fields("start_time", "update_time", "element_code","forecast_model");
        aggregationOperations.add(Aggregation.project(locationFields).and("location").nested(bind("loc", "loc").and("grid_code").and("area_code").and("area_name").and("forecasts")));
        aggregationOperations.add(Aggregation.group(locationFields).push("location").as("locations"));

        Fields elementCodeFields = Fields.fields("start_time", "update_time","forecast_model");
        aggregationOperations.add(Aggregation.project(elementCodeFields).and("element_code").nested(bind("element_code", "element_code").and("locations")));
        aggregationOperations.add(Aggregation.group(elementCodeFields).push("element_code").as("element_codes"));

        return aggregationOperations;
    }

    private List<AggregationOperation> groupByArea(){
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        Fields locationFields = Fields.fields("start_time", "update_time", "element_code","forecast_model", "area_code", "area_name", "forecast_time", "time_effect");
        aggregationOperations.add(Aggregation.project(locationFields).and("location").nested(bind("loc", "loc").and("grid_code").and("value")));
        aggregationOperations.add(Aggregation.group(locationFields).push("location").as("locations"));

        Fields forecastFields = Fields.fields("start_time", "update_time", "element_code","forecast_model", "area_code", "area_name");
        aggregationOperations.add(Aggregation.project(forecastFields).and("forecast").nested(bind("forecast_time", "forecast_time").and("time_effect").and("locations")));
        aggregationOperations.add(Aggregation.group(forecastFields).push("forecast").as("forecasts"));

        Fields elementCodeFields = Fields.fields("start_time", "update_time","forecast_model", "area_code", "area_name");
        aggregationOperations.add(Aggregation.project(elementCodeFields).and("element_code").nested(bind("element_code", "element_code").and("forecasts")));
        aggregationOperations.add(Aggregation.group(elementCodeFields).push("element_code").as("element_codes"));

        return aggregationOperations;
    }
}
