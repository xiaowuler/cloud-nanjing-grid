package com.pingchuan.dto.calc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pingchuan.dto.base.ElementCode;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
public class CalcElement {
    @Field("area_code")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String areaCode;

    @Field("area_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String areaName;

    @Field("update_time")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Field("start_time")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @Field("forecast_model")
    private String forecastModel;

    @Field("element_code")
    private String elementCode;

    @Field("forecast_time")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date forecastTime;

    @Field("time_effect")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer timeEffect;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CalcValue max;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CalcValue min;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double avg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Location> locations;
}
