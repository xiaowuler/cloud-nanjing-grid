package com.pingchuan.parameter.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.pingchuan.parameter.Parameter;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Data
public class TimeEffectParameter extends BaseParameter implements Parameter {

    private Integer timeEffect;

    private String areaCode;

    @JSONField(serialize = false)
    private Date forecastDate;

    @Override
    public List<String> checkCode(boolean isNeed) {

        super.checkCode(isNeed);

        if (check.checkTimeEffect(timeEffect) && !StringUtils.isEmpty(startDate)) {
            forecastDate = check.ConvertForecastTime(startDate, timeEffect);
        }

        areaCode = check.checkString(areaCode, "areaCode");

        return check.errors;
    }
}
