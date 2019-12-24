package com.pingchuan.dto.web;

import lombok.Data;

import java.util.List;

@Data
public class SiteValue {
    private String type;

    private String name;

    private String unit;

    private List<Double> values;

    private List<String> times;

    private List<String> flags;

    private List<Double> windSpeeds;

    private List<String> windDirection;
}
