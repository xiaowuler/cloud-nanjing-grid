package com.pingchuan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaowuler
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    private String callerCode;

    private String token;
}
