package com.pingchuan.providermysql.controller;

import com.pingchuan.domain.InterfaceLog;
import com.pingchuan.providermysql.service.InterfaceLogService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiaowuler
 */

@RequestMapping("interfaceLog")
@RestController
public class InterfaceLogController {

    @Autowired
    private InterfaceLogService interfaceLogService;

    @PostMapping("/insertOne")
    public void insertOne(@RequestBody InterfaceLog interfaceLog){
        interfaceLogService.insertOne(interfaceLog);
    }

}
