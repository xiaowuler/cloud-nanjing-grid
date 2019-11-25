package com.pingchuan.parameter.base;

import com.pingchuan.parameter.Parameter;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ThresholdAreaParameter extends BaseParameter implements Parameter {

    private String areaCode;

    private String forecastTime;

    private String threshold;

    private List<double[]> thresholdValues;

    private Date forecastDate;

    @Override
    public List<String> checkCode(boolean isNeed) {
        super.checkCode(isNeed);

        areaCode = check.checkString(areaCode, "areaCode");
        forecastDate = check.checkTime(forecastTime, "forecastTime");
        thresholdValues = check.checkThreshold(threshold);

        return check.errors;
    }
}
