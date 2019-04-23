package com.dbogucki.bulbapi.enums;


public enum YeelightEffect implements Effect {
    SMOOTH("smooth"),
    SUDDEN("sudden");

    private String value;

    YeelightEffect(String value) {
        this.value = value;
    }


    public String getValue() {
        return this.value;
    }
}

