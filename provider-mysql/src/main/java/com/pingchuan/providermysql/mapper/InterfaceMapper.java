package com.pingchuan.providermysql.mapper;

import com.pingchuan.domain.Interface;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InterfaceMapper {
    Interface findOneById(int id);

    List<Interface> findAll();

}
