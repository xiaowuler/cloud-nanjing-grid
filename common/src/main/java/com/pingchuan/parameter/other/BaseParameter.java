package com.pingchuan.parameter.other;

import com.alibaba.fastjson.annotation.JSONField;
import com.pingchuan.parameter.CheckParameter;
import com.pingchun.utils.SignUtil;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.List;

@Data
public class BaseParameter {

    protected String token;

    protected String forecastModel;

    @JSONField(serialize = false)
    public String callerCode;

    @JSONField(serialize = false)
    protected CheckParameter check;

    public List<String> checkCode(boolean isNeedElementCode){

        check = new CheckParameter();
        forecastModel = check.checkString(forecastModel, "forecastModel");

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
