package com.pingchuan.providermysql.mapper;

import com.pingchuan.domain.Region;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RegionMapper {
    List<Region> findAll();
}
