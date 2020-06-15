package com.enump;

public enum RoleEnum {

    ROLE_USER("ROLE_USER"),
    ROLE_MANAGE("ROLE_MANAGE"),
    ROLE_ADMIN("ROLE_ADMIN");

    private String name;

    RoleEnum(String name) {
        this.name = name;
    }
}
