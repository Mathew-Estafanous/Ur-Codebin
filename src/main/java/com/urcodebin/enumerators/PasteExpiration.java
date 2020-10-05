package com.urcodebin.enumerators;

public enum PasteExpiration {
    TENMINUTES ("10 Minutes"),
    ONEHOUR ("1 Hour"),
    FIVEHOURS ("5 Hours"),
    ONEDAY ("1 Day");

    private String value;
    PasteExpiration(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
