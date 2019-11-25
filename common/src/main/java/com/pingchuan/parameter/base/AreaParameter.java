package com.pingchuan.parameter.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.pingchuan.parameter.Parameter;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author xiaowu
 */

@Data
public class AreaParameter extends BaseParameter implements Parameter {

    private String areaCode;

    private String forecastTime;

    @JSONField(serialize = false)
    private Date forecastDate;

    @Override
    public List<String> checkCode(boolean isNeed) {

        super.checkCode(isNeed);

        forecastDate = check.checkTime(forecastTime, "forecastTime");
        areaCode = check.checkString(areaCode, "areaCode");

        return check.errors;
    }
}
