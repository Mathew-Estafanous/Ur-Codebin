package com.urcodebin.enumerators;

public enum PasteExpiration {
    TENMINUTES ("10 Minutes"),
    THIRTYMINUTES ("30 Minutes"),
    ONEHOUR ("1 Hour"),
    FIVEHOURS ("5 Hours"),
    ONEDAY ("1 Day"),
    THREEDAYS("3 Days");

    private String value;
    PasteExpiration(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
