package com.pingchuan.parameter.real;

import com.alibaba.fastjson.annotation.JSONField;
import com.pingchuan.parameter.Parameter;
import lombok.Data;

import java.util.List;

@Data
public class LocationParameter extends BaseParameter implements Parameter {

    private String location;

    @JSONField(serialize = false)
    private List<double[]> locations;

    @Override
    public String getAreaCode() {
        return null;
    }

    @Override
    public List<String> checkCode(boolean isNeed) {
        super.checkCode(isNeed);

        locations = check.checkLocation(location);

        return check.errors;
    }
}
