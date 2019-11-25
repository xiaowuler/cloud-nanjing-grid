package com.pingchuan.mongodb.dao;

import com.pingchuan.dto.other.Element;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import java.util.List;

public interface OtherSearchDao {

    List<Element> findNewestTime(List<AggregationOperation> aggregationOperations);

    List<Element> findUpdateAndStartByDate(List<AggregationOperation> aggregationOperations);

    List<Element> findNewestTimeByForecastTime(List<AggregationOperation> aggregationOperations);
}
