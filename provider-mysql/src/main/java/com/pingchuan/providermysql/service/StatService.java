package com.pingchuan.providermysql.service;

import com.pingchuan.dto.stat.RegionStat;

import java.util.Date;
import java.util.List;

public interface StatService {
    List<RegionStat> findAreaCallByTimeRange(Date startTime, Date endTime);
}
