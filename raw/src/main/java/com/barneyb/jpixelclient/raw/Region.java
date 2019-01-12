package com.barneyb.jpixelclient.raw;

import com.fasterxml.jackson.annotation.JsonValue;

import java.awt.*;

public class Region {

    public static Region circle(int x, int y, int r) {
        return ellipse(x, y, r, r);
    }

    public static Region ellipse(int x, int y, int hr, int vr) {
        return new Region(x, y, hr, vr);
    }

    public static Region rect(int x, int y, int w, int h) {
        return new Region(x, y, x + w, y + h);
    }

    public static Region rect(Rectangle r) {
        return new Region(r.x, r.y, r.x + r.width, r.y + r.height);
    }

    @JsonValue
    private final int[] coords;

    public Region(int x1, int y1, int x2, int y2) {
        coords = new int[] { x1, y1, x2, y2 };
    }

    public int getX1() {
        return coords[0];
    }

    public int getY1() {
        return coords[1];
    }

    public int getX2() {
        return coords[2];
    }

    public int getY2() {
        return coords[3];
    }

}
