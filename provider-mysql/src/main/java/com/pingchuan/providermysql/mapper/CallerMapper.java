package com.pingchuan.providermysql.mapper;

import com.pingchuan.domain.Caller;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CallerMapper {
    Caller findOneByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
