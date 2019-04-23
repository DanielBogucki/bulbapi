package com.dbogucki.bulbapi.discovering;

import java.util.ArrayList;
import java.util.List;

public class DiscoveryManager {


    private static DiscoveryManager manager;
    private List<Discoverer> list;

    public synchronized DiscoveryManager getDiscoveryManager() {
        if (manager == null) {
            return new DiscoveryManager();
        } else {
            return manager;
        }
    }

    private DiscoveryManager(){
        list = new ArrayList<Discoverer>();

    }

}
