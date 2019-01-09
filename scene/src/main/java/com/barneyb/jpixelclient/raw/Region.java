package com.barneyb.jpixelclient.raw;

import com.fasterxml.jackson.annotation.JsonValue;

public class Region {

    public static Region circle(int x, int y, int r) {
        return ellipse(x, y, r, r);
    }

    public static Region ellipse(int x, int y, int hr, int vr) {
        return new Region(x, y, hr, vr);
    }

    public static Region rect(int x, int y, int w, int h) {
        return new Region(x, y, w, h);
    }

    @JsonValue
    private final int[] coords;

    public Region(int x, int y, int w, int h) {
        coords = new int[] { x, y, w, h };
    }

    public int getX() {
        return coords[0];
    }

    public int getY() {
        return coords[1];
    }

    public int getW() {
        return coords[2];
    }

    public int getH() {
        return coords[3];
    }

}
