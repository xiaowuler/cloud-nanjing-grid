package com.pingchuan.dto.other;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
public class Element {

    @Field("_id")
    private String forecastModel;

    @Field("forecast_time")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date forecastTime;

    @Field("element_codes")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ElementCode> elementCodes;
}
