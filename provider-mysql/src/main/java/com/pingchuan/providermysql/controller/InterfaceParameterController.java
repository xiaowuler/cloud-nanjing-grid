package com.pingchuan.providermysql.controller;

import com.pingchuan.domain.InterfaceParameter;
import com.pingchuan.providermysql.service.InterfaceParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("interfaceParameter")
@RestController
public class InterfaceParameterController {

    @Autowired
    private InterfaceParameterService  interfaceParameterService;

    @PostMapping("findInterfaceParameter")
    public List<InterfaceParameter> findInterfaceParameter(@RequestParam int id){
        return interfaceParameterService.findInterfaceParameter(id);
    }
}
