package com.dbogucki.bulbapi.results;

import com.google.gson.annotations.Expose;

public abstract class YeelightResult extends Result {

    @Expose
    protected int id;

    public int getId(){
        return id;
    }


}
