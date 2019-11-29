package com.pingchuan.mongodb.dao.impl;

import com.pingchuan.dto.other.Element;
import com.pingchuan.mongodb.dao.OtherSearchDao;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.Field;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.bind;

@Repository
public class OtherSearchDaoImpl implements OtherSearchDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Element> findNewestTime(List<AggregationOperation> aggregationOperations) {
        Fields forecastFields = Fields.fields("start_time", "update_time", "element_code", "forecast_model");
        aggregationOperations.add(Aggregation.project(forecastFields).and("forecast").nested(bind("forecast_time", "forecast_time").and("time_effect")));
        aggregationOperations.add(Aggregation.group(forecastFields).push("forecast").as("forecasts"));

        Fields updateTimeFields = Fields.fields("start_time", "element_code", "forecast_model");
        aggregationOperations.add(Aggregation.project(updateTimeFields).and("update_time").nested(bind("update_time", "update_time").and("forecasts")));
        aggregationOperations.add(Aggregation.group(updateTimeFields).max("update_time").as("update_time"));

        Fields startTimeFields = Fields.fields("element_code", "forecast_model");
        aggregationOperations.add(Aggregation.project(startTimeFields).and("start_time").nested(bind("start_time", "start_time").and("update_time")));
        aggregationOperations.add(Aggregation.group(startTimeFields).max("start_time").as("start_time"));

        aggregationOperations.add(Aggregation.project("element_code", "forecast_model", "start_time.start_time", "start_time.update_time.update_time", "start_time.update_time.forecasts"));
        aggregationOperations.add(Aggregation.project("element_code", "forecast_model", "start_time", "update_time.update_time", "update_time.forecasts"));

        Fields elementCodeFields = Fields.fields("forecast_model");
        aggregationOperations.add(Aggregation.project(elementCodeFields).and("element_code").nested(bind("element_code", "element_code").and("start_time").and("update_time").and("forecasts")));
        aggregationOperations.add(Aggregation.group(elementCodeFields).push("element_code").as("element_codes"));

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        List<Element> elements = mongoTemplate.aggregate(aggregation, "elements", Element.class).getMappedResults();
        return elements;
    }

    @Override
    public List<Element> findUpdateAndStartByDate(List<AggregationOperation> aggregationOperations) {
        Fields forecastFields = Fields.fields("start_time", "update_time", "element_code", "forecast_model");
        aggregationOperations.add(Aggregation.project(forecastFields).and("forecast").nested(bind("forecast_time", "forecast_time").and("time_effect")));
        aggregationOperations.add(Aggregation.group(forecastFields).push("forecast").as("forecasts"));

        Fields updateTimeFields = Fields.fields("update_time", "element_code", "forecast_model");
        aggregationOperations.add(Aggregation.project(updateTimeFields).and("start_time").nested(bind("start_time", "start_time").and("forecasts")));
        aggregationOperations.add(Aggregation.group(updateTimeFields).push("start_time").as("start_times"));

        Fields startTimeFields = Fields.fields("element_code", "forecast_model");
        aggregationOperations.add(Aggregation.project(startTimeFields).and("update_time").nested(bind("update_time", "update_time").and("start_times")));
        aggregationOperations.add(Aggregation.group(startTimeFields).push("update_time").as("update_times"));

        Fields elementCodeFields = Fields.fields("forecast_model");
        aggregationOperations.add(Aggregation.project(elementCodeFields).and("element_code").nested(bind("element_code", "element_code").and("update_times")));
        aggregationOperations.add(Aggregation.group(elementCodeFields).push("element_code").as("element_codes"));

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        List<Element> elements = mongoTemplate.aggregate(aggregation, "elements", Element.class).getMappedResults();
        return elements;
    }

    @Override
    public List<Element> findNewestTimeByForecastTime(List<AggregationOperation> aggregationOperations) {

        Fields updateTimeFields = Fields.fields("start_time", "element_code", "forecast_model", "update_time", "forecast_time", "time_effect");
        aggregationOperations.add(Aggregation.group(updateTimeFields).push("update_time").as("update_times"));

        Fields startTimeFields = Fields.fields("element_code", "forecast_model", "forecast_time");
        aggregationOperations.add(Aggregation.project(startTimeFields).and("start_time").nested(bind("start_time", "start_time").and("time_effect").and("update_times")));
        aggregationOperations.add(Aggregation.group(startTimeFields).push("start_time").as("start_times"));

        Fields elementCodeFields = Fields.fields("forecast_model", "forecast_time");
        aggregationOperations.add(Aggregation.project(elementCodeFields).and("forecast_model").as("_id").and("element_code").nested(bind("element_code", "element_code").and("start_times")));
        aggregationOperations.add(Aggregation.group(elementCodeFields).push("element_code").as("element_codes"));

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        List<Element> elements = mongoTemplate.aggregate(aggregation, "elements", Element.class).getMappedResults();
        return elements;
    }
}
