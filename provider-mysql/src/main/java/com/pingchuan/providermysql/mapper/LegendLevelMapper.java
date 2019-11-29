package com.pingchuan.providermysql.mapper;

import com.pingchuan.domain.LegendLevel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LegendLevelMapper {
    List<LegendLevel> findAllByType(@Param("type") String type);
}
