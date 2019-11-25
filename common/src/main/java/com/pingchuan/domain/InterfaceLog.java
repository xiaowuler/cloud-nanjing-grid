package com.pingchuan.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author xiaowuler
 * 日志类
 */

@Data
public class InterfaceLog {

    private Integer id;

    private Integer interfaceId;

    private String parameters;

    private byte state;

    private String callerCode;

    private String errorMessage;

    private Timestamp requestStartTime;

    private Timestamp executeStartTime;

    private Timestamp executeEndTime;

    private Timestamp requestEndTime;

    private String requestType;

    private String hostAddress;

    private String regionCode;

    private Integer resultCode;
}
