package com.dbogucki.bulbapi.commands;

import com.google.gson.annotations.Expose;

public abstract class Command {
    protected static int ID_COMMAND = 1;

    @Expose
    protected int id;

    public Command(){
        this.id = generateId();
    }

    private static int generateId() {
        return ID_COMMAND++;
    }

    public int getId() {
        return id;
    }

    public abstract String toJson();


}
