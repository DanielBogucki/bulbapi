package com.dbogucki.bulbapi.devices;

import com.dbogucki.bulbapi.commands.Command;
import com.dbogucki.bulbapi.commands.YeelightCommand;
import com.dbogucki.bulbapi.enums.Effect;
import com.dbogucki.bulbapi.enums.ResultType;
import com.dbogucki.bulbapi.enums.YeelightEffect;
import com.dbogucki.bulbapi.exceptions.DeviceSocketException;
import com.dbogucki.bulbapi.exceptions.MethodNotSupportedException;
import com.dbogucki.bulbapi.exceptions.ResultException;
import com.dbogucki.bulbapi.results.Result;
import com.dbogucki.bulbapi.sockets.YeelightSocketHandler;
import com.dbogucki.bulbapi.utils.DataFormatter;
import com.google.gson.JsonSyntaxException;

import java.util.HashMap;
import java.util.Map;

import static com.dbogucki.bulbapi.utils.ValueCalc.calc;
import static com.dbogucki.bulbapi.utils.ValueCalc.calcRGB;

public class YeelightBulb extends Bulb {
    private String id;
    private String model = "unknown";
    private String firmwareVersion = "unknown";
    private String[] supportedMethods;
    private boolean power;
    private int bright;
    private int colorMode; // 1 - RGB mode; 2 - color temperature; 3 - HSV mode
    private int colorTemperature; // valid if color_Mode is 2
    private int RGBvalue; // valid if colorMode is 1
    private int HUEvalue; // valid if colorMode is 3
    private int saturation; //valid if colorMode is 3
    private String name;

    private final YeelightSocketHandler socketHandler;
    private int duration;
    private YeelightEffect effect;


    public YeelightBulb(String ip, int port, String id, String model, String firmwareVersion, String[] support, String name, boolean power, int bright, int colorMode, int colorTemperature, int RGBvalue, int HUEvalue, int saturation) throws DeviceSocketException {
        this(ip, port, id, model, firmwareVersion, support, name);
        this.power = power;
        this.bright = bright;
        this.colorMode = colorMode;
        this.colorTemperature = colorTemperature;
        this.RGBvalue = RGBvalue;
        this.HUEvalue = HUEvalue;
        this.saturation = saturation;
    }

    public YeelightBulb(String ip, int port, String id, String model, String firmwareVersion, String[] support, String name) throws DeviceSocketException {
        this(ip, port);
        this.id = id;
        this.model = model;
        this.firmwareVersion = firmwareVersion;
        this.supportedMethods = support;
        this.name = name;
    }


    public YeelightBulb(String ip) throws DeviceSocketException {
        this(ip, 55443);
    }


    public YeelightBulb(String ip, int port) throws DeviceSocketException {
        this.socketHandler = new YeelightSocketHandler(ip, port);
        this.effect = YeelightEffect.SMOOTH;
        this.duration = 500;
    }

    protected Result readResult(int id) throws ResultException, DeviceSocketException {
        Result result;

        String data;
        do {
            data = this.socketHandler.readLine();
            try {
                result = DataFormatter.generateResultFromJson(data, ResultType.YEELIGHT_OK);
                if (result.getResultData() == null) throw new JsonSyntaxException("Probably error result");
                if (result.getId() == id) return result;
            } catch (JsonSyntaxException e) {
                try {
                    result = DataFormatter.generateResultFromJson(data, ResultType.YEELIGHT_ERROR);
                    if (result.getId() == id) return result;
                } catch (JsonSyntaxException exception) {
                    throw new ResultException(exception);
                }
            }
        } while (true);
    }

    protected Result sendCommand(Command command) throws DeviceSocketException {
        String jsonCommand = command.toJson() + "\r\n";
        this.socketHandler.send(jsonCommand);
        return this.readResult(command.getId());
    }

    protected void checkIfMethodIsSupported(String methodName) throws MethodNotSupportedException {
        boolean methodIsSupported = false;
        if (supportedMethods != null) {
            for (String method : supportedMethods) {
                if (method.equalsIgnoreCase(methodName)) methodIsSupported = true;
            }
        } else methodIsSupported = true;

        if (!methodIsSupported)
            throw new MethodNotSupportedException(new Exception("This device do not support method: " + methodName));
    }


    public Result setPower(boolean power) throws ResultException, DeviceSocketException {
        String powerStr = power ? "on" : "off";
        YeelightCommand command = new YeelightCommand("set_power", powerStr, effect, duration);
        Result result = this.sendCommand(command);
        if (result.checkResult()) this.power = power;
        return result;
    }

    public Result setBrightness(int value) throws ResultException, DeviceSocketException {
        value = calc(value, 1, 100);
        YeelightCommand command = new YeelightCommand("set_bright", value, effect, duration);
        Result result = this.sendCommand(command);
        if (result.checkResult()) this.bright = value;
        return result;
    }

    public Result setColorTemperature(int value) throws ResultException, DeviceSocketException {
        value = calc(value, 1700, 6500);
        YeelightCommand command = new YeelightCommand("set_ct_abx", value, effect, duration);
        Result result = this.sendCommand(command);
        if (result.checkResult()) {
            this.colorTemperature = value;
            this.colorMode = 2;
        }
        return result;
    }

