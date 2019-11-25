package com.pingchuan.mongodb.service;

import com.pingchuan.dto.other.Element;
import com.pingchuan.model.Trapezoid;
import com.pingchuan.parameter.other.ForecastTimeParameter;
import com.pingchuan.parameter.other.NewestParameter;
import com.pingchuan.parameter.other.TrapezoidParameter;

import java.util.List;

public interface OtherSearchSerive {
    List<Element> findNewestTime(NewestParameter newest);

    List<Element> findUpdateAndStartByDate(NewestParameter newestParameter);

    List<Element> findNewestTimeByForecastTime(ForecastTimeParameter forecastTime);

    List<Trapezoid> findAllTrapezoid(TrapezoidParameter trapezoidParameter);
}
