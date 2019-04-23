package com.dbogucki.bulbapi.sockets;

import com.dbogucki.bulbapi.exceptions.DeviceSocketException;

public class YeelightSocketHandler extends SocketHandler{
    public YeelightSocketHandler(String ip, int port) throws DeviceSocketException {
        super(ip, port);
    }
}
