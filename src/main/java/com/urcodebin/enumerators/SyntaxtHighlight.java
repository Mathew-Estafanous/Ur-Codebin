package com.urcodebin.enumerators;

public enum SyntaxtHighlight {
    NONE ("None"),
    JAVA ("Java"),
    CSHARP ("C#"),
    Python ("Python");

    private final String value;
    private SyntaxtHighlight(String value) {
        this.value = value;
    }

    private String getValue() {
        return value;
    }
}
