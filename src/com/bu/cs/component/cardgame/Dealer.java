package com.bu.cs.component.cardgame;

import com.bu.cs.component.cardgame.card.Card;
import com.bu.cs.component.cardgame.card.Decks;

public class Dealer extends CardPlayer{

    /**
     * Initialize the dealer with face up and face down card
     * @param decks
     */
    public void initialize(Decks decks) {
        getHands().get(0).addCard(decks.getRandomCard(false));
        getHands().get(0).addCard(decks.getRandomCard(true));
    }

    /**
     * Reveal cards of the dealer
     */
    public void revealCards() {
        for(Card card: getHands().get(0).getCards()) {
            card.setFaceDown(false);
        }
    }

    /**
     * Get a random card from the dealer
     * @param decks
     * @param isFaceDown
     * @return
     */
    public Card dealPlayer(Decks decks, boolean isFaceDown) {
    	return decks.getRandomCard(isFaceDown);
    }

    /**
     * Reset the dealer's hand
     */
    @Override
    public void resetPlayer() {
        resetHand();
    }
}
