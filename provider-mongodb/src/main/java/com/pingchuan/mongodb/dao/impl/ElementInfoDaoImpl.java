package com.pingchuan.mongodb.dao.impl;

import com.pingchuan.mongodb.dao.ElementInfoDao;
import com.pingchuan.mongodb.field.ElementField;
import com.pingchun.utils.TimeUtil;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiaowuler
 */

@Repository
public class ElementInfoDaoImpl implements ElementInfoDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<AggregationOperation> findByUpdateTimeAndStartTimeAndElementCodeAndForecastModel(Date updateTime, Date startTime, String elementCode, String forecastModel) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();

        aggregationOperations.add(Aggregation.lookup("element_infos", "_id", "element_code", "element_info"));
        aggregationOperations.add(Aggregation.unwind("element_info"));
        aggregationOperations.add(Aggregation.match(Criteria.where("element_info.update_time").is(updateTime).and("element_info.start_time").is(startTime).and("element_info.forecast_model").is(forecastModel)));
        aggregationOperations.add(Aggregation.project(ElementField.elementInfoFields).and("element_info._id").as("element_info_id").andExclude("_id"));
        if (!StringUtils.isEmpty(elementCode)){
            aggregationOperations.add(Aggregation.match(Criteria.where("element_code").is(elementCode)));
        }

        return aggregationOperations;
    }

    @Override
    public List<AggregationOperation> findByForecastModel(String forecastModel, String elementCode) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();

        aggregationOperations.add(Aggregation.lookup("element_infos", "_id", "element_code", "element_info"));
        aggregationOperations.add(Aggregation.unwind("element_info"));
        aggregationOperations.add(Aggregation.match(Criteria.where("element_info.forecast_model").is(forecastModel).and("element_info.update_time").gte(TimeUtil.addDay(new Date(), -3))));
        aggregationOperations.add(Aggregation.project(ElementField.elementInfoFields).and("element_info._id").as("element_info_id").andExclude("_id"));
        if (!StringUtils.isEmpty(elementCode)){
            aggregationOperations.add(Aggregation.match(Criteria.where("element_code").is(elementCode)));
        }

        return aggregationOperations;
    }

    @Override
    public List<AggregationOperation> findByForecastModelAndDate(String forecastModel, Date startTime, Date endTime, String elementCode) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();

        aggregationOperations.add(Aggregation.lookup("element_infos", "_id", "element_code", "element_info"));
        aggregationOperations.add(Aggregation.unwind("element_info"));
        aggregationOperations.add(Aggregation.match(Criteria.where("element_info.forecast_model").is(forecastModel).and("element_info.start_time").gte(startTime).lte(endTime)));
        aggregationOperations.add(Aggregation.project(ElementField.elementInfoFields).and("element_info._id").as("element_info_id").andExclude("_id"));
        if (!StringUtils.isEmpty(elementCode)) {
            aggregationOperations.add(Aggregation.match(Criteria.where("element_code").is(elementCode)));
        }

        return aggregationOperations;
    }
}
