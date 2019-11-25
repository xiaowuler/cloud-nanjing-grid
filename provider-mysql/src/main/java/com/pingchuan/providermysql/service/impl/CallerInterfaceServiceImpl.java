package com.pingchuan.providermysql.service.impl;

import com.pingchuan.domain.CallerInterface;
import com.pingchuan.providermysql.mapper.CallerInterfaceMapper;
import com.pingchuan.providermysql.service.CallerInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CallerInterfaceServiceImpl implements CallerInterfaceService {

    @Autowired
    private CallerInterfaceMapper callerInterfaceMapper;

    @Override
    public CallerInterface findOneByCallerAndInterface(String callerCode, Integer interfaceId) {
        return callerInterfaceMapper.findOneByCallerAndInterface(callerCode, interfaceId);
    }
}
