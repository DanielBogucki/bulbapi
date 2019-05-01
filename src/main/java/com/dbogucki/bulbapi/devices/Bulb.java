package com.dbogucki.bulbapi.devices;

import com.dbogucki.bulbapi.commands.Command;
import com.dbogucki.bulbapi.exceptions.DeviceSocketException;
import com.dbogucki.bulbapi.exceptions.ResultException;
import com.dbogucki.bulbapi.results.Result;

public abstract class Bulb {

    protected Bulb() {
    }

    public abstract String getIp();
    public abstract int getPort();

    protected abstract Result readResult(int id) throws DeviceSocketException;

    protected abstract Result sendCommand(Command command) throws DeviceSocketException;

    protected abstract Result setPower(boolean power) throws ResultException, DeviceSocketException;
}
