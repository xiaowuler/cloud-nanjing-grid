package com.pingchuan.weatherservice.service;

import com.pingchuan.dto.other.Element;
import com.pingchuan.model.Trapezoid;
import com.pingchuan.parameter.other.ForecastTimeParameter;
import com.pingchuan.parameter.other.NewestParameter;
import com.pingchuan.parameter.other.TrapezoidParameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name="provider-mongodb")
public interface OtherSearchService {

    @RequestMapping(value = "/otherSearch/findNewestTime", method = RequestMethod.POST)
    List<Element> findNewestTime(NewestParameter newest);

    @RequestMapping(value = "/otherSearch/findUpdateAndStartByDate", method = RequestMethod.POST)
    List<Element> findUpdateAndStartByDate(NewestParameter newest);
}