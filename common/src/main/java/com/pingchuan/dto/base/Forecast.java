package com.pingchuan.dto.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
public class Forecast {

    @Field("forecast_time")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date forecastTime;

    @Field("time_effect")
    private Integer timeEffect;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double value;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Location> locations;
}
