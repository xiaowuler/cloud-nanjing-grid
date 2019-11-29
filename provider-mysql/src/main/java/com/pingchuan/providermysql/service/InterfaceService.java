package com.pingchuan.providermysql.service;

import com.pingchuan.domain.Interface;

import java.util.List;

public interface InterfaceService {
    Interface findOneById(int id);

    List<Interface> findAll();
}
