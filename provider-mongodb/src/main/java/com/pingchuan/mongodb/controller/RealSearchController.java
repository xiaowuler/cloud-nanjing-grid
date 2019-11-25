package com.pingchuan.mongodb.controller;

import com.pingchuan.dto.real.Element;
import com.pingchuan.mongodb.service.RealSearchService;
import com.pingchuan.parameter.real.AreaParameter;
import com.pingchuan.parameter.real.LocationParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("realSearch")
public class RealSearchController {

    @Autowired
    private RealSearchService realSearchService;

    @PostMapping("/findRealNJGridsByArea")
    public List<Element> findRealNJGridsByArea(@RequestBody AreaParameter areaParameter){
        return realSearchService.findRealNJGridsByArea(areaParameter);
    }

    @PostMapping("/findRealNJGridsByLocation")
    public List<Element> findRealNJGridsByLocation(@RequestBody LocationParameter locationParameter){
        return realSearchService.findRealNJGridsByLocation(locationParameter);
    }

}
