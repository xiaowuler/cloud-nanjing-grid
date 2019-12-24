package com.pingchuan.weatherservice.service;

import com.pingchuan.dto.base.AreaElement;
import com.pingchuan.parameter.base.AreaParameter;
import com.pingchuan.parameter.base.TimeRangeParameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name="provider-mongodb")
public interface BaseSearchService {
    @RequestMapping(value = "/baseSearch/findNJGridsByNonArea", method = RequestMethod.POST)
    List<AreaElement> findNJGridsByNonArea(AreaParameter areaParameter);

    @RequestMapping(value = "/baseSearch/findNJGridsByForecastTimeRange", method = RequestMethod.POST)
    List<AreaElement> findNJGridsByForecastTimeRange(TimeRangeParameter timeRangeParameter);
}
