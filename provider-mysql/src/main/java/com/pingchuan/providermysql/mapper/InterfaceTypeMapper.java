package com.pingchuan.providermysql.mapper;

import com.pingchuan.domain.InterfaceType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InterfaceTypeMapper {

    List<InterfaceType> findAll();

}
