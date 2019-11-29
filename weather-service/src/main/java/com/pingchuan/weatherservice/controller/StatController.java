package com.pingchuan.weatherservice.controller;

import com.pingchuan.weatherservice.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("stat")
public class StatController {

    @Autowired
    private StatService statService;

    @RequestMapping("/findAreaCallByTimeRange")
    public void findAreaCallByTimeRange(String startTime, String endTime){
        statService.findAreaCallByTimeRange(startTime, endTime);
    }

}
