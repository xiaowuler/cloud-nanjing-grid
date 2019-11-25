package com.pingchuan.parameter.calc;

import com.alibaba.fastjson.annotation.JSONField;
import com.pingchuan.parameter.Parameter;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TimeRangeParameter extends BaseParameter implements Parameter {

    private String calcType;

    private String startForecastTime;

    private String endForecastTime;

    private String elementCode;

    @JSONField(serialize = false)
    private Date startForecastDate;

    @JSONField(serialize = false)
    private Date endForecastDate;

    @Override
    public List<String> checkCode(boolean isNeed) {
        super.checkCode(isNeed);

        startForecastDate = check.checkTime(this.startForecastTime, "startForecastTime");
        endForecastDate = check.checkTime(this.endForecastTime, "endForecastTime");
        elementCode = check.checkString(elementCode, "elementCode");

        return check.errors;
    }
}
