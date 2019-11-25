package com.pingchuan.gridapi.service.mysql;

import com.pingchuan.domain.Interface;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="provider-mysql")
public interface InterfaceService {
    @RequestMapping(value = "/interface/findOneById", method = RequestMethod.POST)
    Interface findOneById(@RequestParam("id") int id);
}
