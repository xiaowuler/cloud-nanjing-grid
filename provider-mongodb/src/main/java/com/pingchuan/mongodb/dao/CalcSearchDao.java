package com.pingchuan.mongodb.dao;

import com.pingchuan.dto.calc.CalcElement;
import com.pingchuan.dto.calc.StatElement;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import java.util.List;

public interface CalcSearchDao {
    List<CalcElement> findMaxOrMinByArea(List<AggregationOperation> aggregationOperations, String calcType);

    List<CalcElement> findAvgByArea(List<AggregationOperation> aggregationOperations);

    List<CalcElement> findMaxOrMinByLocation(List<AggregationOperation> aggregationOperations, String calcType);

    List<CalcElement> findAvgByLocation(List<AggregationOperation> aggregationOperations);

    List<StatElement> findAvgOrSumStat(List<AggregationOperation> aggregationOperations, String calcType, int timeInterval);

    List<StatElement> findMaxOrMinStat(List<AggregationOperation> aggregationOperations, String calcType, int timeInterval);
}
