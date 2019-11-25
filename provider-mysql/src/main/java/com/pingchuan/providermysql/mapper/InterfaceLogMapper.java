package com.pingchuan.providermysql.mapper;

import com.pingchuan.domain.InterfaceLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author xiaowuler
 */

@Mapper
public interface InterfaceLogMapper {
    void insertOne(@Param("interfaceLog") InterfaceLog interfaceLog);
}
