package com.pingchuan.providermysql.service.impl;

import com.pingchuan.domain.InterfaceLog;
import com.pingchuan.providermysql.mapper.InterfaceLogMapper;
import com.pingchuan.providermysql.mapper.InterfaceMapper;
import com.pingchuan.providermysql.service.InterfaceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Administrator
 */
@Service
@Transactional
public class InterfaceLogServiceImpl implements InterfaceLogService {

    @Autowired
    private InterfaceLogMapper interfaceLogMapper;

    @Override
    public void insertOne(InterfaceLog interfaceLog) {
        interfaceLogMapper.insertOne(interfaceLog);
    }
}
