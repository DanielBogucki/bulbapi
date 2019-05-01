package com.dbogucki.bulbapi.devices;

import com.dbogucki.bulbapi.commands.Command;
import com.dbogucki.bulbapi.enums.Effect;
import com.dbogucki.bulbapi.exceptions.DeviceSocketException;
import com.dbogucki.bulbapi.exceptions.ResultException;
import com.dbogucki.bulbapi.results.Result;

public abstract class Bulb {

    protected Bulb() {
    }

    public abstract String getIp();
    public abstract int getPort();

    protected abstract Result readResult(int id) throws DeviceSocketException, ResultException;

    protected abstract Result sendCommand(Command command) throws DeviceSocketException, ResultException;

    protected abstract Result setPower(boolean power) throws ResultException, DeviceSocketException;

    public abstract boolean getPower() throws DeviceSocketException, ResultException;

    public abstract int getBright() throws DeviceSocketException, ResultException;

    public abstract String getColorMode() throws DeviceSocketException, ResultException;

    public abstract int getColorTemperature() throws DeviceSocketException, ResultException;

    public abstract int getRGBvalue() throws DeviceSocketException, ResultException;

    public abstract int getHUEvalue() throws DeviceSocketException, ResultException;

    public abstract String getName() throws DeviceSocketException, ResultException;

    public abstract int getDuration();


}
