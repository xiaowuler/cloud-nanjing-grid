package com.pingchuan.weatherservice.service;

import com.pingchuan.domain.LegendLevel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="provider-mysql")
public interface LegendLevelService {

    @RequestMapping(value = "/legendLevel/findAllByType", method = RequestMethod.POST)
    List<LegendLevel> findAllByType(@RequestParam String type);
}
