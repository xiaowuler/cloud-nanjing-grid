package com.pingchuan.mongodb.dao;

import com.pingchuan.dto.real.Element;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import java.util.List;

public interface RealSearchDao {
    List<Element> findRealNJGridsByArea(List<AggregationOperation> aggregationOperations);

    List<Element> findRealNJGridsByLocation(List<AggregationOperation> aggregationOperations);
}
