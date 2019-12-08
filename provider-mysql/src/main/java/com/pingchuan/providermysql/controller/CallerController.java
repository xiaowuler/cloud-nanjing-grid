package com.pingchuan.providermysql.controller;

import com.pingchuan.domain.Caller;
import com.pingchuan.providermysql.service.CallerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("caller")
@RestController
public class CallerController {

    @Autowired
    private CallerService callerService;

    @PostMapping("/findOneByUsernameAndPassword")
    public Caller findOneByUsernameAndPassword(String username, String password){
        return callerService.findOneByUsernameAndPassword(username, password);
    }

    @PostMapping("/findOneByLoginName")
    public Caller findOneByLoginName(@RequestParam String loginName){
        return callerService.findOneByLoginName(loginName);
    }
}
