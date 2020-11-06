package com.urcodebin.enumerators;

public enum PasteVisibility {
    PUBLIC ("Public"),
    PRIVATE ("Private");

    private final String value;
    PasteVisibility(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
