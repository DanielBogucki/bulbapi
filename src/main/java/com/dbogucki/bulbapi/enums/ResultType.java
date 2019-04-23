package com.dbogucki.bulbapi.enums;

public enum ResultType {
    YEELIGHT_OK("YeelightOk"),
    YEELIGHT_ERROR("YeelightError"),
    DEFAULT("Default");

    String value;

    ResultType(String value) {
        this.value=value;
    }

    public String getValue(){
        return value;
    }

}
