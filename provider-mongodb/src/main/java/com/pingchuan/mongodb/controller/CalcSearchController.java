package com.pingchuan.mongodb.controller;

import com.pingchuan.dto.calc.CalcElement;
import com.pingchuan.dto.calc.StatElement;
import com.pingchuan.mongodb.service.CalcSearchService;
import com.pingchuan.parameter.calc.RoutineParameter;
import com.pingchuan.parameter.calc.SpecialParameter;
import com.pingchuan.parameter.calc.TimeEffectParameter;
import com.pingchuan.parameter.calc.TimeRangeParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("calcSearch")
@RestController
public class CalcSearchController {

    @Autowired
    private CalcSearchService calcSearchService;

    @PostMapping("/findMaxOrMinAndAvgByArea")
    public List<CalcElement> findMaxOrMinAndAvgByArea(@RequestBody RoutineParameter routineParameter){
        return calcSearchService.findMaxOrMinAndAvgByArea(routineParameter);
    }

    @PostMapping("/findMaxOrMinAndMaxByTimeEffectAndArea")
    public List<CalcElement> findMaxOrMinAndMaxByTimeEffectAndArea(@RequestBody TimeEffectParameter timeEffectParameter){
        return calcSearchService.findMaxOrMinAndMaxByTimeEffectAndArea(timeEffectParameter);
    }

    @PostMapping("/findMaxOrMinAndAvgByLocation")
    public List<CalcElement> findMaxOrMinAndAvgByLocation(@RequestBody TimeRangeParameter timeRange){
        return calcSearchService.findMaxOrMinAndAvgByLocation(timeRange);
    }

    @PostMapping("/findSpecialStat")
    public List<StatElement> findSpecialStat(@RequestBody SpecialParameter specialParameter){
        return calcSearchService.findSpecialStat(specialParameter);
    }

}
