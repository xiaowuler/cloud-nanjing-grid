package com.pingchuan.providermysql.service;

import com.pingchuan.domain.InterfaceParameter;

import java.util.List;

public interface InterfaceParameterService {
    List<InterfaceParameter> findInterfaceParameter(int id);
}
