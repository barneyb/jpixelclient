package com.barneyb.jpixelclient.raw;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Command {

    public static Command thickness(int w) {
        return new Command("thickness", "" + w);
    }

    public static Command color(float r, float g, float b) {
        return new Command("color", r + "," + g + "," + b);
    }

    public static Command circle() {
        return ellipse();
    }

    public static Command ellipse() {
        return new Command("shape", "circle");
    }

    public static Command rectangle() {
        return new Command("shape", "rectangle");
    }

    @JsonProperty("n")
    private final String name;
    @JsonProperty("v")
    private final String value;

    public Command(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

}
