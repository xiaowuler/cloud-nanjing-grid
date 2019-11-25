package com.pingchuan.gridapi.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface BaseAction {
    boolean isNeedElementCode();
    int apiId();
}
