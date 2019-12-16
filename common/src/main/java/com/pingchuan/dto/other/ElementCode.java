package com.pingchuan.dto.other;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
public class ElementCode {

    @Field("element_code")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String elementCode;

    @Field("update_time")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date updateTime;

    @Field("start_time")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @Field("start_times")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<StartTime> startTimes;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Forecast> forecasts;

    @Field("update_times")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<UpdateTime> updateTimes;

}
