package com.pingchuan.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Trapezoid {
    @Id
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ObjectId id;

    @Field("trapezoid_info_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ObjectId trapezoidInfoId;

    @Field("grid_code")
    private String gridCode;

    @Field("area_code")
    private String areaCode;

    @Field("area_name")
    private String areaName;

    private Double[] loc;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
}
