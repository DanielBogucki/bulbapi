package com.dbogucki.bulbapi.commands;

import com.dbogucki.bulbapi.utils.DataFormatter;
import com.google.gson.annotations.Expose;

public class YeelightCommand extends Command {

    @Expose
    private String method;
    @Expose
    private Object[] params;

    public YeelightCommand(String method, Object... params) {
        super();
        this.method = method;
        this.params = params;
    }

    public String toJson() {
        return DataFormatter.GSON.toJson(this);
    }

}
