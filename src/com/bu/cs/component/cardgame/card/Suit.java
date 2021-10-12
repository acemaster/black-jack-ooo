package com.bu.cs.component.cardgame.card;

public enum Suit {
    HEARTS("\u001B[31m"),
    DIAMONDS("\u001B[31m"),
    SPADES("\u001B[0m"),
    CLUBS("\u001B[0m");

    String colorValue;

    Suit(String colorValue) {
        this.colorValue = colorValue;
    }

    public String getColorValue() {
        return  colorValue;
    }
}
