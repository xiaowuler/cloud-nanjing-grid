package com.pingchuan.gridapi.controller;

import com.pingchuan.contants.ResultCode;
import com.pingchuan.dto.other.Element;
import com.pingchuan.gridapi.annotation.OtherAction;
import com.pingchuan.gridapi.domain.ApiResponse;
import com.pingchuan.gridapi.service.mongo.OtherSearchService;
import com.pingchuan.model.Trapezoid;
import com.pingchuan.parameter.other.ForecastTimeParameter;
import com.pingchuan.parameter.other.NewestParameter;
import com.pingchuan.parameter.other.TrapezoidParameter;
import com.pingchun.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("otherSearch")
public class OtherSearchController {

    @Autowired
    private OtherSearchService otherSearchService;

    @RequestMapping("/findNewestTime")
    @OtherAction(apiId = 30, isNeedTime = false)
    public ApiResponse findNewestTime(NewestParameter newest){
        List<Element> elements = otherSearchService.findNewestTime(newest);
        if (elements.size() == 0) {
            return new ApiResponse(ResultCode.NULL_VALUE, "未查询到值", null);
        }
        return new ApiResponse(ResultCode.SUCCESS, "查询成功", elements.get(0));
    }

    @RequestMapping("/findNewestTimeByDate")
    @OtherAction(apiId = 31, isNeedTime = true)
    public ApiResponse findNewestTimeByDate(NewestParameter newest){
        newest.setEndTime(TimeUtil.addDay(newest.getStartTime(), 1));
        List<Element> elementTimes = otherSearchService.findUpdateAndStartByDate(newest);
        if (elementTimes.size() == 0) {
            return new ApiResponse(ResultCode.NULL_VALUE, "未查询到值", null);
        }
        return new ApiResponse(ResultCode.SUCCESS, "查询成功", elementTimes.get(0));
    }

    @RequestMapping("/findNewestTimeByForecastTime")
    @OtherAction(apiId = 31, isNeedTime = true)
    public ApiResponse findNewestTimeByForecastTime(ForecastTimeParameter forecastTime){
        List<Element> elementTimes = otherSearchService.findNewestTimeByForecastTime(forecastTime);
        if (elementTimes.size() == 0) {
            return new ApiResponse(ResultCode.NULL_VALUE, "未查询到值", null);
        }
        return new ApiResponse(ResultCode.SUCCESS, "查询成功", elementTimes.get(0));
    }

    @RequestMapping("/findAllTrapezoid")
    @OtherAction(apiId = 33, isNeedTime = false)
    public ApiResponse findAllTrapezoid(TrapezoidParameter trapezoid){
        List<Trapezoid> trapezoids = otherSearchService.findAllTrapezoid(trapezoid);
        if (trapezoids.size() == 0) {
            return new ApiResponse(ResultCode.NULL_VALUE, "未查询到值", null);
        }
        return new ApiResponse(ResultCode.SUCCESS, "查询成功", trapezoids);
    }

    @RequestMapping("/findAllTrapezoidByAreaCode")
    @OtherAction(apiId = 34, isNeedTime = true)
    public ApiResponse findAllTrapezoidByAreaCode(TrapezoidParameter trapezoid){
        List<Trapezoid> trapezoids = otherSearchService.findAllTrapezoid(trapezoid);
        if (trapezoids.size() == 0) {
            return new ApiResponse(ResultCode.NULL_VALUE, "未查询到值", null);
        }
        return new ApiResponse(ResultCode.SUCCESS, "查询成功", trapezoids);
    }
}
