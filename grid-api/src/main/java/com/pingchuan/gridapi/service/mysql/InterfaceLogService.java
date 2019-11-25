package com.pingchuan.gridapi.service.mysql;

import com.pingchuan.domain.InterfaceLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="provider-mysql")
public interface InterfaceLogService {
    @RequestMapping(value = "/interfaceLog/insertOne", method = RequestMethod.POST)
    void insertOne(@RequestBody InterfaceLog interfaceLog);
}
