package com.pingchuan.mongodb.dao.impl;

import com.pingchuan.mongodb.dao.ElementValueDao;
import com.pingchuan.mongodb.field.ElementField;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ElementValueDaoImpl implements ElementValueDao {

    @Override
    public List<AggregationOperation> findById(String collectionName) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.lookup(collectionName, "element_value_id", "_id", "element_value"));
        aggregationOperations.add(Aggregation.unwind("element_value"));
        aggregationOperations.add(Aggregation.project(ElementField.elementValueFields).andExclude("_id"));
        return aggregationOperations;
    }

    @Override
    public List<AggregationOperation> findByThreshold(String collectionName, double[] threshold) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.lookup(collectionName, "element_value_id", "_id", "element_value"));
        aggregationOperations.add(Aggregation.unwind("element_value"));
        aggregationOperations.add(Aggregation.match(Criteria.where("element_value.value").gte(threshold[0]).lte(threshold[1])));
        aggregationOperations.add(Aggregation.project(ElementField.elementValueFields).andExclude("_id"));
        return aggregationOperations;
    }

    @Override
    public List<AggregationOperation> findReal(String collectionName) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.lookup(collectionName, "element_value_id", "_id", "element_value"));
        aggregationOperations.add(Aggregation.unwind("element_value"));
        aggregationOperations.add(Aggregation.project("real_time", "update_time", "element_code", "area_code", "grid_code", "loc", "area_name", "element_value.value").andExclude("_id"));
        return aggregationOperations;
    }
}
