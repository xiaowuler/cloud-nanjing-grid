package com.pingchuan.mongodb.dao;

import com.pingchuan.dto.base.AreaElement;
import com.pingchuan.parameter.base.AreaParameter;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import java.util.List;

public interface BaseSearchDao {
    List<AreaElement> findNJGridsByArea(List<AggregationOperation> aggregationOperations);

    List<AreaElement> findNJGridsByLocation(List<AggregationOperation> aggregationOperations);
}
