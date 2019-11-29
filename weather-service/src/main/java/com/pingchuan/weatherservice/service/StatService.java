package com.pingchuan.weatherservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="provider-mysql")
public interface StatService {

    @RequestMapping(value = "stat/findAreaCallByTimeRange", method = RequestMethod.POST)
    void findAreaCallByTimeRange(@RequestParam String startTime, @RequestParam String endTime);
}
