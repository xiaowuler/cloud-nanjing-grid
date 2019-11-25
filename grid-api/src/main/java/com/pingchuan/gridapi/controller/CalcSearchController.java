package com.pingchuan.gridapi.controller;

import com.pingchuan.contants.ResultCode;
import com.pingchuan.dto.calc.CalcElement;
import com.pingchuan.dto.calc.StatElement;
import com.pingchuan.gridapi.annotation.CalcAction;
import com.pingchuan.gridapi.domain.ApiResponse;
import com.pingchuan.gridapi.service.mongo.CalcSearchService;
import com.pingchuan.parameter.calc.RoutineParameter;
import com.pingchuan.parameter.calc.SpecialParameter;
import com.pingchuan.parameter.calc.TimeEffectParameter;
import com.pingchuan.parameter.calc.TimeRangeParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("calcSearch")
public class CalcSearchController {

    @Autowired
    private CalcSearchService calcSearchService;

    @CalcAction(apiId = 11, isArea = true, calcType = "max")
    @RequestMapping("/findMaxElementValueByArea")
    public ApiResponse findMaxElementValueByArea(RoutineParameter routineParameter){
        List<CalcElement> calcElements = calcSearchService.findMaxOrMinAndAvgByArea(routineParameter);
        return getRoutineResult(calcElements);
    }

    @CalcAction(apiId = 12, isArea = true, calcType = "min")
    @RequestMapping("/findMinElementValueByArea")
    public ApiResponse findMinElementValueByArea(RoutineParameter routineParameter){
        List<CalcElement> calcElements = calcSearchService.findMaxOrMinAndAvgByArea(routineParameter);
        return getRoutineResult(calcElements);
    }

    @CalcAction(apiId = 13, isArea = true, calcType = "avg")
    @RequestMapping("/findAvgElementValueByArea")
    public ApiResponse findAvgElementValueByArea(RoutineParameter routineParameter){
        List<CalcElement> calcElements = calcSearchService.findMaxOrMinAndAvgByArea(routineParameter);
        return getRoutineResult(calcElements);
    }

    @CalcAction(apiId = 14, isArea = true, calcType = "max")
    @RequestMapping("/findMaxElementValueTimeEffectByArea")
    public ApiResponse findMaxElementValueTimeEffectByArea(TimeEffectParameter timeEffectParameter){
        List<CalcElement> calcElements = calcSearchService.findMaxOrMinAndMaxByTimeEffectAndArea(timeEffectParameter);
        return getRoutineResult(calcElements);
    }

    @CalcAction(apiId = 15, isArea = true, calcType = "min")
    @RequestMapping("/findMinElementValueTimeEffectByArea")
    public ApiResponse findMinElementValueTimeEffectByArea(TimeEffectParameter timeEffectParameter){
        List<CalcElement> calcElements = calcSearchService.findMaxOrMinAndMaxByTimeEffectAndArea(timeEffectParameter);
        return getRoutineResult(calcElements);
    }

    @CalcAction(apiId = 16, isArea = true, calcType = "avg")
    @RequestMapping("/findAvgElementValueTimeEffectByArea")
    public ApiResponse findAvgElementValueTimeEffectByArea(TimeEffectParameter timeEffectParameter){
        List<CalcElement> calcElements = calcSearchService.findMaxOrMinAndMaxByTimeEffectAndArea(timeEffectParameter);
        return getRoutineResult(calcElements);
    }

    @CalcAction(apiId = 17, isArea = false, calcType = "max")
    @RequestMapping("/findMaxElementValueTimeRangeByLocation")
    public ApiResponse findMaxElementValueTimeRangeByLocation(TimeRangeParameter timeRange){
        List<CalcElement> calcElements = calcSearchService.findMaxOrMinAndAvgByLocation(timeRange);
        return getRoutineResult(calcElements);
    }

    @CalcAction(apiId = 18, isArea = false, calcType = "min")
    @RequestMapping("/findMinElementValueTimeRangeByLocation")
    public ApiResponse findMinElementValueTimeRangeByLocation(TimeRangeParameter timeRange){
        List<CalcElement> calcElements = calcSearchService.findMaxOrMinAndAvgByLocation(timeRange);
        return getRoutineResult(calcElements);
    }

    @CalcAction(apiId = 19, isArea = false, calcType = "avg")
    @RequestMapping("/findAvgElementValueTimeRangeByLocation")
    public ApiResponse findAvgElementValueTimeRangeByLocation(TimeRangeParameter timeRange){
        List<CalcElement> calcElements = calcSearchService.findMaxOrMinAndAvgByLocation(timeRange);
        return getRoutineResult(calcElements);
    }

