package com.pingchuan.gridapi.service.mongo;

import com.pingchuan.dto.real.Element;
import com.pingchuan.parameter.real.AreaParameter;
import com.pingchuan.parameter.real.LocationParameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name="provider-mongodb")
public interface RealSearchService {

    @RequestMapping(value = "/realSearch/findRealNJGridsByArea", method = RequestMethod.POST)
    List<Element> findRealNJGridsByArea(AreaParameter areaParameter);

    @RequestMapping(value = "/realSearch/findRealNJGridsByLocation", method = RequestMethod.POST)
    List<Element> findRealNJGridsByLocation(LocationParameter location);
}
