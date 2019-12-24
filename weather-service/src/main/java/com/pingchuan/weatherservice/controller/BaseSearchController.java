package com.pingchuan.weatherservice.controller;

import com.pingchuan.contants.ResultCode;
import com.pingchuan.domain.LegendLevel;
import com.pingchuan.dto.base.AreaElement;
import com.pingchuan.dto.base.ElementCode;
import com.pingchuan.dto.base.Forecast;
import com.pingchuan.dto.base.Location;
import com.pingchuan.dto.web.Box;
import com.pingchuan.dto.web.DrawResult;
import com.pingchuan.dto.web.SiteCenterValue;
import com.pingchuan.dto.web.SiteValue;
import com.pingchuan.parameter.base.AreaParameter;
import com.pingchuan.parameter.base.TimeRangeParameter;
import com.pingchuan.weatherservice.service.BaseSearchService;
import com.pingchuan.weatherservice.service.LegendLevelService;
import com.pingchun.utils.CalcUtil;
import com.pingchun.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
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
        List<Box> boxes;
        if ("Wind".equals(elementCode)){
            List<AreaElement> uAreaElements = baseSearchService.findNJGridsByNonArea(createAreaParameter(updateDate, startDate, forecastDate, "U10M", forecastModel));
            List<Location> uLocations = mergeLocation(uAreaElements);
            List<AreaElement> vAreaElements = baseSearchService.findNJGridsByNonArea(createAreaParameter(updateDate, startDate, forecastDate, "V10M", forecastModel));
            List<Location> vLocations = mergeLocation(vAreaElements);
            boxes = calcWind(uLocations, vLocations);
        }else {
            List<AreaElement> areaElements = baseSearchService.findNJGridsByNonArea(createAreaParameter(updateDate, startDate, forecastDate, elementCode, forecastModel));
            List<Location> locations = mergeLocation(areaElements);
            boxes = getBox(locations, elementCode);
        }

        List<LegendLevel> legendLevels = legendLevelService.findAllByType(getTypeByForecastModel(forecastModel));
        DrawResult drawResult = new DrawResult();
        drawResult.setBox(boxes);
        drawResult.setLegendLevels(legendLevels);
        return drawResult;
    }

    @RequestMapping("/findNJGridsByForecastTimeRangeAllElement")
    public SiteValue findNJGridsByForecastTimeRangeAllElement(Date updateDate, Date startDate, Date startForecastDate, Date endForecastDate, String elementCode, String forecastModel, double lat, double lng){
        TimeRangeParameter timeRangeParameter = new TimeRangeParameter();
        timeRangeParameter.setEndForecastDate(endForecastDate);
        timeRangeParameter.setStartForecastDate(startForecastDate);
        List<double[]> locations = new ArrayList<>();
        locations.add(new double[] {lng, lat});
        timeRangeParameter.setLocations(locations);
        timeRangeParameter.setForecastModel(forecastModel);
        timeRangeParameter.setStartDate(startDate);
        timeRangeParameter.setUpdateDate(updateDate);

        if ("Wind".equals(elementCode)){
            timeRangeParameter.setElementCode("U10M");
            List<AreaElement> us = baseSearchService.findNJGridsByForecastTimeRange(timeRangeParameter);
            timeRangeParameter.setElementCode("V10M");
            List<AreaElement> vs = baseSearchService.findNJGridsByForecastTimeRange(timeRangeParameter);
            return getSiteChatValueByWind(us, vs, elementCode);
        }else {
            timeRangeParameter.setElementCode(elementCode);
            List<AreaElement> areaElements = baseSearchService.findNJGridsByForecastTimeRange(timeRangeParameter);
            return getSiteChatValue(areaElements, elementCode);
        }
    }

    private List<SiteCenterValue> getSiteCenterValue(List<AreaElement> areaElements){
        List<SiteCenterValue> siteCenterValues = new ArrayList<>();

        if (areaElements.size() == 0){
            return null;
        }
        AreaElement areaElement = areaElements.get(0);
        if (areaElement.getElementCodes().size() == 0){
            return null;
        }

        ElementCode element = areaElement.getElementCodes().get(0);
        if (element.getForecasts().size() == 0){
            return null;
        }
        List<Forecast> forecasts = element.getForecasts();
        forecasts.sort(Comparator.comparing(Forecast::getForecastTime));

        forecasts.forEach(f -> siteCenterValues.add(new SiteCenterValue(keepOne(f.getLocations().get(0).getValue()), f.getForecastTime())));
        return  siteCenterValues;
    }

    private SiteValue getSiteChatValueByWind(List<AreaElement> us, List<AreaElement> vs, String elementCode){
        SiteValue siteValue = getSiteBaseInfo(elementCode);

        List<SiteCenterValue> uSites = getSiteCenterValue(us);
        if (StringUtils.isEmpty(uSites)){
            return null;
        }

        List<SiteCenterValue> vSites = getSiteCenterValue(vs);
        if (StringUtils.isEmpty(vSites)){
            return null;
        }

        List<Double> windSpeeds = new ArrayList<>();
        List<String> windDirections = new ArrayList<>();
        List<String> times = new ArrayList<>();

        for(SiteCenterValue siteCenterValue : uSites){
            List<SiteCenterValue> vSite = vSites.stream().filter(v -> v.getForecastTime().compareTo(siteCenterValue.getForecastTime()) == 0).collect(Collectors.toList());
            if (vSite.size() == 0){
                continue;
            }

            times.add(TimeUtil.CovertDateToString("yyMMddHH", siteCenterValue.getForecastTime()));
            windSpeeds.add(keepOne(CalcUtil.windSpeed(siteCenterValue.getValue(), vSite.get(0).getValue())));
            windDirections.add(CalcUtil.getWindDirectionStr(CalcUtil.windDirection(siteCenterValue.getValue(), vSite.get(0).getValue())));
        }
        siteValue.setTimes(times);
        siteValue.setValues(windSpeeds);
        siteValue.setWindDirection(windDirections);
        return siteValue;
    }

    private SiteValue getSiteChatValue(List<AreaElement> areaElements, String elementCode){
        SiteValue siteValue = getSiteBaseInfo(elementCode);

        List<SiteCenterValue> siteCenterValues = getSiteCenterValue(areaElements);
        List<String> forecastDates = siteCenterValues.stream().map(f -> TimeUtil.CovertDateToString("yyMMddHH", f.getForecastTime())).collect(Collectors.toList());
        List<Double> values = siteCenterValues.stream().map(f -> keepOne(f.getValue())).collect(Collectors.toList());
        if ("WTYPE".equals(elementCode)){
            siteValue.setFlags(values.stream().map(v -> getWeatherPhenomena(v.intValue())).collect(Collectors.toList()));
        }else if("PTYPE".equals(elementCode)) {
            siteValue.setFlags(values.stream().map(v -> getPhaseState(v.intValue())).collect(Collectors.toList()));
        }

        siteValue.setTimes(forecastDates);
        siteValue.setValues(values);
        return siteValue;
    }

    private double keepOne(double value){
        return new BigDecimal(value).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private SiteValue getSiteBaseInfo(String elementCode){
        SiteValue siteValue = new SiteValue();
        switch (elementCode){
            case "PRE":
                siteValue.setUnit("mm");
                siteValue.setName("降水");
                siteValue.setType("column");
                break;
            case "VIS":
                siteValue.setUnit("km");
                siteValue.setName("能见度");
                siteValue.setType("spline");
                break;
            case "Wind":
                siteValue.setUnit("m/s");
                siteValue.setName("10米风速");
                siteValue.setType("spline");
                break;
            case "R2M":
                siteValue.setUnit("%");
                siteValue.setName("2米相对湿度:");
                siteValue.setType("spline");
                break;
            case "PTYPE":
                siteValue.setUnit("");
                siteValue.setName("相态");
                siteValue.setType("spline");
                break;
            case "TCC":
                siteValue.setUnit("%");
                siteValue.setName("云量");
                siteValue.setType("spline");
                break;
            case "WTYPE":
                siteValue.setUnit("");
                siteValue.setName("天气现象");
                siteValue.setType("spline");
                break;
            case "T2M":
                siteValue.setUnit("℃");
                siteValue.setName("2米气温");
                siteValue.setType("spline");
                break;
            default:
                siteValue.setUnit("");
        }

        return siteValue;
    }

    private List<Box> calcWind(List<Location> us, List<Location> vs){
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
                List<Location> ulocation = us.stream().filter(l -> l.getLoc()[0] >= box.getStartLon() && l.getLoc()[1] >= box.getStartLat() && l.getLoc()[0] < box.getEndLon() && l.getLoc()[1] < box.getEndLat()).collect(Collectors.toList());
                List<Location> vlocation = vs.stream().filter(l -> l.getLoc()[0] >= box.getStartLon() && l.getLoc()[1] >= box.getStartLat() && l.getLoc()[0] < box.getEndLon() && l.getLoc()[1] < box.getEndLat()).collect(Collectors.toList());
                if (ulocation.size() == 0 || vlocation.size() == 0){
                    continue;
                }

                double u = ulocation.get(0).getValue();
                double v = vlocation.get(0).getValue();
                box.setWindSpeedLevel(CalcUtil.getWindSpeedLevel(CalcUtil.windSpeed(u, v)));
                box.setWindDirection(CalcUtil.getWindDirectionStr(CalcUtil.windDirection(u, v)));
                boxes.add(box);
            }
        }
        return boxes;
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

    private List<Box> getBox(List<Location> locations, String elementCode){

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
                List<Location> location = locations.stream().filter(l -> l.getLoc()[0] >= box.getStartLon() && l.getLoc()[1] >= box.getStartLat() && l.getLoc()[0] < box.getEndLon() && l.getLoc()[1] < box.getEndLat()).collect(Collectors.toList());
                if (location.size() == 0){
                    continue;
                }
                box.setValue(location.get(0).getValue());
                if ("WTYPE".equals(elementCode)){
                    box.setFlag(getWeatherPhenomena((int) box.getValue()));
                } else if ("PTYPE".equals(elementCode)) {
                    box.setFlag(getPhaseState((int) box.getValue()));
                }
                boxes.add(box);
            }
        }
        return boxes;
    }

    private String getPhaseState(int value){
        if (value ==  3){
            return "雨";
        }else  if (value == 4){
            return "雪";
        }else if (value == 5){
            return "雨夹雪";
        }else {
            return "";
        }
    }

    private String getWeatherPhenomena(int value){
        if (value == 0){
            return "晴";
        }else if (value == 1){
            return "多云";
        }else if (value == 2){
            return "阴";
        }else if (value == 3){
            return "阵雨";
        }else if (value == 4){
            return "雷阵雨";
        }else if (value == 5){
            return "雷阵雨并伴有冰雹";
        }else if (value == 6){
            return "雨夹雪";
        }else if (value == 7){
            return "小雨";
        }else if (value == 8){
            return "中雨";
        }else if (value == 9){
            return "大雨";
        }else if (value == 10){
            return "暴雨";
        }else if (value == 11){
            return "大暴雨";
        }else if (value == 12){
            return "特大暴雨";
        }else if (value == 13){
            return "阵雪";
        }else if (value == 14){
            return "小雪";
        }else if (value == 15){
            return "中雪";
        }else if (value == 16){
            return "大雪";
        }else if (value == 17){
            return "暴雪";
        }else if (value == 18){
            return "雾";
        }else if (value == 19){
            return "冻雨";
        }else if (value == 20){
            return "沙尘暴";
        }else if (value == 21){
            return "小到中雨";
        }else if (value == 22){
            return "中到大雨";
        }else if (value == 23){
            return "大到暴雨";
        }else if (value == 24){
            return "暴雨到大暴雨";
        }else if (value == 25){
            return "大暴雨到特大暴雨";
        }else if (value == 26){
            return "小到中雪";
        }else if (value == 27){
            return "中到大雪";
        }else if (value == 28){
            return "大到暴雪";
        }else if (value == 53){
            return "霾";
        }
        return "";
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
