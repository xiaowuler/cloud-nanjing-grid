package com.pingchuan.gridapi.service.mongo;

import com.pingchuan.dto.base.AreaElement;
import com.pingchuan.parameter.base.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name="provider-mongodb")
public interface BaseSearchService {
    @RequestMapping(value = "/baseSearch/findNJGridsByArea", method = RequestMethod.POST)
    List<AreaElement> findNJGridsByArea(AreaParameter areaParameter);

    @RequestMapping(value = "/baseSearch/findNJGridsByLocation", method = RequestMethod.POST)
    List<AreaElement> findNJGridsByLocation(LocationParameter location);

    @RequestMapping(value = "/baseSearch/findNJGridsByForecastTimeRange", method = RequestMethod.POST)
    List<AreaElement> findNJGridsByForecastTimeRange(TimeRangeParameter timeRangeParameter);

    @RequestMapping(value = "/baseSearch/findNJGridsByTimeEffect", method = RequestMethod.POST)
    List<AreaElement> findNJGridsByTimeEffect(TimeEffectParameter timeEffectParameter);

    @RequestMapping(value = "/baseSearch/findNJGridsByElementThresholdArea", method = RequestMethod.POST)
    List<AreaElement> findNJGridsByElementThresholdArea(ThresholdAreaParameter thresholdAreaParameter);

    @RequestMapping(value = "/baseSearch/findNJGridsByElementThresholdLocation", method = RequestMethod.POST)
    List<AreaElement> findNJGridsByElementThresholdLocation(ThresholdLocationParameter thresholdLocationParameter);
}
