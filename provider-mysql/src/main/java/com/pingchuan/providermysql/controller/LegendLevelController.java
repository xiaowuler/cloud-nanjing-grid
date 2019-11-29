package com.pingchuan.providermysql.controller;

import com.pingchuan.domain.LegendLevel;
import com.pingchuan.providermysql.service.LegendLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("legendLevel")
@RestController
public class LegendLevelController {

    @Autowired
    private LegendLevelService legendLevelService;

    @PostMapping("/findAllByType")
    public List<LegendLevel> findAllByType(String type){
        return legendLevelService.findAllByType(type);
    }

}
