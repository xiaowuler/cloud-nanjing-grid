package com.pingchuan.providermysql.mapper;

import com.pingchuan.domain.InterfaceParameter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InterfaceParameterMapper {
    List<InterfaceParameter> findInterfaceParameter(@Param("id") int id);
}
