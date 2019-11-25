package com.pingchuan.mongodb.service.impl;

import com.pingchuan.contants.CalcType;
import com.pingchuan.contants.TimeFormat;
import com.pingchuan.dto.calc.CalcElement;
import com.pingchuan.dto.calc.StatElement;
import com.pingchuan.mongodb.dao.*;
import com.pingchuan.mongodb.service.CalcSearchService;
import com.pingchuan.parameter.calc.RoutineParameter;
import com.pingchuan.parameter.calc.SpecialParameter;
import com.pingchuan.parameter.calc.TimeEffectParameter;
import com.pingchuan.parameter.calc.TimeRangeParameter;
import com.pingchun.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalcSearchServiceImpl implements CalcSearchService {

    @Autowired
    private ElementInfoDao elementInfoDao;

    @Autowired
    private TrapezoidDao trapezoidDao;

    @Autowired
    private ForecastInfoDao forecastInfoDao;

    @Autowired
    private ElementValueDao elementValueDao;

    @Autowired
    private CalcSearchDao calcSearchDao;

    @Override
    public List<CalcElement> findMaxOrMinAndAvgByArea(RoutineParameter routineParameter) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        List<AggregationOperation> elementInfos = elementInfoDao.findByUpdateTimeAndStartTimeAndElementCodeAndForecastModel(routineParameter.getUpdateDate(), routineParameter.getStartDate(), routineParameter.getElementCode(), routineParameter.getForecastModel());
        aggregationOperations.addAll(elementInfos);
        List<AggregationOperation> trapezoids = trapezoidDao.findByAreaCode(routineParameter.getAreaCode());
        aggregationOperations.addAll(trapezoids);
        List<AggregationOperation> forecastInfos = forecastInfoDao.findByForecastTime(routineParameter.getForecastDate());
        aggregationOperations.addAll(forecastInfos);
        List<AggregationOperation> elementValues = elementValueDao.findById(TimeUtil.CovertDateToString(TimeFormat.ELEMENT_VALUES_NAME, routineParameter.getStartDate()));
        aggregationOperations.addAll(elementValues);

        if (CalcType.avg.equals(routineParameter.getCalcType())){
            return calcSearchDao.findAvgByArea(aggregationOperations);
        }
        return calcSearchDao.findMaxOrMinByArea(aggregationOperations, routineParameter.getCalcType());
    }

    @Override
    public List<CalcElement> findMaxOrMinAndMaxByTimeEffectAndArea(TimeEffectParameter timeEffectParameter) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        List<AggregationOperation> elementInfos = elementInfoDao.findByUpdateTimeAndStartTimeAndElementCodeAndForecastModel(timeEffectParameter.getUpdateDate(), timeEffectParameter.getStartDate(), timeEffectParameter.getElementCode(), timeEffectParameter.getForecastModel());
        aggregationOperations.addAll(elementInfos);
        List<AggregationOperation> trapezoids = trapezoidDao.findByAreaCode(timeEffectParameter.getAreaCode());
        aggregationOperations.addAll(trapezoids);
        List<AggregationOperation> forecastInfos = forecastInfoDao.findByForecastTime(timeEffectParameter.getForecastDate());
        aggregationOperations.addAll(forecastInfos);
        List<AggregationOperation> elementValues = elementValueDao.findById(TimeUtil.CovertDateToString(TimeFormat.ELEMENT_VALUES_NAME, timeEffectParameter.getStartDate()));
        aggregationOperations.addAll(elementValues);

        if (CalcType.avg.equals(timeEffectParameter.getCalcType())){
            return calcSearchDao.findAvgByArea(aggregationOperations);
        }
        return calcSearchDao.findMaxOrMinByArea(aggregationOperations, timeEffectParameter.getCalcType());
    }

    @Override
    public List<CalcElement> findMaxOrMinAndAvgByLocation(TimeRangeParameter timeRange) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        List<AggregationOperation> elementInfos = elementInfoDao.findByUpdateTimeAndStartTimeAndElementCodeAndForecastModel(timeRange.getUpdateDate(), timeRange.getStartDate(), timeRange.getElementCode(), timeRange.getForecastModel());
        aggregationOperations.addAll(elementInfos);
        List<AggregationOperation> trapezoids = trapezoidDao.findByLocation(timeRange.getLocations());
        aggregationOperations.addAll(trapezoids);
        List<AggregationOperation> forecastInfos = forecastInfoDao.findByForecastTimeRange(timeRange.getStartForecastDate(), timeRange.getEndForecastDate());
        aggregationOperations.addAll(forecastInfos);
        List<AggregationOperation> elementValues = elementValueDao.findById(TimeUtil.CovertDateToString(TimeFormat.ELEMENT_VALUES_NAME, timeRange.getStartDate()));
        aggregationOperations.addAll(elementValues);

        if (CalcType.avg.equals(timeRange.getCalcType())){
            return calcSearchDao.findAvgByLocation(aggregationOperations);
        }
        return calcSearchDao.findMaxOrMinByLocation(aggregationOperations, timeRange.getCalcType());
    }

    @Override
    public List<StatElement> findSpecialStat(SpecialParameter specialParameter) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        List<AggregationOperation> elementInfos = elementInfoDao.findByUpdateTimeAndStartTimeAndElementCodeAndForecastModel(specialParameter.getUpdateDate(), specialParameter.getStartDate(), specialParameter.getElementCode(), specialParameter.getForecastModel());
        aggregationOperations.addAll(elementInfos);
        List<AggregationOperation> trapezoids = trapezoidDao.findByLocation(specialParameter.getLocations());
        aggregationOperations.addAll(trapezoids);
        List<AggregationOperation> forecastInfos = forecastInfoDao.findAll();
        aggregationOperations.addAll(forecastInfos);
        List<AggregationOperation> elementValues = elementValueDao.findById(TimeUtil.CovertDateToString(TimeFormat.ELEMENT_VALUES_NAME, specialParameter.getStartDate()));
        aggregationOperations.addAll(elementValues);

        if (CalcType.avg.equals(specialParameter.getCalcType()) || CalcType.sum.equals(specialParameter.getCalcType())){
            return calcSearchDao.findAvgOrSumStat(aggregationOperations, specialParameter.getCalcType(), specialParameter.getTimeInterval());
        }

        return calcSearchDao.findMaxOrMinStat(aggregationOperations, specialParameter.getCalcType(), specialParameter.getTimeInterval());
    }
}
