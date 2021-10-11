package com.bu.cs.component.cardgame;

/**
 * Class for individual card
 */
public class Card {

    private Suit suit;
    private CardValue cardValue;
    private boolean isFaceDown;

    public Card(Suit suit, CardValue value) {
        this.suit = suit;
        this.cardValue = value;
        isFaceDown = true;
    }

    public Card(Suit suit, CardValue value,boolean isFaceDown) {
        this.suit = suit;
        this.cardValue = value;
        this.isFaceDown = isFaceDown;
    }


    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public CardValue getCardValue() {
        return cardValue;
    }

    public void setCardValue(CardValue cardValue) {
        this.cardValue = cardValue;
    }

    public boolean isFaceDown() {
        return isFaceDown;
    }

    public void setFaceDown(boolean faceDown) {
        isFaceDown = faceDown;
    }
}
