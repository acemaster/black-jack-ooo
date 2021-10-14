package com.bu.cs.component.cardgame.card;

public enum CardValue {

    ACE(1,11),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(10),
    QUEEN(10),
    KING(10);

    private int value;
    private int altValue;

    CardValue(int value) {
        this.value = value;
        this.altValue = 0;
    }

    CardValue(int value,int altValue) {
        this.value = value;
        this.altValue = altValue;
    }

    public int getValue() {
        return value;
    }

    public int getAltValue() {
        return altValue;
    }

}
