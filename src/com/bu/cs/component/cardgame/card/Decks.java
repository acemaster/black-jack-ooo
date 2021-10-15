package com.bu.cs.component.cardgame.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Wrapper class for handling multiple decks of cards
 */
public class Decks {
    private List<Deck> deckList;
    private Random randomGenerator;

    public Decks(int noOfDecks) {
        randomGenerator = new Random();
        deckList = new ArrayList<>();
        for (int i=0;i<noOfDecks;i++) {
            deckList.add(new Deck());
        }
    }

    /**
     * Private function to get random deck
     * @return
     */
    private Deck getRandomDeck(){
        if(deckList.size() == 1 && deckList.get(0).getRemainingCards() > 0) {
            return deckList.get(0);
        }
        else {
            return deckList.get(randomGenerator.nextInt(deckList.size()));
        }
    }

    /**
     * Get the random card
     * @return
     */
    public Card getRandomCard() {
        Deck deck = getRandomDeck();
        Card card = deck.getRandomCard();
        if(deck.getRemainingCards() == 0) {
            deckList.remove(deck);
        }
        return card;
    }

    /**
     * Get a random card with particular face down value
     * @param isFaceDown
     * @return
     */
    public Card getRandomCard(boolean isFaceDown) {
        Deck deck = getRandomDeck();
        Card card = deck.getRandomCard();
        card.setFaceDown(isFaceDown);
        if(deck.getRemainingCards() == 0) {
            deckList.remove(deck);
            deckList.add(new Deck());
        }
        return card;
    }
}
