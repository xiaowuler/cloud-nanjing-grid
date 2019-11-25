package com.pingchuan.parameter.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.pingchuan.parameter.Parameter;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class LocationParameter extends BaseParameter implements Parameter {

    private String location;

    private String forecastTime;

    @JSONField(serialize = false)
    private Date forecastDate;

    @JSONField(serialize = false)
    private List<double[]> locations;

    @Override
    public List<String> checkCode(boolean isNeed) {

        super.checkCode(isNeed);

        forecastDate = check.checkTime(forecastTime, "forecastTime");
        locations = check.checkLocation(location);

        return check.errors;
    }

    @Override
    public String getAreaCode() {
        return null;
    }
}
