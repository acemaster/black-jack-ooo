package com.bu.cs.component.cardgame;

import com.bu.cs.component.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class CardPlayer extends Player {

    private List<Card> cards;
    private List<Chip> chips;

    public CardPlayer() {
        cards = new ArrayList<>();
        chips = new ArrayList<>();
    }


    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<Chip> getChips() {
        return chips;
    }

    public void setChips(List<Chip> chips) {
        this.chips = chips;
    }

    public int currentAmount() {
        int sum = 0;
        for(Chip chip:chips) {
            sum += chip.getValue();
        }
        return sum;
    }

    public int currentHand() {
        int sum = 0;
        for(Card card:cards) {
            sum += card.getCardValue().getValue();
        }
        return sum;
    }


}
