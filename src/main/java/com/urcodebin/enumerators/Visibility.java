package com.urcodebin.enumerators;

public enum Visibility {
    PUBLIC ("Public"),
    PRIVATE ("Private");

    private final String value;
    Visibility(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
