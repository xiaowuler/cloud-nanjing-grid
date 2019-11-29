package com.pingchuan.dto.web;

import com.pingchuan.domain.LegendLevel;
import lombok.Data;

import java.util.List;

@Data
public class DrawResult {

    private List<Box> box;

    private List<LegendLevel> legendLevels;
}
