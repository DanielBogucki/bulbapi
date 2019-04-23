package com.dbogucki.bulbapi.sockets;

import com.dbogucki.bulbapi.exceptions.DeviceSocketException;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;


public abstract class SocketHandler {
    protected final static int TIMEOUT = 1500;
    protected String ipAddress;
    protected int port;
    protected Socket socket;
    protected BufferedReader reader;
    protected BufferedWriter writer;

    public SocketHandler(String ip, int port) throws DeviceSocketException {
        this.ipAddress = ip;
        this.port = port;
        this.initSocket();

    }

    protected void initSocket() throws DeviceSocketException {
        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(ipAddress, port);
            this.socket = new Socket();
            this.socket.connect(inetSocketAddress, TIMEOUT);
            this.socket.setSoTimeout(TIMEOUT);
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        } catch (Exception e) {
            throw new DeviceSocketException(e);
        }
    }

    public void send(String data) throws DeviceSocketException {
        try {
            this.writer.write(data);
            this.writer.flush();

        } catch (Exception e) {
            throw new DeviceSocketException(e);
        }
    }

    public String readLine() throws DeviceSocketException {
        try {
            String data = this.reader.readLine();
            return data;

        } catch (Exception e) {
            throw new DeviceSocketException(e);
        }
    }

    public String getIp() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }

}