    @CalcAction(apiId = 20, isArea = false, calcType = "sum")
    @RequestMapping("/findTotalRain12Hour")
    public ApiResponse findTotalRain12Hour(SpecialParameter specialParameter){
        specialParameter.setElementCode("PRE");
        specialParameter.setTimeInterval(12);

        List<StatElement> statElements = calcSearchService.findSpecialStat(specialParameter);
        return getStatResult(statElements);
    }

    @CalcAction(apiId = 21, isArea = false, calcType = "sum")
    @RequestMapping("/findTotalRain24Hour")
    public ApiResponse findTotalRain24Hour(SpecialParameter specialParameter){
        specialParameter.setElementCode("PRE");
        specialParameter.setTimeInterval(24);

        List<StatElement> statElements = calcSearchService.findSpecialStat(specialParameter);
        return getStatResult(statElements);
    }

    @CalcAction(apiId = 22, isArea = false, calcType = "max")
    @RequestMapping("/findMaxTmp24Hour")
    public ApiResponse findMaxTmp24Hour(SpecialParameter specialParameter){
        specialParameter.setElementCode("T2M");
        specialParameter.setTimeInterval(24);

        List<StatElement> statElements = calcSearchService.findSpecialStat(specialParameter);
        return getStatResult(statElements);
    }

    @CalcAction(apiId = 23, isArea = false, calcType = "min")
    @RequestMapping("/findMinTmp24Hour")
    public ApiResponse findMinTmp24Hour(SpecialParameter specialParameter){
        specialParameter.setElementCode("T2M");
        specialParameter.setTimeInterval(24);

        List<StatElement> statElements = calcSearchService.findSpecialStat(specialParameter);
        return getStatResult(statElements);
    }

    @CalcAction(apiId = 24, isArea = false, calcType = "max")
    @RequestMapping("/findMaxWindSpeed12Hour")
    public ApiResponse findMaxWindSpeed12Hour(SpecialParameter specialParameter){
        specialParameter.setElementCode("U10M");
        specialParameter.setTimeInterval(12);

        List<StatElement> statElements = calcSearchService.findSpecialStat(specialParameter);
        return getStatResult(statElements);
    }

    @CalcAction(apiId = 25, isArea = false, calcType = "avg")
    @RequestMapping("/findAvgHum12Hour")
    public ApiResponse findAvgHum12Hour(SpecialParameter specialParameter){
        specialParameter.setElementCode("R2M");
        specialParameter.setTimeInterval(12);

        List<StatElement> statElements = calcSearchService.findSpecialStat(specialParameter);
        return getStatResult(statElements);
    }

    @CalcAction(apiId = 26, isArea = false, calcType = "max")
    @RequestMapping("/findMaxHum12Hour")
    public ApiResponse findMaxHum12Hour(SpecialParameter specialParameter){
        specialParameter.setElementCode("R2M");
        specialParameter.setTimeInterval(12);

        List<StatElement> statElements = calcSearchService.findSpecialStat(specialParameter);
        return getStatResult(statElements);
    }

    @CalcAction(apiId = 27, isArea = false, calcType = "min")
    @RequestMapping("/findMinHum12Hour")
    public ApiResponse findMinHum12Hour(SpecialParameter specialParameter){
        specialParameter.setElementCode("R2M");
        specialParameter.setTimeInterval(12);

        List<StatElement> statElements = calcSearchService.findSpecialStat(specialParameter);
        return getStatResult(statElements);
    }

    @CalcAction(apiId = 28, isArea = false, calcType = "max")
    @RequestMapping("/findMaxVis12Hour")
    public ApiResponse findMaxVis12Hour(SpecialParameter specialParameter){
        specialParameter.setElementCode("VIS");
        specialParameter.setTimeInterval(12);

        List<StatElement> statElements = calcSearchService.findSpecialStat(specialParameter);
        return getStatResult(statElements);
    }

    @CalcAction(apiId = 29, isArea = false, calcType = "min")
    @RequestMapping("/findMinVis12Hour")
    public ApiResponse findMinVis12Hour(SpecialParameter specialParameter){
        specialParameter.setElementCode("VIS");
        specialParameter.setTimeInterval(12);

        List<StatElement> statElements = calcSearchService.findSpecialStat(specialParameter);
        return getStatResult(statElements);
    }

    private ApiResponse getStatResult(List<StatElement> statElements){
        if (statElements.size() == 0) {
            return new ApiResponse(ResultCode.NULL_VALUE, "未查询到值", null);
        }
        return new ApiResponse(ResultCode.SUCCESS, "查询成功", statElements.get(0));
    }

    private ApiResponse getRoutineResult(List<CalcElement> calcElements){
        if (calcElements.size() == 0) {
            return new ApiResponse(ResultCode.NULL_VALUE, "未查询到值", null);
        }
        return new ApiResponse(ResultCode.SUCCESS, "查询成功", calcElements.get(0));
    }

}
