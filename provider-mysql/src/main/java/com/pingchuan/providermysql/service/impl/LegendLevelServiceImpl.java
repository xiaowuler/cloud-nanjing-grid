package com.pingchuan.providermysql.service.impl;

import com.pingchuan.domain.LegendLevel;
import com.pingchuan.providermysql.mapper.LegendLevelMapper;
import com.pingchuan.providermysql.service.LegendLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LegendLevelServiceImpl implements LegendLevelService {

    @Autowired
    private LegendLevelMapper legendLevelMapper;

    @Override
    public List<LegendLevel> findAllByType(String type) {
        return legendLevelMapper.findAllByType(type);
    }
}
