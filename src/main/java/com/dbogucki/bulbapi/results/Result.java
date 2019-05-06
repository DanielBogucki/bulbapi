package com.dbogucki.bulbapi.results;


public abstract class Result {


    protected Result() {
    }

    public abstract int getId();
    public abstract String[] getResultData();
    public abstract String getCode();
    public abstract String getMessage();
    public abstract boolean checkResult();

}
