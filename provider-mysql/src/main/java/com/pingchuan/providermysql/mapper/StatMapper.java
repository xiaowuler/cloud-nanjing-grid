package com.pingchuan.providermysql.mapper;

import com.pingchuan.dto.stat.RegionStat;
import com.pingchuan.dto.stat.TypeStat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface StatMapper {
    List<RegionStat> findAreaCallByTimeRange(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<TypeStat> findTypeCallByTimeRange(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
