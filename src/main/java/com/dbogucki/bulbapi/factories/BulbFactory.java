package com.dbogucki.bulbapi.factories;

import com.dbogucki.bulbapi.devices.Bulb;
import com.dbogucki.bulbapi.devices.YeelightBulb;
import com.dbogucki.bulbapi.enums.Category;
import com.dbogucki.bulbapi.exceptions.DeviceNotRecognizedException;
import com.dbogucki.bulbapi.exceptions.DeviceSocketException;

public class BulbFactory {
    public static Bulb getBulb(Category bulbType, String ip, int port) throws DeviceSocketException, DeviceNotRecognizedException {
        Bulb bulb = null;

        switch (bulbType) {
            case YEELIGHT:
                if (port == 0) {
                    bulb = new YeelightBulb(ip);
                } else {
                    bulb = new YeelightBulb(ip, port);
                }
                break;
            default:
               throw new DeviceNotRecognizedException(new Exception("Device hasn't been recognized"));
        }


        return bulb;
    }
}
