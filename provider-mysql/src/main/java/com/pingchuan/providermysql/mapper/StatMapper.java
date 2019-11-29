package com.pingchuan.providermysql.mapper;

import com.pingchuan.dto.stat.RegionStat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface StatMapper {
    List<RegionStat> findAreaCallByTimeRange(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
