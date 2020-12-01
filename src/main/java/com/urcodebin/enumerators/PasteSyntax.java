package com.urcodebin.enumerators;

public enum PasteSyntax {
    NONE ("None"),
    JAVA ("Java"),
    CSHARP ("C#"),
    Python ("Python"),
    JAVASCRIPT ("JavaScript"),
    GO ("Go"),
    SQL ("SQL"),
    CLANG ("C"),
    CPLUSPLUS ("C++"),
    PHP ("PHP"),
    SWIFT ("Swift"),
    LUA ("Lua"),
    RUBY ("Ruby");

    private final String value;
    PasteSyntax(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
