package com.barneyb.jpixelclient.raw;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Element {

    @JsonProperty("c")
    private final Command command;

    @JsonProperty("r")
    private final Region region;

    public Element(Command command) {
        this.command = command;
        this.region = null;
    }

    public Element(Region region) {
        this.command = null;
        this.region = region;
    }
}
