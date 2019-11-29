package com.pingchuan.weatherservice.controller;

import com.pingchuan.contants.ResultCode;
import com.pingchuan.dto.other.Element;
import com.pingchuan.dto.other.ElementCode;
import com.pingchuan.dto.other.UpdateTime;
import com.pingchuan.dto.stat.NestData;
import com.pingchuan.parameter.other.NewestParameter;
import com.pingchuan.weatherservice.service.OtherSearchService;
import com.pingchun.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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

    @RequestMapping("/findNewestTimeByModeCode")
    public List<UpdateTime> findNewestTimeByModeCode(String modeCode, String elementCode){
        NewestParameter newestParameter = new NewestParameter();
        newestParameter.setForecastModel(modeCode);
        newestParameter.setElementCode(elementCode);
        List<Element> elements = otherSearchService.findNewestTime(newestParameter);
        if (elements.size() == 0) {
            return null;
        }

        Date updateTime = elements.get(0).getElementCodes().get(0).getUpdateTime();
        Date startTime = TimeUtil.getCurrentZeroHour(updateTime);
        newestParameter.setStartTime(startTime);
        newestParameter.setEndTime(TimeUtil.addDay(startTime, 1));

        List<Element> elementRange = otherSearchService.findUpdateAndStartByDate(newestParameter);
        if (elementRange.size() == 0) {
            return null;
        }

        List<ElementCode> elementCodes = elementRange.get(0).getElementCodes();
        if (elementCodes.size() == 0){
            return null;
        }

        List<UpdateTime> updateTimes = elementCodes.get(0).getUpdateTimes();
        if (updateTimes.size() == 0){
            return null;
        }

        Collections.sort(updateTimes, Comparator.comparing(UpdateTime::getUpdateTime));
        return updateTimes;
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
