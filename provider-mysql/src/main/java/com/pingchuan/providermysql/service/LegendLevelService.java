package com.pingchuan.providermysql.service;

import com.pingchuan.domain.LegendLevel;

import java.util.List;

public interface LegendLevelService {
    List<LegendLevel> findAllByType(String type);
}
