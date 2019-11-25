package com.pingchuan.gridapi.controller;

import com.pingchuan.contants.ResultCode;
import com.pingchuan.dto.real.Element;
import com.pingchuan.gridapi.annotation.RealAction;
import com.pingchuan.gridapi.domain.ApiResponse;
import com.pingchuan.gridapi.service.mongo.RealSearchService;
import com.pingchuan.parameter.real.AreaParameter;
import com.pingchuan.parameter.real.LocationParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("realSearch")
public class RealSearchController {

    @Autowired
    private RealSearchService realSearchService;

    @RequestMapping("/findRealNJGridsByArea")
    @RealAction(apiId = 35, isNeedElementCode = true)
    public ApiResponse findRealNJGridsByArea(AreaParameter area){
        List<Element> elements = realSearchService.findRealNJGridsByArea(area);
        if (elements.size() == 0) {
            return new ApiResponse(ResultCode.NULL_VALUE, "未查询到值", null);
        }
        return new ApiResponse(ResultCode.SUCCESS, "查询成功", elements);
    }

    @RequestMapping("/findRealNJGridsByAreaAllElement")
    @RealAction(apiId = 36, isNeedElementCode = false)
    public ApiResponse findRealNJGridsByAreaAllElement(AreaParameter area){
        List<Element> elements = realSearchService.findRealNJGridsByArea(area);
        if (elements.size() == 0) {
            return new ApiResponse(ResultCode.NULL_VALUE, "未查询到值", null);
        }
        return new ApiResponse(ResultCode.SUCCESS, "查询成功", elements);
    }

    @RequestMapping("/findRealNJGridsByLocation")
    @RealAction(apiId = 37, isNeedElementCode = true)
    public ApiResponse findRealNJGridsByLocation(LocationParameter location){
        List<Element> elements = realSearchService.findRealNJGridsByLocation(location);
        if (elements.size() == 0) {
            return new ApiResponse(ResultCode.NULL_VALUE, "未查询到值", null);
        }
        return new ApiResponse(ResultCode.SUCCESS, "查询成功", elements);
    }

    @RequestMapping("/findRealNJGridsByLocationAllElement")
    @RealAction(apiId = 38, isNeedElementCode = false)
    public ApiResponse findRealNJGridsByLocationAllElement(LocationParameter location){
        List<Element> elements = realSearchService.findRealNJGridsByLocation(location);
        if (elements.size() == 0) {
            return new ApiResponse(ResultCode.NULL_VALUE, "未查询到值", null);
        }
        return new ApiResponse(ResultCode.SUCCESS, "查询成功", elements);
    }
}
