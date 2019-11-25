package com.pingchuan.dto.other;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
public class StartTime {

    @Field("start_time")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date startTime;

    @Field("update_time")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UpdateTime updateTime;

    @Field("time_effect")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer timeEffect;

    @Field("updates")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<UpdateTime> updates;

    @Field("update_times")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private List<Date> updateTimes;

}
