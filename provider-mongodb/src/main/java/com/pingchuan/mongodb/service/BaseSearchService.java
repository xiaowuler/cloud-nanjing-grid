package com.pingchuan.mongodb.service;

import com.pingchuan.dto.base.AreaElement;
import com.pingchuan.parameter.base.*;

import java.util.List;

public interface BaseSearchService {
    List<AreaElement> findNJGridsByArea(AreaParameter areaParameter);

    List<AreaElement> findNJGridsByLocation(LocationParameter location);

    List<AreaElement> findNJGridsByForecastTimeRange(TimeRangeParameter timeRangeParameter);

    List<AreaElement> findNJGridsByTimeEffect(TimeEffectParameter timeEffectParameter);

    List<AreaElement> findNJGridsByElementThresholdArea(ThresholdAreaParameter thresholdAreaParameter);

    List<AreaElement> findNJGridsByElementThresholdLocation(ThresholdLocationParameter thresholdLocationParameter);
}
