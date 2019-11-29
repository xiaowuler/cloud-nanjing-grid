package com.pingchuan.weatherservice.service;

import com.pingchuan.dto.stat.RegionStat;
import com.pingchuan.dto.stat.TypeStat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="provider-mysql")
public interface StatService {

    @RequestMapping(value = "stat/findAreaCallByTimeRange", method = RequestMethod.POST)
    List<RegionStat> findAreaCallByTimeRange(@RequestParam String startTime, @RequestParam String endTime);

    @RequestMapping(value = "stat/findTypeCallByTimeRange", method = RequestMethod.POST)
    List<TypeStat> findTypeCallByTimeRange(@RequestParam String startTime, @RequestParam String endTime);
}
