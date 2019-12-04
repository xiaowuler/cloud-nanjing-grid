package com.pingchuan.providermysql.controller;

import com.pingchuan.domain.Interface;
import com.pingchuan.domain.InterfaceType;
import com.pingchuan.providermysql.service.InterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xiaowuler
 */
@RestController
@RequestMapping("interface")
public class InterfaceController {

    @Autowired
    private InterfaceService interfaceService;

    @PostMapping("findOneById")
    public Interface findOneById(int id){
        return interfaceService.findOneById(id);
    }

    @PostMapping("findAll")
    public List<Interface> findAll(){
        return interfaceService.findAll();
    }

    @PostMapping("findInterfaceDetail")
    public List<InterfaceType> findInterfaceDetail(){
        return interfaceService.findInterfaceDetail();
    }

}
