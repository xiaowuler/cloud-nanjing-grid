package com.pingchuan.providermysql.controller;

import com.pingchuan.domain.Token;
import com.pingchuan.providermysql.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/insertOne")
    public void insertOne(@RequestBody Token token){
        tokenService.insertOne(token);
    }

    @PostMapping("/findOneByCallerCode")
    public Token findOneByCallerCode(String code){
        return tokenService.findOneByCallerCode(code);
    }

    @PostMapping("/deleteOneByCallerCode")
    public void deleteOneByCallerCode(String code){
        tokenService.deleteOneByCallerCode(code);
    }
}
