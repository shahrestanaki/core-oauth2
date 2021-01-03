package com.enump;

public enum RoleSingUpEnum {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER"),
    ROLE_SYSTEM("ROLE_SYSTEM"),
    ROLE_SUPPORT("ROLE_SUPPORT"),
    ROLE_LOCAL("ROLE_LOCAL"),
    ROLE_SOFTWARE("ROLE_SOFTWARE"),
    ROLE_BASIC("ROLE_BASIC"),
    ROLE_SECURITY("ROLE_SECURITY"),
    ROLE_REPORT("ROLE_REPORT"),
    ROLE_COMPANY("ROLE_COMPANY"),
    ROLE_TEST("ROLE_TEST"),
    ROLE_DEVELOPER("ROLE_DEVELOPER");

    private String name;

    RoleSingUpEnum(String name) {
        this.name = name;
    }
}
