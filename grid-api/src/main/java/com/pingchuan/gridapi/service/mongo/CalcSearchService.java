package com.pingchuan.gridapi.service.mongo;

import com.pingchuan.dto.calc.CalcElement;
import com.pingchuan.dto.calc.StatElement;
import com.pingchuan.parameter.calc.RoutineParameter;
import com.pingchuan.parameter.calc.SpecialParameter;
import com.pingchuan.parameter.calc.TimeEffectParameter;
import com.pingchuan.parameter.calc.TimeRangeParameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name="provider-mongodb")
public interface CalcSearchService {

    @RequestMapping(value = "/calcSearch/findMaxOrMinAndAvgByArea", method = RequestMethod.POST)
    List<CalcElement> findMaxOrMinAndAvgByArea(RoutineParameter routineParameter);

    @RequestMapping(value = "/calcSearch/findMaxOrMinAndMaxByTimeEffectAndArea", method = RequestMethod.POST)
    List<CalcElement> findMaxOrMinAndMaxByTimeEffectAndArea(TimeEffectParameter timeEffectParameter);

    @RequestMapping(value = "/calcSearch/findMaxOrMinAndAvgByLocation", method = RequestMethod.POST)
    List<CalcElement> findMaxOrMinAndAvgByLocation(TimeRangeParameter timeRange);

    @RequestMapping(value = "/calcSearch/findSpecialStat", method = RequestMethod.POST)
    List<StatElement> findSpecialStat(SpecialParameter specialParameter);
}
