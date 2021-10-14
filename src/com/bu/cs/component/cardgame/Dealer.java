package com.bu.cs.component.cardgame;

import com.bu.cs.component.cardgame.card.Card;
import com.bu.cs.component.cardgame.card.Decks;

import java.util.Arrays;
import java.util.List;

public class Dealer extends CardPlayer{

    public void initialize(Decks decks) {
        getHands().get(0).addCard(decks.getRandomCard(false));
        getHands().get(0).addCard(decks.getRandomCard(true));
    }

    public Card dealPlayer(Decks decks, boolean isFaceDown) {
    	return decks.getRandomCard(isFaceDown);
    }

    @Override
    public void resetPlayer() {
        resetHand();
    }
}
