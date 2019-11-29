package com.pingchuan.dto.stat;

import com.pingchuan.domain.InterfaceType;
import lombok.Data;

@Data
public class TypeStat extends InterfaceType {

    private double totalTime;

    private int count;
}