    public Result setRGB(int red, int green, int blue) throws ResultException, DeviceSocketException {
        int value = calcRGB(red, green, blue);
        YeelightCommand command = new YeelightCommand("set_rgb", value, effect, duration);
        Result result = this.sendCommand(command);
        if (result.checkResult()) {
            this.RGBvalue = value;
            this.colorMode = 1;
        }
        return result;
    }

    public Result setHSV(int hue, int sat) throws ResultException, DeviceSocketException {
        hue = calc(hue, 0, 359);
        sat = calc(sat, 0, 100);
        YeelightCommand command = new YeelightCommand("set_hsv", hue, sat, effect, duration);
        Result result = this.sendCommand(command);
        if (result.checkResult()) {
            this.HUEvalue = hue;
            this.saturation = sat;
            this.colorMode = 3;
        }
        return result;
    }

    public Result setName(String name) throws ResultException, DeviceSocketException {
        YeelightCommand command = new YeelightCommand("set_name", name);
        Result result = this.sendCommand(command);
        if (result.checkResult()) this.power = power;
        return result;
    }

    public Result setDefault() throws ResultException, DeviceSocketException {
        YeelightCommand command = new YeelightCommand("set_default");
        Result result = this.sendCommand(command);
        return result;
    }

    public Result toggle() throws ResultException, DeviceSocketException {
        YeelightCommand command = new YeelightCommand("toggle");
        Result result = this.sendCommand(command);
        if (result.checkResult()) this.power = !this.power;
        return result;
    }

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String[] getSupportedMethods() {
        return supportedMethods;
    }

    public boolean getPower() throws DeviceSocketException, ResultException {
        YeelightCommand command = new YeelightCommand("get_prop", "power");
        Result result = this.sendCommand(command);
        if (result.checkResult()) {
            if (result.getResultData()[0].equalsIgnoreCase("on")) power = true;
            else power = false;
        }
        return power;
    }

    public int getBright() throws DeviceSocketException, ResultException {
        YeelightCommand command = new YeelightCommand("get_prop", "bright");
        Result result = this.sendCommand(command);
        if (result.checkResult()) {
            bright = Integer.parseInt(result.getResultData()[0]);
        }
        return bright;
    }

    public String getColorMode() throws DeviceSocketException, ResultException {
        YeelightCommand command = new YeelightCommand("get_prop", "color_mode");
        Result result = this.sendCommand(command);
        String colorModeString = "";
        if (result.checkResult()) {
            colorMode = Integer.parseInt(result.getResultData()[0]);
            switch (colorMode) {
                case 1:
                    colorModeString = "RGB";
                    break;
                case 2:
                    colorModeString = "White";
                    break;
                case 3:
                    colorModeString = "HSV";
                    break;
                default:
                    colorModeString = "Wrong type";
            }
        }
        return colorModeString;
    }

    public int getColorTemperature() throws DeviceSocketException, ResultException {
        YeelightCommand command = new YeelightCommand("get_prop", "ct");
        Result result = this.sendCommand(command);
        if (result.checkResult()) {
            colorTemperature = Integer.parseInt(result.getResultData()[0]);
        }
        return colorTemperature;
    }

    public int getRGBvalue() throws DeviceSocketException, ResultException {
        YeelightCommand command = new YeelightCommand("get_prop", "rgb");
        Result result = this.sendCommand(command);
        if (result.checkResult()) {
            RGBvalue = Integer.parseInt(result.getResultData()[0]);
        }
        return RGBvalue;
    }

    public int getHUEvalue() throws DeviceSocketException, ResultException {
        YeelightCommand command = new YeelightCommand("get_prop", "hue");
        Result result = this.sendCommand(command);
        if (result.checkResult()) {
            HUEvalue = Integer.parseInt(result.getResultData()[0]);
        }
        return HUEvalue;
    }

    public String getName() throws DeviceSocketException, ResultException {
        YeelightCommand command = new YeelightCommand("get_prop", "name");
        Result result = this.sendCommand(command);
        if (result.checkResult()) {
            name = result.getResultData()[0];
        }
        return name;
    }

    public Map<String, String> getProperties(String[] properties) throws DeviceSocketException, ResultException {
        Map map = new HashMap<String, String>();
        YeelightCommand command = new YeelightCommand("get_prop", properties);
        Result result = this.sendCommand(command);
        if (result.checkResult()){
            int i = 0;
            for (String p : properties){
                map.put(p, result.getResultData()[i++]);
            }
            //TODO update local properties
            return map;
        }
        else throw new ResultException(new Exception("Bad result received"));
    }

    public int getDuration() {
        return duration;
    }

    public Effect getEffect() throws DeviceSocketException, ResultException {
        return effect;
    }

    public String getIp() {
        return socketHandler.getIp();
    }

    public int getPort() {
        return socketHandler.getPort();
    }


    @Override
    public boolean equals(Object o) {
        YeelightBulb bulb = (YeelightBulb) o;
        return this.getIp().equals(bulb.getIp());
    }

    @Override
    public int hashCode() {
        return this.getIp().hashCode();
    }
}
