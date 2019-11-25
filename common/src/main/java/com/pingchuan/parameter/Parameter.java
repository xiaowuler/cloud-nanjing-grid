package com.pingchuan.parameter;

import java.util.List;

public interface Parameter {

    List<String> checkCode(boolean isNeed);

    boolean verifyToken();

    String getCallerCode();

    String getAreaCode();

    default void setCalcType(String calcType){};

}
