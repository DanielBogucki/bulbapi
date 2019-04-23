package com.dbogucki.bulbapi.discovering;

import com.dbogucki.bulbapi.devices.Bulb;
import com.dbogucki.bulbapi.devices.YeelightBulb;
import com.dbogucki.bulbapi.exceptions.DeviceSocketException;

import java.io.IOException;
import java.net.*;
import java.util.HashSet;
import java.util.Set;

public class YeelightDiscoverer implements Discoverer {

    private final static String NEWLINE = "\r\n";
    private final static String SEARCH_REQUEST_ADDRESS = "239.255.255.250";
    private final static int SEARCH_REQUEST_PORT = 1982;
    private final static int RESPONSE_TIMEOUT = 1000;


    @Override
    public Set<Bulb> search() throws IOException, DeviceSocketException {
        final String request = "M-SEARCH * HTTP/1.1" + NEWLINE +
                "MAN: \"ssdp:discover\"" + NEWLINE +
                "ST: wifi_bulb" + NEWLINE;
        final byte[] requestBytes = request.getBytes();

        Set<Bulb> devices = new HashSet<>();

        InetSocketAddress address = new InetSocketAddress(SEARCH_REQUEST_ADDRESS, SEARCH_REQUEST_PORT);
        DatagramPacket requestPacket = new DatagramPacket(requestBytes, requestBytes.length, address);
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(RESPONSE_TIMEOUT);
        socket.send(requestPacket);
        try {
            while (true) {
                byte[] responseBuffer = new byte[1500];
                DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
                socket.receive(responsePacket);
                Bulb device = processResponse(new String(responseBuffer, 0, responsePacket.getLength()));
                devices.add(device);
            }
        } catch (SocketTimeoutException e) {
            // signifies that all responses have been processed
        }
        return devices;
    }

    private static Bulb processResponse(String response) throws DeviceSocketException {
        // TODO response validation
        String ip = null;
        int port = 0;
        String id;
        String model;
        String firmware;
        String[] capabilities;

        String[] lines = response.split(NEWLINE);
        for (String line : lines) {
            if (line.startsWith("Location:")) {
                final int prefixLength = "Location: yeelight://".length();
                String[] ipPortPair = line.substring(prefixLength).split(":");
                ip = ipPortPair[0];
                port = Integer.parseInt(ipPortPair[1]);
                continue;
            }
            if (line.startsWith("id:")) {
                final int prefixLength = "id: ".length();
                id = line.substring(prefixLength);
                continue;
            }
            if (line.startsWith("model:")) {
                final int prefixLength = "model: ".length();
                model = line.substring(prefixLength);
                continue;
            }
            if (line.startsWith("fw_ver:")) {
                final int prefixLength = "fw_ver: ".length();
                firmware = line.substring(prefixLength);
                continue;
            }
            if (line.startsWith("support:")) {
                final int prefixLength = "support: ".length();
                capabilities = line.substring(prefixLength).split(" ");
                continue;
            }
        }

        Bulb bulb = new YeelightBulb(ip, port);

        return bulb;
    }
}
