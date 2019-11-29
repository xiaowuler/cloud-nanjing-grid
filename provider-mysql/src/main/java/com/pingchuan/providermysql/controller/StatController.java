package com.pingchuan.providermysql.controller;

import com.pingchuan.dto.stat.RegionStat;
import com.pingchuan.providermysql.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("stat")
public class StatController {

    @Autowired
    private StatService statService;

    @PostMapping("/findAreaCallByTimeRange")
    public List<RegionStat> findAreaCallByTimeRange(Date startTime, Date endTime){
        return statService.findAreaCallByTimeRange(startTime, endTime);
    }

}
