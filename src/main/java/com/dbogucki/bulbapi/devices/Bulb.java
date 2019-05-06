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

    protected abstract Result readResult(int id) throws ResultException, DeviceSocketException;

    protected abstract Result sendCommand(Command command) throws ResultException, DeviceSocketException;

    public abstract Result setPower(boolean power) throws ResultException, DeviceSocketException;
    public abstract Result setBrightness(int value) throws ResultException, DeviceSocketException;
    public abstract Result setColorTemperature(int value) throws ResultException, DeviceSocketException;
    public abstract Result setRGB(int red, int green, int blue) throws ResultException, DeviceSocketException;
    public abstract Result setHSV(int hue, int sat) throws ResultException, DeviceSocketException;
    public abstract Result setName(String name) throws ResultException, DeviceSocketException;
    public abstract Result setDefault() throws ResultException, DeviceSocketException;
    public abstract Result toggle() throws ResultException, DeviceSocketException;

    public abstract boolean getPower() throws DeviceSocketException, ResultException;

    public abstract int getBright() throws DeviceSocketException, ResultException;

    public abstract String getColorMode() throws DeviceSocketException, ResultException;

    public abstract int getColorTemperature() throws DeviceSocketException, ResultException;

    public abstract int getRGBvalue() throws DeviceSocketException, ResultException;

    public abstract int getHUEvalue() throws DeviceSocketException, ResultException;

    public abstract String getName() throws DeviceSocketException, ResultException;

    public abstract int getDuration();


}
