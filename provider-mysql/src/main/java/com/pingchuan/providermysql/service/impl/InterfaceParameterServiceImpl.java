package com.pingchuan.providermysql.service.impl;

import com.pingchuan.domain.InterfaceParameter;
import com.pingchuan.providermysql.mapper.InterfaceParameterMapper;
import com.pingchuan.providermysql.service.InterfaceParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterfaceParameterServiceImpl implements InterfaceParameterService {

    @Autowired
    private InterfaceParameterMapper interfaceParameterMapper;

    @Override
    public List<InterfaceParameter> findInterfaceParameter(int id) {
        return interfaceParameterMapper.findInterfaceParameter(id);
    }
}
