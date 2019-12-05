package com.pingchuan.dto.web;

import com.pingchuan.domain.LegendLevel;
import lombok.Data;

import java.util.List;

@Data
public class Box {
    private double startLon;

    private double endLon;

    private double startLat;

    private double endLat;

    private double value;

    private String windDirection;

    private double windSpeedLevel;

    private String flag;

    private List<LegendLevel> legendLevels;
}
