package com.pingchuan.dto.real;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class Element {

    @Field("_id")
    private String elementCode;

    @Field("area_code")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String areaCode;

    @Field("area_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String areaName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Real> reals;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Location> locations;
}
