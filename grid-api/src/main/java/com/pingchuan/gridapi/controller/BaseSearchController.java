package com.pingchuan.gridapi.controller;

import com.pingchuan.contants.ResultCode;
import com.pingchuan.dto.base.AreaElement;
import com.pingchuan.parameter.base.*;
import com.pingchuan.gridapi.annotation.BaseAction;
import com.pingchuan.gridapi.domain.ApiResponse;
import com.pingchuan.gridapi.service.mongo.BaseSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("baseSearch")
@RestController
public class BaseSearchController {

    @Autowired
    private BaseSearchService baseSearchService;

    @RequestMapping("/findNJGridsByArea")
    @BaseAction(apiId = 1, isNeedElementCode = true)
    public ApiResponse findNJGridsByArea(AreaParameter areaParameter){
        List<AreaElement> areaElements = baseSearchService.findNJGridsByArea(areaParameter);
        if (areaElements.size() == 0) {
            return new ApiResponse(ResultCode.NULL_VALUE, "未查询到值", null);
        }
        return new ApiResponse(ResultCode.SUCCESS, "查询成功", areaElements.get(0));
    }

    @RequestMapping("/findNJGridsByAreaAllElement")
    @BaseAction(apiId = 2, isNeedElementCode = false)
    public ApiResponse findNJGridsByAreaAllElement(AreaParameter areaParameter){
        List<AreaElement> areaElements = baseSearchService.findNJGridsByArea(areaParameter);
        if (areaElements.size() == 0) {
            return new ApiResponse(ResultCode.NULL_VALUE, "未查询到值", null);
        }
        return new ApiResponse(ResultCode.SUCCESS, "查询成功", areaElements.get(0));
    }

    @BaseAction(isNeedElementCode = true, apiId = 3)
    @RequestMapping("/findNJGridsByLocation")
    public ApiResponse findNJGridsByLocation(LocationParameter location){
        List<AreaElement> areaElements = baseSearchService.findNJGridsByLocation(location);
        if (areaElements.size() == 0) {
            return new ApiResponse(ResultCode.NULL_VALUE, "未查询到值", null);
        }
        return new ApiResponse(ResultCode.SUCCESS, "查询成功", areaElements.get(0));
    }

    @BaseAction(isNeedElementCode = false, apiId = 4)
    @RequestMapping("/findNJGridsByLocationAllElement")
    public ApiResponse findNJGridsByLocationAllElement(LocationParameter location){
        List<AreaElement> areaElements = baseSearchService.findNJGridsByLocation(location);
        if (areaElements.size() == 0) {
            return new ApiResponse(ResultCode.NULL_VALUE, "未查询到值", null);
        }
        return new ApiResponse(ResultCode.SUCCESS, "查询成功", areaElements.get(0));
    }

    @BaseAction(isNeedElementCode = true, apiId = 5)
    @RequestMapping("/findNJGridsByForecastTimeRange")
    public ApiResponse findNJGridsByForecastTimeRange(TimeRangeParameter timeRangeParameter){
        List<AreaElement> areaElements = baseSearchService.findNJGridsByForecastTimeRange(timeRangeParameter);
        if (areaElements.size() == 0) {
            return new ApiResponse(ResultCode.NULL_VALUE, "未查询到值", null);
        }
        return new ApiResponse(ResultCode.SUCCESS, "查询成功", areaElements.get(0));
    }

    @BaseAction(isNeedElementCode = false, apiId = 6)
    @RequestMapping("/findNJGridsByForecastTimeRangeAllElement")
    public ApiResponse findNJGridsByForecastTimeRangeAllElement(TimeRangeParameter timeRangeParameter){
        List<AreaElement> areaElements =baseSearchService.findNJGridsByForecastTimeRange(timeRangeParameter);
        if (areaElements.size() == 0) {
            return new ApiResponse(ResultCode.NULL_VALUE, "未查询到值", null);
        }
        return new ApiResponse(ResultCode.SUCCESS, "查询成功", areaElements.get(0));
    }

    @BaseAction(isNeedElementCode = true, apiId = 7)
    @RequestMapping("/findNJGridsByTimeEffect")
    public ApiResponse findNJGridsByTimeEffect(TimeEffectParameter timeEffectParameter){

        List<AreaElement> elements = baseSearchService.findNJGridsByTimeEffect(timeEffectParameter);
        if (elements.size() == 0) {
            return new ApiResponse(ResultCode.NULL_VALUE, "未查询到值", null);
        }
        return new ApiResponse(ResultCode.SUCCESS, "查询成功", elements.get(0));
    }

    @BaseAction(isNeedElementCode = false, apiId = 8)
    @RequestMapping("/findNJGridsByTimeEffectAllElement")
    public ApiResponse findNJGridsByTimeEffectAllElement(TimeEffectParameter timeEffectParameter){

        List<AreaElement> elements = baseSearchService.findNJGridsByTimeEffect(timeEffectParameter);
        if (elements.size() == 0) {
            return new ApiResponse(ResultCode.NULL_VALUE, "未查询到值", null);
        }
        return new ApiResponse(ResultCode.SUCCESS, "查询成功", elements.get(0));
    }

    @BaseAction(isNeedElementCode = true, apiId = 9)
    @RequestMapping("/findNJGridsByElementThresholdArea")
    public ApiResponse findNJGridsByElementThresholdArea(ThresholdAreaParameter thresholdAreaParameter){
        List<AreaElement> elements = baseSearchService.findNJGridsByElementThresholdArea(thresholdAreaParameter);
        if (elements.size() == 0) {
            return new ApiResponse(ResultCode.NULL_VALUE, "未查询到值", null);
        }
        return new ApiResponse(ResultCode.SUCCESS, "查询成功", elements);
    }

    @BaseAction(isNeedElementCode = true, apiId = 10)
    @RequestMapping("/findNJGridsByElementThresholdLocation")
    public ApiResponse findNJGridsByElementThresholdLocation(ThresholdLocationParameter thresholdLocationParameter){
        List<AreaElement> elements = baseSearchService.findNJGridsByElementThresholdLocation(thresholdLocationParameter);
        if (elements.size() == 0) {
            return new ApiResponse(ResultCode.NULL_VALUE, "未查询到值", null);
        }
        return new ApiResponse(ResultCode.SUCCESS, "查询成功", elements);
    }

}
