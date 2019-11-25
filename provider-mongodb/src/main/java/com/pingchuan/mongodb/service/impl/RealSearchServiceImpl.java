package com.pingchuan.mongodb.service.impl;

import com.pingchuan.dto.real.Element;
import com.pingchuan.dto.real.Location;
import com.pingchuan.dto.real.Real;
import com.pingchuan.mongodb.dao.ElementValueDao;
import com.pingchuan.mongodb.dao.RealInfoDao;
import com.pingchuan.mongodb.dao.RealSearchDao;
import com.pingchuan.mongodb.dao.TrapezoidDao;
import com.pingchuan.mongodb.service.RealSearchService;
import com.pingchuan.parameter.real.AreaParameter;
import com.pingchuan.parameter.real.LocationParameter;
import com.pingchun.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RealSearchServiceImpl implements RealSearchService {

    @Autowired
    private TrapezoidDao trapezoidDao;

    @Autowired
    private RealInfoDao realInfoDao;

    @Autowired
    private ElementValueDao elementValueDao;

    @Autowired
    private RealSearchDao realSearchDao;

    @Override
    public List<Element> findRealNJGridsByArea(AreaParameter areaParameter) {
        List<Element> elements = new ArrayList<>();
        List<AggregationOperation> realInfos = realInfoDao.findByStartTimeAndEndTimeAndElementCode(areaParameter.getStartRealDate(), areaParameter.getEndRealDate(), areaParameter.getElementCode());
        List<AggregationOperation> trapezoids = trapezoidDao.findRealByArea(areaParameter.getAreaCode());
        for (String collectionName : TimeUtil.getCollectionByTimeRange(areaParameter.getStartRealDate(), areaParameter.getEndRealDate())){
            List<AggregationOperation> aggregationOperations = new ArrayList<>();
            aggregationOperations.addAll(realInfos);
            aggregationOperations.addAll(trapezoids);
            aggregationOperations.addAll(elementValueDao.findReal(collectionName));
            mergeAreaElements(elements, realSearchDao.findRealNJGridsByArea(aggregationOperations));
        }

        return elements;
    }

    @Override
    public List<Element> findRealNJGridsByLocation(LocationParameter locationParameter) {
        List<Element> elements = new ArrayList<>();
        List<AggregationOperation> realInfos = realInfoDao.findByStartTimeAndEndTimeAndElementCode(locationParameter.getStartRealDate(), locationParameter.getEndRealDate(), locationParameter.getElementCode());
        List<AggregationOperation> trapezoids = trapezoidDao.findRealByLocation(locationParameter.getLocations());
        for (String collectionName : TimeUtil.getCollectionByTimeRange(locationParameter.getStartRealDate(), locationParameter.getEndRealDate())){
            List<AggregationOperation> aggregationOperations = new ArrayList<>();
            aggregationOperations.addAll(realInfos);
            aggregationOperations.addAll(trapezoids);
            aggregationOperations.addAll(elementValueDao.findReal(collectionName));
            mergeLocationElements(elements, realSearchDao.findRealNJGridsByLocation(aggregationOperations));
        }

        return elements;
    }

    private List<Element> mergeAreaElements(List<Element> areaElements, List<Element> searchList){
        if (areaElements.size() == 0){
            areaElements.addAll(searchList);
            return areaElements;
        }

        for (Element areaElement : areaElements){
            List<Element> elementCodes = searchList.stream().filter(a -> a.getElementCode() == areaElement.getElementCode()).collect(Collectors.toList());
            if (elementCodes.size() == 0){
                continue;
            }

            List<Real> reals = elementCodes.get(0).getReals();
            for(Real real : areaElement.getReals()){

                List<Real> realList = reals.stream().filter(r -> r.getRealTime() == real.getRealTime()).collect(Collectors.toList());
                if (realList.size() == 0){
                    continue;
                }

                real.getLocations().addAll(realList.get(0).getLocations());
            }
        }

        return areaElements;
    }

    private List<Element> mergeLocationElements(List<Element> areaElements, List<Element> searchList){
        if (areaElements.size() == 0){
            areaElements.addAll(searchList);
            return areaElements;
        }

        for (Element areaElement : areaElements){
            List<Element> elementCodes = searchList.stream().filter(a -> a.getElementCode() == areaElement.getElementCode()).collect(Collectors.toList());
            if (elementCodes.size() == 0){
                continue;
            }

            List<Location> locations = elementCodes.get(0).getLocations();
            for(Location location : areaElement.getLocations()){

                List<Location> locs = locations.stream().filter(l -> l.getLoc() == location.getLoc()).collect(Collectors.toList());
                if (locs.size() == 0){
                    continue;
                }

                location.getReals().addAll(locs.get(0).getReals());
            }
        }

        return areaElements;
    }
}
