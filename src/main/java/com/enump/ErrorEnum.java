package com.enump;

public enum ErrorEnum {
    General("General"),
    ValidArg("ValidArg"),
    ValidBusiness("ValidBusiness");

    private String name;

    ErrorEnum(String name) {
        this.name = name;
    }

}
