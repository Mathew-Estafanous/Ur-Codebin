package com.urcodebin.enumerators;

public enum PasteVisibility implements HasStringValue {
    PUBLIC ("Public"),
    PRIVATE ("Private");

    private final String value;
    PasteVisibility(String value) {
        this.value = value;
    }

    public String getStringValue() {
        return value;
    }
}
