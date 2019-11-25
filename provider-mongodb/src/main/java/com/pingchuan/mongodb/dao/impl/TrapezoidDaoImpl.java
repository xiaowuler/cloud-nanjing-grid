package com.pingchuan.mongodb.dao.impl;

import com.pingchuan.model.Trapezoid;
import com.pingchuan.mongodb.dao.TrapezoidDao;
import com.pingchuan.mongodb.field.ElementField;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrapezoidDaoImpl implements TrapezoidDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<AggregationOperation> findByAreaCode(String areaCode) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.lookup("trapezoids", "trapezoid_info_id", "trapezoid_info_id", "trapezoid"));
        aggregationOperations.add(Aggregation.unwind("trapezoid"));
        aggregationOperations.add(Aggregation.match(Criteria.where("trapezoid.area_code").is(areaCode)));
        aggregationOperations.add(Aggregation.project(ElementField.trapezoidFields));
        return aggregationOperations;
    }

    @Override
    public List<AggregationOperation> findByLocation(List<double[]> locations) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.lookup("trapezoids", "trapezoid_info_id", "trapezoid_info_id", "trapezoid"));
        aggregationOperations.add(Aggregation.unwind("trapezoid"));
        aggregationOperations.add(Aggregation.match(Criteria.where("trapezoid._id").in(findAllTrapezoidIdByLocation(locations))));
        aggregationOperations.add(Aggregation.project(ElementField.trapezoidFields));
        return aggregationOperations;
    }

    @Override
    public List<Trapezoid> findAllTrapezoid(String areaCode) {

        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        if(!StringUtils.isEmpty(areaCode)) {
            aggregationOperations.add(Aggregation.match(Criteria.where("area_code").is(areaCode)));
        };
        aggregationOperations.add(Aggregation.project("grid_code", "loc", "area_code", "area_name").andExclude("_id"));

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        return mongoTemplate.aggregate(aggregation, "trapezoids", Trapezoid.class).getMappedResults();
    }

    @Override
    public List<AggregationOperation> findRealByArea(String areaCode) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.lookup("trapezoids", "trapezoid_info_id", "trapezoid_info_id", "trapezoid"));
        aggregationOperations.add(Aggregation.unwind("trapezoid"));
        aggregationOperations.add(Aggregation.match(Criteria.where("trapezoid.area_code").is(areaCode)));
        aggregationOperations.add(Aggregation.project("real_time", "update_time", "element_code", "trapezoid.area_code", "trapezoid.grid_code", "trapezoid.loc", "trapezoid.area_name").and("$real_info_id").concat("$trapezoid.grid_code").as("element_value_id"));

        return aggregationOperations;
    }

    @Override
    public List<AggregationOperation> findRealByLocation(List<double[]> locations) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.lookup("trapezoids", "trapezoid_info_id", "trapezoid_info_id", "trapezoid"));
        aggregationOperations.add(Aggregation.unwind("trapezoid"));
        aggregationOperations.add(Aggregation.match(Criteria.where("trapezoid._id").in(findAllTrapezoidIdByLocation(locations))));
        aggregationOperations.add(Aggregation.project("real_time", "update_time", "element_code", "trapezoid.area_code", "trapezoid.grid_code", "trapezoid.loc", "trapezoid.area_name").and("$real_info_id").concat("$trapezoid.grid_code").as("element_value_id"));
        return aggregationOperations;
    }

    private List<ObjectId> findAllTrapezoidIdByLocation(List<double[]> locations){
        List<ObjectId> trapezoidIds = new ArrayList<>();
        for (double[] loc : locations) {
            if (StringUtils.isEmpty(loc)) {
                continue;
            }

            List<AggregationOperation> aggregationOperations = new ArrayList<>();
            aggregationOperations.add(Aggregation.geoNear(NearQuery.near(loc[0], loc[1]).spherical(true), "distance"));
            aggregationOperations.add(Aggregation.limit(1));
            aggregationOperations.add(Aggregation.project("_id"));

            Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
            List<Trapezoid> trapezoids = mongoTemplate.aggregate(aggregation, "trapezoids", Trapezoid.class).getMappedResults();
            trapezoids.forEach(t -> trapezoidIds.add(t.getId()));
        }

        return trapezoidIds;
    }
}
