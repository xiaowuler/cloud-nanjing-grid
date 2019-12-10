package com.pingchuan.domain;

import lombok.Data;

import java.util.List;

@Data
public class InterfaceType {

    private int id;

    private String name;

    private int sequence;

    private List<Interface> interfaces;

}
