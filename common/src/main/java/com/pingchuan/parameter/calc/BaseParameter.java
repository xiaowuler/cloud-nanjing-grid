package com.pingchuan.parameter.calc;

import com.alibaba.fastjson.annotation.JSONField;
import com.pingchuan.parameter.CheckParameter;
import com.pingchun.utils.SignUtil;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Data
public class BaseParameter {

    protected String token;

    protected String areaCode;

    protected String location;

    protected String updateTime;

    protected String startTime;

    protected String forecastModel;

    @JSONField(serialize = false)
    protected Date updateDate;

    @JSONField(serialize = false)
    protected Date startDate;

    @JSONField(serialize = false)
    public String callerCode;

    @JSONField(serialize = false)
    public List<double[]> locations;

    @JSONField(serialize = false)
    protected CheckParameter check;

    public List<String> checkCode(boolean isNeed){
        check = new CheckParameter();
        startDate = check.checkTime(startTime, "startTime");
        updateDate = check.checkTime(updateTime, "updateTime");
        forecastModel = check.checkString(forecastModel, "forecastModel");
        if (isNeed) {
            areaCode = check.checkString(areaCode, "areaCode");
        }else {
            locations = check.checkLocation(location);
        }

        return null;
    }

    public boolean verifyToken(){

        if (StringUtils.isEmpty(token)) {
            return false;
        }

        callerCode = SignUtil.getClaim(token, "userCode");
        return SignUtil.verify(token);
    }
}
