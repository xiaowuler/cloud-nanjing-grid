package com.pingchuan.weatherservice.controller;

import com.pingchuan.contants.ResultCode;
import com.pingchuan.dto.other.Element;
import com.pingchuan.dto.other.ElementCode;
import com.pingchuan.dto.stat.NestData;
import com.pingchuan.parameter.other.NewestParameter;
import com.pingchuan.weatherservice.service.OtherSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("other")
public class OtherController {

    @Autowired
    private OtherSearchService otherSearchService;

    @RequestMapping("/findNewestTime")
    public List<NestData> findNewestTime(){
        List<NestData> nestData = new ArrayList<>();
        NewestParameter newestParameter = new NewestParameter();
        newestParameter.setForecastModel("NJGRID-STANDARD");
        List<Element> elements = otherSearchService.findNewestTime(newestParameter);
        nestData = getNestData(nestData, elements);

        newestParameter.setForecastModel("NJGRID-REALTIME");
        List<Element> element = otherSearchService.findNewestTime(newestParameter);

        return getNestData(nestData, element);
    }

    private List<NestData> getNestData(List<NestData> nestDatas, List<Element> elements){
        if (elements.size() == 0){
            return nestDatas;
        }

        Element element = elements.get(0);
        for(ElementCode elementCode : element.getElementCodes()){
            NestData nestData = new NestData();
            nestData.setName(String.format("%s-%s", element.getForecastModel(), elementCode.getElementCode()));
            nestData.setTime(elementCode.getUpdateTime());
            nestDatas.add(nestData);
        }
        return nestDatas;
    }
}
