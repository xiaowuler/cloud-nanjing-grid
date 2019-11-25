package com.pingchuan.parameter.calc;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.pingchuan.parameter.Parameter;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RoutineParameter extends BaseParameter implements Parameter {

    private String elementCode;

    private String calcType;

    private String forecastTime;

    @JSONField(serialize = false)
    private Date forecastDate;

    @Override
    public List<String> checkCode(boolean isNeed) {
        super.checkCode(isNeed);
        elementCode = check.checkString(elementCode, "elementCode");
        forecastDate = check.checkTime(forecastTime, "forecastTime");
        return check.errors;
    }
}
