package com.pingchuan.mongodb.dao.impl;

import com.pingchuan.contants.CalcType;
import com.pingchuan.dto.calc.CalcElement;
import com.pingchuan.dto.calc.StatElement;
import com.pingchuan.mongodb.dao.CalcSearchDao;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.bind;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@Repository
public class CalcSearchDaoImpl implements CalcSearchDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<CalcElement> findMaxOrMinByArea(List<AggregationOperation> aggregationOperations, String calcType) {

        Fields locationFields = Fields.fields("start_time", "update_time", "forecast_model", "element_code", "forecast_time", "time_effect", "value").and("area_code").and("area_name");
        aggregationOperations.add(Aggregation.project(locationFields).and("location").nested(bind("loc", "loc").and("grid_code")));
        aggregationOperations.add(Aggregation.group(locationFields).push("location").as("locations"));

        aggregationOperations.add(Aggregation.sort("max".equals(calcType) ? Sort.Direction.DESC : Sort.Direction.ASC, "value"));
        Fields valueFields = Fields.fields("start_time", "update_time", "forecast_model", "element_code", "forecast_time", "time_effect").and("area_code").and("area_name");
        aggregationOperations.add(Aggregation.project(valueFields).and("value").nested(bind("value", "value").and("locations")));
        aggregationOperations.add(Aggregation.group(valueFields).first("value").as(calcType));

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        List<CalcElement> calcElements = mongoTemplate.aggregate(aggregation, "elements", CalcElement.class).getMappedResults();
        return calcElements;
    }

    @Override
    public List<CalcElement> findAvgByArea(List<AggregationOperation> aggregationOperations) {
        Fields valueFields = Fields.fields("start_time", "update_time", "forecast_model", "element_code", "forecast_time", "time_effect").and("area_code").and("area_name");
        aggregationOperations.add(Aggregation.group(valueFields).avg("value").as("avg"));

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        List<CalcElement> calcElements = mongoTemplate.aggregate(aggregation, "elements", CalcElement.class).getMappedResults();
        return calcElements;
    }

    @Override
    public List<CalcElement> findMaxOrMinByLocation(List<AggregationOperation> aggregationOperations, String calcType) {

        Fields forecastFields = Fields.fields("loc", "area_code", "grid_code", "area_name", "start_time", "update_time", "forecast_model", "element_code", "value");
        aggregationOperations.add(Aggregation.project(forecastFields).and("forecast").nested(bind("forecast_time", "forecast_time").and("time_effect")));
        aggregationOperations.add(Aggregation.group(forecastFields).push("forecast").as("forecasts"));

        aggregationOperations.add(Aggregation.sort("max".equals(calcType) ? Sort.Direction.DESC : Sort.Direction.ASC, "value"));
        Fields valueFields = Fields.fields("loc", "area_code", "grid_code", "area_name", "start_time", "update_time", "forecast_model", "element_code");
        aggregationOperations.add(Aggregation.project(valueFields).and("value").nested(bind("value", "value").and("forecasts")));
        aggregationOperations.add(Aggregation.group(valueFields).first("value").as(calcType));

        Fields locationFields = Fields.fields("start_time", "update_time", "forecast_model", "element_code");
        aggregationOperations.add(Aggregation.project(locationFields).and("location").nested(bind("loc", "loc").and("grid_code").and("area_name").and("area_code").and(calcType)));
        aggregationOperations.add(Aggregation.group(locationFields).push("location").as("locations"));
        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        List<CalcElement> calcElements = mongoTemplate.aggregate(aggregation, "elements", CalcElement.class).getMappedResults();
        return calcElements;
    }

    @Override
    public List<CalcElement> findAvgByLocation(List<AggregationOperation> aggregationOperations) {
        aggregationOperations.add(Aggregation.group("loc", "area_code","grid_code", "area_name", "start_time", "update_time", "forecast_model", "element_code").min("forecast_time").as("start_forecast_time").max("forecast_time").as("end_forecast_time").avg("value").as("avg"));
        Fields locationFields = Fields.fields("start_time", "update_time", "forecast_model", "element_code");
        aggregationOperations.add(Aggregation.project(locationFields).and("location").nested(bind("loc", "loc").and("area_code").and("area_name").and("grid_code").and("start_forecast_time").and("end_forecast_time").and("avg")));
        aggregationOperations.add(Aggregation.group(locationFields).push("location").as("locations"));

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        List<CalcElement> calcElements = mongoTemplate.aggregate(aggregation, "elements", CalcElement.class).getMappedResults();
        return calcElements;
    }

    @Override
    public List<StatElement> findAvgOrSumStat(List<AggregationOperation> aggregationOperations, String calcType, int timeInterval) {
        aggregationOperations.add(project("loc", "area_code", "grid_code", "area_name", "start_time", "update_time", "forecast_model", "element_code", "forecast_time", "time_effect", "value").and(DateOperators.dateOf("forecast_time").withTimezone(DateOperators.Timezone.valueOf("+08:00")).hour()).as("hour").and(DateOperators.dateOf("forecast_time").withTimezone(DateOperators.Timezone.valueOf("+08:00")).toString("%Y-%m-%d")).as("day").andExclude("_id"));

        aggregationOperations.add(Aggregation.project("loc", "area_code", "grid_code", "area_name", "start_time", "update_time", "forecast_model", "element_code", "forecast_time", "time_effect", "value", "day", "hour").and("hour").lt(timeInterval).as("is_midnight"));
        if (CalcType.avg.equals(calcType)){
            aggregationOperations.add(Aggregation.group("area_name", "loc", "grid_code", "start_time", "update_time", "forecast_model", "area_code", "element_code", "day", "is_midnight").avg("value").as("avg").max("forecast_time").as("end_forecast_time").min("forecast_time").as("start_forecast_time"));
            aggregationOperations.add(Aggregation.project("start_time", "update_time", "forecast_model", "element_code", "loc", "area_code", "area_name", "grid_code").and("value").nested(bind("avg", "avg").and("start_forecast_time").and("end_forecast_time")));
        }else {
            aggregationOperations.add(Aggregation.group("area_name", "loc", "grid_code", "start_time", "update_time", "forecast_model", "area_code", "element_code", "day", "is_midnight").sum("value").as("sum").max("forecast_time").as("end_forecast_time").min("forecast_time").as("start_forecast_time"));
            aggregationOperations.add(Aggregation.project("start_time", "update_time", "forecast_model", "element_code", "loc", "area_code", "area_name", "grid_code").and("value").nested(bind("sum", "sum").and("start_forecast_time").and("end_forecast_time")));
        }

        aggregationOperations.add(Aggregation.group("start_time", "update_time", "forecast_model", "element_code", "loc", "area_code", "area_name", "grid_code").push("value").as("values"));
        aggregationOperations.add(Aggregation.project("start_time", "update_time", "forecast_model", "element_code").and("location").nested(bind("values", "values").and("loc").and("area_code").and("area_name").and("grid_code")));
        aggregationOperations.add(Aggregation.group("start_time", "update_time", "forecast_model", "element_code").push("location").as("locations"));
        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        List<StatElement> statElements = mongoTemplate.aggregate(aggregation, "elements", StatElement.class).getMappedResults();
        return statElements;
    }

    @Override
    public List<StatElement> findMaxOrMinStat(List<AggregationOperation> aggregationOperations, String calcType, int timeInterval) {
        aggregationOperations.add(project("loc", "area_code", "grid_code", "area_name", "start_time", "update_time", "forecast_model", "element_code", "forecast_time", "time_effect", "value").and(DateOperators.dateOf("forecast_time").withTimezone(DateOperators.Timezone.valueOf("+08:00")).hour()).as("hour").and(DateOperators.dateOf("forecast_time").withTimezone(DateOperators.Timezone.valueOf("+08:00")).toString("%Y-%m-%d")).as("day").andExclude("_id"));

        Fields midnightFields = Fields.fields("loc", "area_code", "grid_code", "area_name", "start_time", "update_time", "forecast_model", "element_code", "forecast_time", "time_effect", "value", "day");
        aggregationOperations.add(Aggregation.project(midnightFields).and("hour").lt(timeInterval).as("is_midnight"));

        Fields forecastFields = Fields.fields("loc", "area_code", "grid_code", "area_name", "start_time", "update_time", "forecast_model", "element_code","value", "day", "is_midnight");
        aggregationOperations.add(Aggregation.project(forecastFields).and("forecast").nested(bind("forecast_time", "forecast_time").and("time_effect")));
        aggregationOperations.add(Aggregation.group(forecastFields).min("forecast.forecast_time").as("start_forecast_time").max("forecast.forecast_time").as("end_forecast_time").push("forecast").as("forecasts"));

        Fields valueFields = Fields.fields("loc", "area_code", "grid_code", "area_name", "start_time", "update_time", "forecast_model", "element_code", "day", "is_midnight");
        aggregationOperations.add((Aggregation.project(valueFields.and("start_forecast_time").and("end_forecast_time")).and("value").nested(bind("value", "value").and("forecasts"))));
        if (CalcType.max.equals(calcType)){
            aggregationOperations.add(Aggregation.group(valueFields).max("value").as("max").min("start_forecast_time").as("start_forecast_time").max("end_forecast_time").as("end_forecast_time"));
        }else {
            aggregationOperations.add(Aggregation.group(valueFields).min("value").as("min").min("start_forecast_time").as("start_forecast_time").max("end_forecast_time").as("end_forecast_time"));
        }

        Fields fields = Fields.fields("start_time", "update_time", "forecast_model", "element_code", "loc", "area_code", "grid_code", "area_name");
        aggregationOperations.add(Aggregation.project(fields).and("value").nested(bind("start_forecast_time", "start_forecast_time").and("end_forecast_time").and(calcType)));
        aggregationOperations.add(Aggregation.group(fields).push("value").as("values"));

        Fields locationFields = Fields.fields("start_time", "update_time", "forecast_model", "element_code");
        aggregationOperations.add(Aggregation.project(locationFields).and("location").nested(bind("loc", "loc").and("grid_code").and("area_code").and("area_name").and("values")));
        aggregationOperations.add(Aggregation.group(locationFields).push("location").as("locations"));

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        List<StatElement> statElements = mongoTemplate.aggregate(aggregation, "elements", StatElement.class).getMappedResults();
        return statElements;
    }
}
