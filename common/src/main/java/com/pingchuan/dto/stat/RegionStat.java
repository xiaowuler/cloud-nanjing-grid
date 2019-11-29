package com.pingchuan.dto.stat;

import com.pingchuan.domain.Region;
import lombok.Data;

@Data
public class RegionStat extends Region {
    private int count;
}
