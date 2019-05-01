package com.dbogucki.bulbapi.results;

import com.google.gson.annotations.Expose;

public class YeelightResultFailed extends YeelightResult {

    @Expose
    private YeelightErrorFailedDetails error;

    public YeelightResultFailed() {
    }

    @Override
    public String[] getResultData() {
        return new String[]{error.getCode(), error.getMessage()};
    }

    @Override
    public String getCode() {
        return error.getCode();
    }

    @Override
    public String getMessage() {
        return error.getMessage();
    }

    @Override
    public boolean checkResult() {
        return false;
    }

    private static class YeelightErrorFailedDetails {

        @Expose
        private String code;

        @Expose
        private String message;

        public YeelightErrorFailedDetails() {
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
