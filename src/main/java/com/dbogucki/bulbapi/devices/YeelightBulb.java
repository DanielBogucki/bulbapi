package com.dbogucki.bulbapi.devices;

import com.dbogucki.bulbapi.commands.Command;
import com.dbogucki.bulbapi.commands.YeelightCommand;
import com.dbogucki.bulbapi.enums.ResultType;
import com.dbogucki.bulbapi.enums.YeelightEffect;
import com.dbogucki.bulbapi.exceptions.DeviceSocketException;
import com.dbogucki.bulbapi.exceptions.ResultException;
import com.dbogucki.bulbapi.results.NullResult;
import com.dbogucki.bulbapi.results.Result;
import com.dbogucki.bulbapi.sockets.YeelightSocketHandler;
import com.dbogucki.bulbapi.utils.DataFormatter;
import com.google.gson.JsonSyntaxException;

public class YeelightBulb extends Bulb {
    private String name;
    private final YeelightSocketHandler socketHandler;
    private int duration;
    private YeelightEffect effect;

    public YeelightBulb(String ip, int port) throws DeviceSocketException {

        this.socketHandler = new YeelightSocketHandler(ip, port);
    }

    public YeelightBulb(String ip) throws DeviceSocketException {
        this(ip, 55443);
    }

    protected Result readResult(int id) throws DeviceSocketException {
        Result result = new NullResult();
        String data;
        do {
            data = this.socketHandler.readLine();
            try {
                result = DataFormatter.generateResultFromJson(data, ResultType.YEELIGHT_OK);
                if (result.getResultData() == null) throw new JsonSyntaxException("Probably error result");
                return result;
            } catch (JsonSyntaxException e) {
                result = DataFormatter.generateResultFromJson(data, ResultType.YEELIGHT_ERROR);
                return result;
            }

        } while (true);
    }

    protected Result sendCommand(Command command) throws DeviceSocketException {
        String jsonCommand = command.toJson() + "\r\n";
        this.socketHandler.send(jsonCommand);
        return this.readResult(command.getId());
    }


    public Result setPower(boolean power) throws ResultException, DeviceSocketException {
        String powerStr = power ? "on" : "off";
        YeelightCommand command = new YeelightCommand("set_power", powerStr, YeelightEffect.SUDDEN, 500);
        return this.sendCommand(command);
    }

    public String getIp() {
        return socketHandler.getIp();
    }

    public int getPort() {
        return socketHandler.getPort();
    }


}
