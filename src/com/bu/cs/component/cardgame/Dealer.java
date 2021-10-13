package com.bu.cs.component.cardgame;

import com.bu.cs.component.cardgame.card.Card;
import com.bu.cs.component.cardgame.card.Decks;
import com.bu.cs.component.cardgame.exception.NoDeckException;

import java.util.Arrays;
import java.util.List;

public class Dealer extends CardPlayer{

    public void initialize(Decks decks) throws NoDeckException {
        getHands().get(0).addCard(decks.getRandomCard());
        getHands().get(0).addCard(decks.getRandomCard(true));
    }

    public List<Card> dealPlayers(Decks decks) throws NoDeckException {
        return Arrays.asList(decks.getRandomCard(),decks.getRandomCard());
    }
}
