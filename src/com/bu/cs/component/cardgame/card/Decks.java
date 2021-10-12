package com.bu.cs.component.cardgame.card;

import com.bu.cs.component.cardgame.exception.NoDeckException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public Deck getRandomDeck() throws NoDeckException {
        if(deckList.size() == 1 && deckList.get(0).getRemainingCards() > 0) {
            return deckList.get(0);
        }
        else if(deckList.size() > 1) {
            return deckList.get(randomGenerator.nextInt(deckList.size()));
        } else {
            throw new NoDeckException();
        }
    }

    public Card getRandomCard() throws NoDeckException {
        Deck deck = getRandomDeck();
        Card card = deck.getRandomCard();
        if(deck.getRemainingCards() == 0) {
            deckList.remove(deck);
        }
        return card;
    }
}
