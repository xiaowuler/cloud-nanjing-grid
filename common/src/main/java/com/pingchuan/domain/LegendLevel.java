package com.pingchuan.domain;

import lombok.Data;

@Data
public class LegendLevel {
    private int levelId;
    private double beginValue;
    private double endValue;
    private String color;
    private String type;
}
