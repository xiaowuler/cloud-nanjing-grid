package com.pingchuan.domain;

import lombok.Data;

@Data
public class Caller {

    private String code;

    private String department;

    private String loginName;

    private String loginPassword;

    private String role;

    private String url;

    private byte enabled;

    private String realName;

}
