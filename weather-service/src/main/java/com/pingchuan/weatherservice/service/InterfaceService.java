package com.pingchuan.weatherservice.service;

import com.pingchuan.domain.Interface;
import com.pingchuan.domain.InterfaceParameter;
import com.pingchuan.domain.InterfaceType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="provider-mysql")
public interface InterfaceService {

    @RequestMapping(value = "interface/findAll", method = RequestMethod.POST)
    List<Interface> findAll();

    @RequestMapping(value = "interface/findInterfaceDetail", method = RequestMethod.POST)
    List<InterfaceType> findInterfaceDetail();

    @RequestMapping(value = "interfaceParameter/findInterfaceParameter", method = RequestMethod.POST)
    List<InterfaceParameter> findInterfaceParameter(@RequestParam int id);
}
