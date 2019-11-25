package com.pingchuan.gridapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {

    private Integer retCode;

    private String retMsg;

    private Object data;

}
