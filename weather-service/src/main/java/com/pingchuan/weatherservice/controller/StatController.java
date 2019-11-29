package com.pingchuan.weatherservice.controller;

import com.pingchuan.dto.stat.RegionStat;
import com.pingchuan.dto.stat.TypeStat;
import com.pingchuan.weatherservice.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("stat")
public class StatController {

    @Autowired
    private StatService statService;

    @RequestMapping("/findAreaCallByTimeRange")
    public List<RegionStat> findAreaCallByTimeRange(String startTime, String endTime){
        return statService.findAreaCallByTimeRange(startTime, endTime);
    }

    @RequestMapping("/findTypeCallByTimeRange")
    public List<TypeStat> findTypeCallByTimeRange(String startTime, String endTime){
        return statService.findTypeCallByTimeRange(startTime, endTime);
    }

}
