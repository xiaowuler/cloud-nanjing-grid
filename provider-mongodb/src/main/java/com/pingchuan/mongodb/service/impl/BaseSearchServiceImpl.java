package com.pingchuan.mongodb.service.impl;

import com.pingchuan.contants.TimeFormat;
import com.pingchuan.dto.base.AreaElement;
import com.pingchuan.parameter.base.*;
import com.pingchuan.mongodb.dao.*;
import com.pingchuan.mongodb.service.BaseSearchService;
import com.pingchun.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class BaseSearchServiceImpl implements BaseSearchService {

    @Autowired
    private ElementInfoDao elementInfoDao;

    @Autowired
    private TrapezoidDao trapezoidDao;

    @Autowired
    private ForecastInfoDao forecastInfoDao;

    @Autowired
    private ElementValueDao elementValueDao;

    @Autowired
    private BaseSearchDao baseSearchDao;

    @Override
    public List<AreaElement> findNJGridsByArea(AreaParameter areaParameter) {

        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        List<AggregationOperation> elementInfos = elementInfoDao.findByUpdateTimeAndStartTimeAndElementCodeAndForecastModel(areaParameter.getUpdateDate(), areaParameter.getStartDate(), areaParameter.getElementCode(), areaParameter.getForecastModel());
        aggregationOperations.addAll(elementInfos);
        List<AggregationOperation> trapezoids = trapezoidDao.findByAreaCode(areaParameter.getAreaCode());
        aggregationOperations.addAll(trapezoids);
        List<AggregationOperation> forecastInfos = forecastInfoDao.findByForecastTime(areaParameter.getForecastDate());
        aggregationOperations.addAll(forecastInfos);
        List<AggregationOperation> elementValues = elementValueDao.findById(TimeUtil.CovertDateToString(TimeFormat.ELEMENT_VALUES_NAME, areaParameter.getStartDate()));
        aggregationOperations.addAll(elementValues);

        return baseSearchDao.findNJGridsByArea(aggregationOperations);
    }

    @Override
    public List<AreaElement> findNJGridsByLocation(LocationParameter location) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        List<AggregationOperation> elementInfos = elementInfoDao.findByUpdateTimeAndStartTimeAndElementCodeAndForecastModel(location.getUpdateDate(), location.getStartDate(), location.getElementCode(), location.getForecastModel());
        aggregationOperations.addAll(elementInfos);
        List<AggregationOperation> trapezoids = trapezoidDao.findByLocation(location.getLocations());
        aggregationOperations.addAll(trapezoids);
        List<AggregationOperation> forecastInfos = forecastInfoDao.findByForecastTime(location.getForecastDate());
        aggregationOperations.addAll(forecastInfos);
        List<AggregationOperation> elementValues = elementValueDao.findById(TimeUtil.CovertDateToString(TimeFormat.ELEMENT_VALUES_NAME, location.getStartDate()));
        aggregationOperations.addAll(elementValues);

        return baseSearchDao.findNJGridsByLocation(aggregationOperations);
    }

    @Override
    public List<AreaElement> findNJGridsByForecastTimeRange(TimeRangeParameter timeRangeParameter) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        List<AggregationOperation> elementInfos = elementInfoDao.findByUpdateTimeAndStartTimeAndElementCodeAndForecastModel(timeRangeParameter.getUpdateDate(), timeRangeParameter.getStartDate(), timeRangeParameter.getElementCode(), timeRangeParameter.getForecastModel());
        aggregationOperations.addAll(elementInfos);
        List<AggregationOperation> trapezoids = trapezoidDao.findByLocation(timeRangeParameter.getLocations());
        aggregationOperations.addAll(trapezoids);
        List<AggregationOperation> forecastInfos = forecastInfoDao.findByForecastTimeRange(timeRangeParameter.getStartForecastDate(), timeRangeParameter.getEndForecastDate());
        aggregationOperations.addAll(forecastInfos);
        List<AggregationOperation> elementValues = elementValueDao.findById(TimeUtil.CovertDateToString(TimeFormat.ELEMENT_VALUES_NAME, timeRangeParameter.getStartDate()));
        aggregationOperations.addAll(elementValues);

        return baseSearchDao.findNJGridsByArea(aggregationOperations);
    }

    @Override
    public List<AreaElement> findNJGridsByTimeEffect(TimeEffectParameter timeEffectParameter) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        List<AggregationOperation> elementInfos = elementInfoDao.findByUpdateTimeAndStartTimeAndElementCodeAndForecastModel(timeEffectParameter.getUpdateDate(), timeEffectParameter.getStartDate(), timeEffectParameter.getElementCode(), timeEffectParameter.getForecastModel());
        aggregationOperations.addAll(elementInfos);
        List<AggregationOperation> trapezoids = trapezoidDao.findByAreaCode(timeEffectParameter.getAreaCode());
        aggregationOperations.addAll(trapezoids);
        List<AggregationOperation> forecastInfos = forecastInfoDao.findByForecastTime(timeEffectParameter.getForecastDate());
        aggregationOperations.addAll(forecastInfos);
        List<AggregationOperation> elementValues = elementValueDao.findById(TimeUtil.CovertDateToString(TimeFormat.ELEMENT_VALUES_NAME, timeEffectParameter.getStartDate()));
        aggregationOperations.addAll(elementValues);

        return baseSearchDao.findNJGridsByArea(aggregationOperations);
    }

    @Override
    public List<AreaElement> findNJGridsByElementThresholdArea(ThresholdAreaParameter thresholdAreaParameter) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        List<AggregationOperation> elementInfos = elementInfoDao.findByUpdateTimeAndStartTimeAndElementCodeAndForecastModel(thresholdAreaParameter.getUpdateDate(), thresholdAreaParameter.getStartDate(), thresholdAreaParameter.getElementCode(), thresholdAreaParameter.getForecastModel());
        aggregationOperations.addAll(elementInfos);
        List<AggregationOperation> trapezoids = trapezoidDao.findByAreaCode(thresholdAreaParameter.getAreaCode());
        aggregationOperations.addAll(trapezoids);
        List<AggregationOperation> forecastInfos = forecastInfoDao.findByForecastTime(thresholdAreaParameter.getForecastDate());
        aggregationOperations.addAll(forecastInfos);
        return getThreshold(thresholdAreaParameter.getThresholdValues(), aggregationOperations, TimeUtil.CovertDateToString(TimeFormat.ELEMENT_VALUES_NAME, thresholdAreaParameter.getStartDate()), true);
    }

    private List<AreaElement> getThreshold(List<double[]> thresholds, List<AggregationOperation> infos, String collectionName, boolean isArea){
        List<AreaElement> areaElements = new ArrayList<>();

        for (double[] threshold : thresholds){

            if (StringUtils.isEmpty(threshold)) {
                continue;
            }

            List<AggregationOperation> aggregationOperations = new ArrayList<>();
            aggregationOperations.addAll(infos);
            List<AggregationOperation> elementValues = elementValueDao.findByThreshold(collectionName, threshold);
            aggregationOperations.addAll(elementValues);

            List<AreaElement> elements;
            if (isArea){
                elements = baseSearchDao.findNJGridsByArea(aggregationOperations);
            }else {
                elements = baseSearchDao.findNJGridsByLocation(aggregationOperations);
            }

            if (elements.size() == 0){
                continue;
            }
            AreaElement areaElement = elements.get(0);
            areaElement.setThreshold(threshold);
            areaElements.add(areaElement);
        }

        return areaElements;
    }

    @Override
    public List<AreaElement> findNJGridsByElementThresholdLocation(ThresholdLocationParameter thresholdLocationParameter) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        List<AggregationOperation> elementInfos = elementInfoDao.findByUpdateTimeAndStartTimeAndElementCodeAndForecastModel(thresholdLocationParameter.getUpdateDate(), thresholdLocationParameter.getStartDate(), thresholdLocationParameter.getElementCode(), thresholdLocationParameter.getForecastModel());
        aggregationOperations.addAll(elementInfos);
        List<AggregationOperation> trapezoids = trapezoidDao.findByLocation(thresholdLocationParameter.getLocations());
        aggregationOperations.addAll(trapezoids);
        List<AggregationOperation> forecastInfos = forecastInfoDao.findByForecastTime(thresholdLocationParameter.getForecastDate());
        aggregationOperations.addAll(forecastInfos);
        return getThreshold(thresholdLocationParameter.getThresholdValues(), aggregationOperations, TimeUtil.CovertDateToString(TimeFormat.ELEMENT_VALUES_NAME, thresholdLocationParameter.getStartDate()), false);

    }

    @Override
    public List<AreaElement> findNJGridsByNonArea(AreaParameter areaParameter) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        List<AggregationOperation> elementInfos = elementInfoDao.findByUpdateTimeAndStartTimeAndElementCodeAndForecastModel(areaParameter.getUpdateDate(), areaParameter.getStartDate(), areaParameter.getElementCode(), areaParameter.getForecastModel());
        aggregationOperations.addAll(elementInfos);
        List<AggregationOperation> trapezoids = trapezoidDao.findByNonAreaCode();
        aggregationOperations.addAll(trapezoids);
        List<AggregationOperation> forecastInfos = forecastInfoDao.findByForecastTime(areaParameter.getForecastDate());
        aggregationOperations.addAll(forecastInfos);
        List<AggregationOperation> elementValues = elementValueDao.findById(TimeUtil.CovertDateToString(TimeFormat.ELEMENT_VALUES_NAME, areaParameter.getStartDate()));
        aggregationOperations.addAll(elementValues);

        return baseSearchDao.findNJGridsByArea(aggregationOperations);
    }
}
