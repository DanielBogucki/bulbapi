package com.dbogucki.bulbapi.discovering;

import com.dbogucki.bulbapi.devices.Bulb;
import com.dbogucki.bulbapi.exceptions.DeviceSocketException;
import com.dbogucki.bulbapi.exceptions.DiscoveringException;

import java.io.IOException;
import java.util.Set;

public interface Discoverer {

    public Set<Bulb> search() throws DiscoveringException, IOException, DeviceSocketException;

}
