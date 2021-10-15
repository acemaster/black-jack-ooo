package com.bu.cs.component.cardgame.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class for handling deck of cards
 */
public class Deck {

    private List<Card> cards;
    private Random randomGenerator;

    /**
     * Constructor for creating the deck
     */
    public Deck() {
        randomGenerator = new Random();
        cards = new ArrayList<>();
        for(Suit suit:Suit.values()) {
            for(CardValue cardValue:CardValue.values()) {
                Card card = new Card(suit,cardValue);
                cards.add(card);
            }
        }
    }

    /**
     * Constructor for getting the list of cards
     * @return
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * Constructor for getting the remaining
     * @return
     */
    public int getRemainingCards() {
        return cards.size();
    }

    /**
     * Get the random card
     * @return
     */
    public Card getRandomCard() {
        int index = randomGenerator.nextInt(cards.size());
        Card card = cards.get(index);
        cards.remove(index);
        return card;
    }
}
