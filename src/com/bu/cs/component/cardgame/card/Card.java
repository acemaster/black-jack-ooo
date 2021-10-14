package com.bu.cs.component.cardgame.card;


/**
 * Class for individual card
 */
public class Card implements Comparable<Card>{

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
		// TODO Auto-generated method stub
		if(cardValue.equals(o.getCardValue()))
			return 1;
		else
			return 0;
	}


}
