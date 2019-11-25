package com.pingchuan.mongodb.service;

import com.pingchuan.dto.calc.CalcElement;
import com.pingchuan.dto.calc.StatElement;
import com.pingchuan.parameter.calc.RoutineParameter;
import com.pingchuan.parameter.calc.SpecialParameter;
import com.pingchuan.parameter.calc.TimeEffectParameter;
import com.pingchuan.parameter.calc.TimeRangeParameter;

import java.util.List;

public interface CalcSearchService {
    List<CalcElement> findMaxOrMinAndAvgByArea(RoutineParameter routineParameter);

    List<CalcElement> findMaxOrMinAndMaxByTimeEffectAndArea(TimeEffectParameter timeEffectParameter);

    List<CalcElement> findMaxOrMinAndAvgByLocation(TimeRangeParameter timeRange);

    List<StatElement> findSpecialStat(SpecialParameter specialParameter);
}
