package com.pingchuan.weatherservice.controller;

import com.pingchuan.contants.ResultCode;
import com.pingchuan.domain.LegendLevel;
import com.pingchuan.dto.base.AreaElement;
import com.pingchuan.dto.base.ElementCode;
import com.pingchuan.dto.base.Forecast;
import com.pingchuan.dto.base.Location;
import com.pingchuan.dto.web.Box;
import com.pingchuan.dto.web.DrawResult;
import com.pingchuan.parameter.base.AreaParameter;
import com.pingchuan.weatherservice.service.BaseSearchService;
import com.pingchuan.weatherservice.service.LegendLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("baseSearch")
public class BaseSearchController {

    @Autowired
    private BaseSearchService baseSearchService;

    @Autowired
    private LegendLevelService legendLevelService;

    @RequestMapping("/findNJGridsByNonArea")
    public DrawResult findNJGridsByNonArea(Date updateDate, Date startDate, Date forecastDate, String elementCode, String forecastModel){

        List<AreaElement> areaElements = baseSearchService.findNJGridsByNonArea(createAreaParameter(updateDate, startDate, forecastDate, elementCode, forecastModel));
        List<Location> locations = mergeLocation(areaElements);
        List<Box> boxes = getBox(locations);
        List<LegendLevel> legendLevels = legendLevelService.findAllByType(getTypeByForecastModel(forecastModel));
        DrawResult drawResult = new DrawResult();
        drawResult.setBox(boxes);
        drawResult.setLegendLevels(legendLevels);
        return drawResult;
    }

    private String getTypeByForecastModel(String forecastModel){
        if ("WTYPE".equals(forecastModel)){
            return "rainfalls";
        }else if ("VIS".equals(forecastModel)){
            return "rainfalls";
        }else if ("V10M".equals(forecastModel)){
            return "temperatures";
        }else if ("U10M".equals(forecastModel)){
            return "temperatures";
        }else if ("TCC".equals(forecastModel)){
            return "pressures";
        }else if ("T2M".equals(forecastModel)){
            return "rainfalls";
        }else if ("R2M".equals(forecastModel)){
            return "rainfalls";
        }else if ("PTYPE".equals(forecastModel)){
            return "rainfalls";
        }else{
            return "rainfalls";
        }
    }

    private AreaParameter createAreaParameter(Date updateDate, Date startDate, Date forecastDate, String elementCode, String forecastModel){
        AreaParameter areaParameter = new AreaParameter();
        areaParameter.setForecastDate(forecastDate);
        areaParameter.setStartDate(startDate);
        areaParameter.setUpdateDate(updateDate);
        areaParameter.setForecastModel(forecastModel);
        areaParameter.setElementCode(elementCode);
        return areaParameter;
    }

    private List<Box> getBox(List<Location> locations){
        List<Box> boxes = new ArrayList<>();
        double lonLength = 119.14 - 118.22;
        double boxLength = lonLength / 8;
        for(double x = 118.2; x <= 119.14; x += boxLength){
            for(double y = 31.14; y <= 32.6; y += boxLength){
                Box box = new Box();
                box.setStartLon(x);
                box.setEndLon(x + boxLength);
                box.setStartLat(y);
                box.setEndLat(y + boxLength);
                double total = locations.stream().filter(l -> l.getLoc()[0] >= box.getStartLon() && l.getLoc()[1] >= box.getStartLat() && l.getLoc()[0] < box.getEndLon() && l.getLoc()[1] < box.getEndLat()).collect(Collectors.summingDouble(Location::getValue));
                if (total == 0){
                    continue;
                }

                box.setTotal(total);
                boxes.add(box);
            }
        }

        return boxes;
    }

    private List<Location> mergeLocation(List<AreaElement> areaElements){
        List<Location> locations = new ArrayList<>();

        for(AreaElement areaElement : areaElements){
            for(ElementCode elementCode : areaElement.getElementCodes()){
                for(Forecast forecast : elementCode.getForecasts()){
                    locations.addAll(forecast.getLocations());
                }
            }
        }

        return locations;
    }
}
