package com.pingchuan.mongodb.service;

import com.pingchuan.dto.real.Element;
import com.pingchuan.parameter.real.AreaParameter;
import com.pingchuan.parameter.real.LocationParameter;

import java.util.List;

public interface RealSearchService {
    List<Element> findRealNJGridsByArea(AreaParameter areaParameter);

    List<Element> findRealNJGridsByLocation(LocationParameter locationParameter);
}
