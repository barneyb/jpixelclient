package com.barneyb.jpixelclient.raw;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;

public class Scene {

    @JsonProperty("b")
    private Frame base;
    @JsonProperty("f")
    private List<Frame> frames;

    public Scene() {
        this.frames = new LinkedList<>();
    }

    public Scene(Frame base) {
        this();
        setBase(base);
    }

    public Frame getBase() {
        return base;
    }

    public void setBase(Frame base) {
        this.base = base;
    }

    public int getFrameCount() {
        return frames.size();
    }

    public void addFrame(Frame f) {
        frames.add(f);
    }

}
