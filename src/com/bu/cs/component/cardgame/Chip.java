package com.bu.cs.component.cardgame;

/**
 * Class to store chip (money data)
 */
public class Chip {

    private int value;

    public Chip(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
