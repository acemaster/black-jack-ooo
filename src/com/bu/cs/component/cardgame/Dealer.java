package com.bu.cs.component.cardgame;

import com.bu.cs.component.cardgame.card.Card;
import com.bu.cs.component.cardgame.card.Decks;

public class Dealer extends CardPlayer{

    public void initialize(Decks decks) {
        getHands().get(0).addCard(decks.getRandomCard(false));
        getHands().get(0).addCard(decks.getRandomCard(true));
    }

    public void revealCards() {
        for(Card card: getHands().get(0).getCards()) {
            card.setFaceDown(false);
        }
    }

    public Card dealPlayer(Decks decks, boolean isFaceDown) {
    	return decks.getRandomCard(isFaceDown);
    }

    @Override
    public void resetPlayer() {
        resetHand();
    }
}
