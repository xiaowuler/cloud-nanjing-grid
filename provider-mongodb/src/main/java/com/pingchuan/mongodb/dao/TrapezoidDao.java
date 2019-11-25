package com.pingchuan.mongodb.dao;

import com.pingchuan.model.Trapezoid;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import java.util.List;

public interface TrapezoidDao {

    List<AggregationOperation> findByAreaCode(String areaCode);

    List<AggregationOperation> findByLocation(List<double[]> locations);

    List<Trapezoid> findAllTrapezoid(String areaCode);

    List<AggregationOperation> findRealByArea(String areaCode);

    List<AggregationOperation> findRealByLocation(List<double[]> locations);
}
