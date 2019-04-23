package com.dbogucki.bulbapi.results;

import com.google.gson.annotations.Expose;

public abstract class Result {


    protected Result() {
    }

    public abstract int getId();
    public abstract String[] getResultData();
    public abstract String getCode();
    public abstract String getMessage();
    public abstract boolean checkResult();

}
