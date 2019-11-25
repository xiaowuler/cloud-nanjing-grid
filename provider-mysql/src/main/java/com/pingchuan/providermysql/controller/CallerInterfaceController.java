package com.pingchuan.providermysql.controller;

import com.pingchuan.domain.CallerInterface;
import com.pingchuan.providermysql.service.CallerInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("callerInterface")
public class CallerInterfaceController {

    @Autowired
    private CallerInterfaceService callerInterfaceService;

    @PostMapping("/findOneByCallerAndInterface")
    public CallerInterface findOneByCallerAndInterface(String callerCode, Integer interfaceId){
        return callerInterfaceService.findOneByCallerAndInterface(callerCode, interfaceId);
    }

}
