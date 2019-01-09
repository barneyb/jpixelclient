package com.barneyb.jpixelclient.raw;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;

public class Frame {

    @JsonProperty("e")
    private List<Element> elements;

    public Frame() {
        this.elements = new LinkedList<>();
    }

    public Frame(Element... elements) {
        this();
        for (Element e : elements) {
            addElement(e);
        }
    }

    public Frame(Region r) {
        this(new Element(r));
    }

    public Frame(Command c, Region r) {
        this(new Element(c), new Element(r));
    }

    public int getElementCount() {
        return elements.size();
    }

    public void addElement(Element e) {
        elements.add(e);
    }

}
