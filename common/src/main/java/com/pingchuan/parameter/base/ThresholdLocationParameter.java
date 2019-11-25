package com.pingchuan.parameter.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.pingchuan.parameter.Parameter;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ThresholdLocationParameter extends BaseParameter implements Parameter {

    private String location;

    @JSONField(serialize = false)
    private List<double[]> locations;

    private String forecastTime;

    private String threshold;

    @JSONField(serialize = false)
    private List<double[]> thresholdValues;

    @JSONField(serialize = false)
    private Date forecastDate;

    @Override
    public List<String> checkCode(boolean isNeed) {
        super.checkCode(isNeed);

        forecastDate = check.checkTime(forecastTime, "forecastTime");
        thresholdValues = check.checkThreshold(threshold);
        locations = check.checkLocation(location);

        return check.errors;
    }

    @Override
    public String getAreaCode() {
        return null;
    }
}
