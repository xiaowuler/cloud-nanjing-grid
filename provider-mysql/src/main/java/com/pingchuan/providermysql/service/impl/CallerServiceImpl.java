package com.pingchuan.providermysql.service.impl;

import com.pingchuan.domain.Caller;
import com.pingchuan.providermysql.mapper.CallerMapper;
import com.pingchuan.providermysql.service.CallerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CallerServiceImpl implements CallerService {

    @Autowired
    private CallerMapper callerMapper;

    @Override
    public Caller findOneByUsernameAndPassword(String username, String password) {
        return callerMapper.findOneByUsernameAndPassword(username, password);
    }

    @Override
    public Caller findOneByLoginName(String loginName) {
        return callerMapper.findOneByLoginName(loginName);
    }
}
