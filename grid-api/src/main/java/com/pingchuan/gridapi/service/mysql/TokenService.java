package com.pingchuan.gridapi.service.mysql;

import com.pingchuan.domain.Token;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="provider-mysql")
public interface TokenService {

    @RequestMapping(value = "/token/insertOne", method = RequestMethod.POST)
    void insertOne(@RequestBody Token token);

    @RequestMapping(value = "/token/findOneByCallerCode", method = RequestMethod.POST)
    Token findOneByCallerCode(@RequestParam("code") String code);

    @RequestMapping(value = "/token/deleteOneByCallerCode", method = RequestMethod.POST)
    void deleteOneByCallerCode(@RequestParam("code") String code);
}
