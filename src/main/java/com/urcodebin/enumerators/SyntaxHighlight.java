package com.urcodebin.enumerators;

public enum SyntaxHighlight {
    NONE ("None"),
    JAVA ("Java"),
    CSHARP ("C#"),
    Python ("Python");

    private final String value;
    SyntaxHighlight(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
