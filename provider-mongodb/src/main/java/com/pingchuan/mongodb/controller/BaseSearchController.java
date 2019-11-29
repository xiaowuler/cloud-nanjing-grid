package com.pingchuan.mongodb.controller;

import com.pingchuan.dto.base.AreaElement;
import com.pingchuan.parameter.base.*;
import com.pingchuan.mongodb.service.BaseSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("baseSearch")
@RestController
public class BaseSearchController {

    @Autowired
    private BaseSearchService baseSearchService;

    @PostMapping("/findNJGridsByArea")
    public List<AreaElement> findNJGridsByArea(@RequestBody AreaParameter areaParameter){
        return baseSearchService.findNJGridsByArea(areaParameter);
    }

    @PostMapping("/findNJGridsByLocation")
    public List<AreaElement> findNJGridsByLocation(@RequestBody LocationParameter locationParameter){
        return baseSearchService.findNJGridsByLocation(locationParameter);
    }

    @PostMapping("/findNJGridsByForecastTimeRange")
    public List<AreaElement> findNJGridsByForecastTimeRange(@RequestBody TimeRangeParameter timeRangeParameter){
        return baseSearchService.findNJGridsByForecastTimeRange(timeRangeParameter);
    }

    @PostMapping("/findNJGridsByTimeEffect")
    public List<AreaElement> findNJGridsByTimeEffect(@RequestBody TimeEffectParameter timeEffectParameter){
        return baseSearchService.findNJGridsByTimeEffect(timeEffectParameter);
    }

    @PostMapping("/findNJGridsByElementThresholdArea")
    public List<AreaElement> findNJGridsByElementThresholdArea(@RequestBody ThresholdAreaParameter thresholdAreaParameter){
        return baseSearchService.findNJGridsByElementThresholdArea(thresholdAreaParameter);
    }

    @PostMapping("/findNJGridsByElementThresholdLocation")
    public List<AreaElement> findNJGridsByElementThresholdLocation(@RequestBody ThresholdLocationParameter thresholdLocationParameter){
        return baseSearchService.findNJGridsByElementThresholdLocation(thresholdLocationParameter);
    }
}
