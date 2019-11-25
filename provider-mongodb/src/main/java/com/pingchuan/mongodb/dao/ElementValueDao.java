package com.pingchuan.mongodb.dao;

import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import java.util.List;

public interface ElementValueDao {
    List<AggregationOperation> findById(String collectionName);

    List<AggregationOperation> findByThreshold(String covertDateToString, double[] threshold);

    List<AggregationOperation> findReal(String collectionName);
}
