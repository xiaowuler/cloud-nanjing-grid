package com.pingchuan.providermysql.service;

import com.pingchuan.dto.stat.RegionStat;
import com.pingchuan.dto.stat.TypeStat;

import java.util.Date;
import java.util.List;

public interface StatService {
    List<RegionStat> findAreaCallByTimeRange(Date startTime, Date endTime);

    List<TypeStat> findTypeCallByTimeRange(Date startTime, Date endTime);
}
