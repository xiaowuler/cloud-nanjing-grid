package com.pingchuan.mongodb.controller;

import com.pingchuan.dto.other.Element;
import com.pingchuan.model.Trapezoid;
import com.pingchuan.mongodb.service.OtherSearchSerive;
import com.pingchuan.parameter.other.ForecastTimeParameter;
import com.pingchuan.parameter.other.NewestParameter;
import com.pingchuan.parameter.other.TrapezoidParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("otherSearch")
public class OtherSearchController {

    @Autowired
    private OtherSearchSerive otherSearchSerive;

    @PostMapping("/findNewestTime")
    public List<Element> findNewestTime(@RequestBody NewestParameter newest){
        return otherSearchSerive.findNewestTime(newest);
    }

    @PostMapping("/findUpdateAndStartByDate")
    public List<Element> findUpdateAndStartByDate(@RequestBody NewestParameter newestParameter){
        return otherSearchSerive.findUpdateAndStartByDate(newestParameter);
    }

    @PostMapping("/findNewestTimeByForecastTime")
    public List<Element> findNewestTimeByForecastTime(@RequestBody ForecastTimeParameter forecastTime){
        return otherSearchSerive.findNewestTimeByForecastTime(forecastTime);
    }

    @PostMapping("/findAllTrapezoid")
    public List<Trapezoid> findAllTrapezoid(@RequestBody TrapezoidParameter trapezoidParameter){
        return otherSearchSerive.findAllTrapezoid(trapezoidParameter);
    }
}
