package com.dbogucki.bulbapi.results;

import com.google.gson.annotations.Expose;

public class YeelightResultOk extends YeelightResult {

    @Expose
    private String[] result;

    public YeelightResultOk() {
    }

    @Override
    public String[] getResultData() {
        return result;
    }

    @Override
    public String getCode() {
        return "0";
    }

    @Override
    public String getMessage() {
        return "ok";
    }

    @Override
    public boolean checkResult() {
        return true;
    }

}
