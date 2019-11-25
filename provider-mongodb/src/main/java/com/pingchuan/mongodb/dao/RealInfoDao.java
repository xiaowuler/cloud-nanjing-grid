package com.pingchuan.mongodb.dao;

import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import java.util.Date;
import java.util.List;

public interface RealInfoDao {
    List<AggregationOperation> findByStartTimeAndEndTimeAndElementCode(Date startRealTime, Date endRealTime, String elementCode);
}
