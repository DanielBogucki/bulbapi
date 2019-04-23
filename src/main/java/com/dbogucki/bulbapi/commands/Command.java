package com.dbogucki.bulbapi.commands;

public abstract class Command {
    protected static int ID_COMMAND = 1;

    protected int id;

    protected static int generateId() {
        return ID_COMMAND++;
    }

    public int getId() {
        return id;
    }

    public abstract String toJson();


}
