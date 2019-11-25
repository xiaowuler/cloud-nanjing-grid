package com.pingchuan.gridapi.service.mysql;

import com.pingchuan.domain.Caller;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="provider-mysql")
public interface CallerService {
    @RequestMapping(value = "/caller/findOneByUsernameAndPassword", method = RequestMethod.POST)
    Caller findOneByUsernameAndPassword(@RequestParam("username") String username, @RequestParam("password") String password);
}
