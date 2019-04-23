package com.dbogucki.bulbapi.enums;

public enum Category {
    YEELIGHT("Yeelight"),
    DEFAULT("Default");

    private String value;

    Category(String type) {
        this.value = type;
    }
    
    public String getValue(){return value;}
}
