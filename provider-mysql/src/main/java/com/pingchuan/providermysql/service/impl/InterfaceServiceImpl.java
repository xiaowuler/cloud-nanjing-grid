package com.pingchuan.providermysql.service.impl;

import com.pingchuan.domain.Interface;
import com.pingchuan.domain.InterfaceType;
import com.pingchuan.providermysql.mapper.InterfaceMapper;
import com.pingchuan.providermysql.mapper.InterfaceTypeMapper;
import com.pingchuan.providermysql.service.InterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xiaowuler
 */

@Service
@Transactional
public class InterfaceServiceImpl implements InterfaceService {

    @Autowired
    private InterfaceMapper interfaceMapper;

    @Autowired
    private InterfaceTypeMapper interfaceTypeMapper;

    @Override
    public Interface findOneById(int id) {
        return interfaceMapper.findOneById(id);
    }

    @Override
    public List<Interface> findAll() {
        return interfaceMapper.findAll();
    }

    @Override
    public List<InterfaceType> findInterfaceDetail() {
        List<InterfaceType> interfaceTypes = interfaceTypeMapper.findAll();
        for(InterfaceType interfaceType : interfaceTypes){
            List<Interface> interfaces = interfaceMapper.findAllByTypeId(interfaceType.getId());
            interfaceType.setInterfaces(interfaces);
        }
        return interfaceTypes;
    }
}
