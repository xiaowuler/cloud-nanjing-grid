package com.pingchuan.mongodb.dao.impl;

import com.pingchuan.mongodb.dao.RealInfoDao;
import com.pingchuan.mongodb.field.ElementField;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class RealInfoDaoImpl implements RealInfoDao {

    @Override
    public List<AggregationOperation> findByStartTimeAndEndTimeAndElementCode(Date startRealTime, Date endRealTime, String elementCode) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.lookup("real_infos", "_id", "element_code", "real_info"));
        aggregationOperations.add(Aggregation.unwind("real_info"));
        aggregationOperations.add(Aggregation.match(Criteria.where("real_info.real_time").gte(startRealTime).lte(endRealTime)));
        aggregationOperations.add(Aggregation.project(ElementField.realInfoFields).and("real_info._id").as("real_info_id").andExclude("_id"));
        if (!StringUtils.isEmpty(elementCode)){
            aggregationOperations.add(Aggregation.match(Criteria.where("element_code").is(elementCode)));
        }

        return aggregationOperations;
    }
}
