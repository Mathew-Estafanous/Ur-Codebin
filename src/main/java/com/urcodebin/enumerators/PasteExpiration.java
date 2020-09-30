package com.urcodebin.enumerators;

public enum PasteExpiration {
    TENMINUTES ("10 Minutes"),
    ONEHOUR ("1 Hour"),
    FIVEHOURS ("5 Hours"),
    ONEDAY ("1 Day");

    private String value;
    private PasteExpiration(String value) {
        this.value = value;
    }

    private String getValue(){
        return value;
    }
}
