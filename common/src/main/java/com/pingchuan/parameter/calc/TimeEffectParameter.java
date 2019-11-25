package com.pingchuan.parameter.calc;

import com.alibaba.fastjson.annotation.JSONField;
import com.pingchuan.parameter.Parameter;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Data
public class TimeEffectParameter extends BaseParameter implements Parameter {

    private String calcType;

    private Integer timeEffect;

    private String elementCode;

    @JSONField(serialize = false)
    private Date forecastDate;

    @Override
    public List<String> checkCode(boolean isNeed) {
        super.checkCode(isNeed);

        elementCode = check.checkString(elementCode, "elementCode");
        if (check.checkTimeEffect(timeEffect) && !StringUtils.isEmpty(startDate)){
            forecastDate = check.ConvertForecastTime(startDate, timeEffect);
        }

        return check.errors;
    }

}
