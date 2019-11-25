package com.pingchuan.dto.calc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
public class Location {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private double[] loc;

    @Field("grid_code")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String gridCode;

    @Field("area_code")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String areaCode;

    @Field("area_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String areaName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CalcValue max;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CalcValue min;

    @Field("start_forecast_time")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date startForecastTime;

    @Field("end_forecast_time")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date endForecastTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double avg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<StatValue> values;
}
