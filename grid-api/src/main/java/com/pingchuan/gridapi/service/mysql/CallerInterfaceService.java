package com.pingchuan.gridapi.service.mysql;

import com.pingchuan.domain.CallerInterface;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="provider-mysql")
public interface CallerInterfaceService {
    @RequestMapping(value = "/callerInterface/findOneByCallerAndInterface", method = RequestMethod.POST)
    CallerInterface findOneByCallerAndInterface(@RequestParam("callerCode") String callerCode, @RequestParam("interfaceId") Integer interfaceId);
}
