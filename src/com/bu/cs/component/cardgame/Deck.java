package com.bu.cs.component.cardgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class for handling deck of cards
 */
public class Deck {

    private List<Card> cards;
    private Random randomGenerator;

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

    public List<Card> getCards() {
        return cards;
    }

    public int getRemainingCards() {
        return cards.size();
    }

    public Card getRandomCard() {
        int index = randomGenerator.nextInt(cards.size());
        Card card = cards.get(index);
        cards.remove(index);
        return card;
    }
}
