package com.pingchuan.providermysql.service.impl;

import com.pingchuan.domain.Region;
import com.pingchuan.dto.stat.RegionStat;
import com.pingchuan.providermysql.mapper.RegionMapper;
import com.pingchuan.providermysql.mapper.StatMapper;
import com.pingchuan.providermysql.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
public class StatServiceImpl implements StatService {

    @Autowired
    private StatMapper statMapper;

    @Autowired
    private RegionMapper regionMapper;

    @Override
    public List<RegionStat> findAreaCallByTimeRange(Date startTime, Date endTime) {
        List<Region> regions = regionMapper.findAll();
        List<RegionStat> regionStats = statMapper.findAreaCallByTimeRange(startTime, endTime);
        for(Region region : regions){
            Stream<RegionStat> stream = regionStats.stream();
            long count = stream.filter(rs -> region.getCode().equals(rs.getCode())).count();
            if (count > 0) {
                continue;
            }

            RegionStat regionStat = new RegionStat();
            regionStat.setCode(region.getCode());
            regionStat.setGridCount(region.getGridCount());
            regionStat.setName(region.getName());
            regionStats.add(regionStat);
        }

        return regionStats;
    }
}
