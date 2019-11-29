package com.pingchuan.mongodb.service.impl;

import com.pingchuan.dto.other.Element;
import com.pingchuan.model.Trapezoid;
import com.pingchuan.mongodb.dao.ElementInfoDao;
import com.pingchuan.mongodb.dao.ForecastInfoDao;
import com.pingchuan.mongodb.dao.OtherSearchDao;
import com.pingchuan.mongodb.dao.TrapezoidDao;
import com.pingchuan.mongodb.service.OtherSearchSerive;
import com.pingchuan.parameter.other.ForecastTimeParameter;
import com.pingchuan.parameter.other.NewestParameter;
import com.pingchuan.parameter.other.TrapezoidParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OtherSearchServiceImpl implements OtherSearchSerive {

    @Autowired
    private ElementInfoDao elementInfoDao;

    @Autowired
    private ForecastInfoDao forecastInfoDao;

    @Autowired
    private OtherSearchDao otherSearchDao;

    @Autowired
    private TrapezoidDao trapezoidDao;

    @Override
    public List<Element> findNewestTime(NewestParameter newest) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        List<AggregationOperation> elementInfos = elementInfoDao.findByForecastModel(newest.getForecastModel(), newest.getElementCode());

        aggregationOperations.addAll(elementInfos);
        List<AggregationOperation> forecastInfos = forecastInfoDao.findAllByNewest();
        aggregationOperations.addAll(forecastInfos);

        return otherSearchDao.findNewestTime(aggregationOperations);
    }

    @Override
    public List<Element> findUpdateAndStartByDate(NewestParameter newestParameter) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        List<AggregationOperation> elementInfos = elementInfoDao.findByForecastModelAndDate(newestParameter.getForecastModel(), newestParameter.getStartTime(), newestParameter.getEndTime());
        aggregationOperations.addAll(elementInfos);
        List<AggregationOperation> forecastInfos = forecastInfoDao.findAllByNewest();
        aggregationOperations.addAll(forecastInfos);

        return otherSearchDao.findUpdateAndStartByDate(aggregationOperations);
    }

    @Override
    public List<Element> findNewestTimeByForecastTime(ForecastTimeParameter forecastTime) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        List<AggregationOperation> elementInfos = elementInfoDao.findByForecastModel(forecastTime.getForecastModel(), null);
        aggregationOperations.addAll(elementInfos);
        List<AggregationOperation> forecastInfos = forecastInfoDao.findAllByForecastTime(forecastTime.getForecastDate());
        aggregationOperations.addAll(forecastInfos);

        return otherSearchDao.findNewestTimeByForecastTime(aggregationOperations);
    }

    @Override
    public List<Trapezoid> findAllTrapezoid(TrapezoidParameter trapezoidParameter) {
        return trapezoidDao.findAllTrapezoid(trapezoidParameter.getAreaCode());
    }
}
