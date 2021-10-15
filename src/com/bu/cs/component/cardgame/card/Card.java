package com.bu.cs.component.cardgame.card;


/**
 * Class for individual card
 */
public class Card implements Comparable<Card>{

    private Suit suit;
    private CardValue cardValue;
    private boolean isFaceDown;

    /**
     * Constructor for creating a card with specific suit and value
     * @param suit
     * @param value
     */
    public Card(Suit suit, CardValue value) {
        this.suit = suit;
        this.cardValue = value;
        isFaceDown = true;
    }

    /**
     * Constructor for creating a card with specific suit, card value and face down value
     * @param suit
     * @param value
     * @param isFaceDown
     */
    public Card(Suit suit, CardValue value,boolean isFaceDown) {
        this.suit = suit;
        this.cardValue = value;
        this.isFaceDown = isFaceDown;
    }

    /**
     * Check if one card is equal to another
     * @param card
     * @return
     */
    public boolean equals(Card card){
        return (this.getCardValue() == card.getCardValue() && this.getSuit() == card.getSuit());
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

	@Override
	public int compareTo(Card o) {
		return this.cardValue.getValue() - o.getCardValue().getValue();
	}


}
