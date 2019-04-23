package com.dbogucki.bulbapi.results;

public class NullResult extends Result {

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String[] getResultData() {
        return new String[]{"error"};
    }

    @Override
    public String getCode() {
        return "0";
    }

    @Override
    public String getMessage() {
        return "<No result found>";
    }


    @Override
    public boolean checkResult() {
        return false;
    }
}
