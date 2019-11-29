package com.pingchuan.weatherservice.service;

import com.pingchuan.domain.Interface;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name="provider-mysql")
public interface InterfaceService {

    @RequestMapping(value = "interface/findAll", method = RequestMethod.POST)
    List<Interface> findAll();
}
