package com.urcodebin.enumerators;

public enum PasteExpiration implements HasStringValue {
    TENMINUTES ("10 Minutes", 10),
    THIRTYMINUTES ("30 Minutes", 30),
    ONEHOUR ("1 Hour", 60),
    FIVEHOURS ("5 Hours", 300),
    ONEDAY ("1 Day", 1440),
    THREEDAYS("3 Days", 4320);

    private final String value;
    private final int offsetMin;
    PasteExpiration(String value, int offsetMin ) {
        this.value = value;
        this.offsetMin = offsetMin;
    }

    public String getStringValue(){
        return value;
    }

    public int getOffsetMin() {
        return offsetMin;
    }
}
