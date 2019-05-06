package com.dbogucki.bulbapi.utils;

public class ValueCalc {

    public static int calc(int value, int min, int max) {
        if (value > max) value = max;
        if (value < min) value = min;
        return value;
    }

    public static int calcRGB(int red, int green, int blue) {
        red = calc(red, 0, 255);
        green = calc(green, 0, 255);
        blue = calc(blue, 0, 255);

        return (red * 65536) + (green * 256) + blue;

    }
}
