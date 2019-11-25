package com.pingchuan.parameter.calc;

import com.alibaba.fastjson.annotation.JSONField;
import com.pingchuan.parameter.Parameter;
import lombok.Data;

import java.util.List;

@Data
public class SpecialParameter extends BaseParameter implements Parameter {

    private String calcType;

    @JSONField(serialize = false)
    private int totalHour;

    @JSONField(serialize = false)
    private int timeInterval;

    @JSONField(serialize = false)
    private String elementCode;

    @Override
    public String getAreaCode() {
        return null;
    }

    @Override
    public List<String> checkCode(boolean isNeed) {
        totalHour = 72;
        forecastModel = "NJGRID-STANDARD";

        super.checkCode(isNeed);
        return check.errors;
    }
}
