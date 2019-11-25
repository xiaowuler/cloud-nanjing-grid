package com.pingchuan.providermysql.mapper;

import com.pingchuan.domain.Interface;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InterfaceMapper {
    Interface findOneById(int id);
}
