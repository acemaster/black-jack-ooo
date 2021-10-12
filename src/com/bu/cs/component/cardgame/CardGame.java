package com.bu.cs.component.cardgame;

import com.bu.cs.component.Game;
import com.bu.cs.component.cardgame.card.Deck;
import com.bu.cs.component.cardgame.exception.NoDeckException;

import java.util.ArrayList;
import java.util.List;

public abstract class CardGame implements Game {

    protected List<CardPlayer> cardPlayers;
    protected CardGameConfig cardGameConfig;
    private List<Deck> decks;

    protected CardGame() {
        cardGameConfig = new CardGameConfig();
        cardPlayers = new ArrayList<>();
        decks = new ArrayList<>();
    }
    protected CardGame(CardGameConfig cardGameConfig) {
        this();
        this.cardGameConfig = cardGameConfig;
        initializeDeck();
    }

    public void initializeDeck() {
        for(int i=0;i<cardGameConfig.getNumberOfDecks();i++) {
            decks.add(new Deck());
        }
    }

    public Deck getRandomDeck() throws NoDeckException {
        if(decks.size() == 1 && decks.get(0).getRemainingCards() > 0) {
            return decks.get(0);
        }
        else {
            //Todo: Change the logic to random
            for(Deck deckItem:decks) {
                if(deckItem.getRemainingCards() > 0) {
                    return deckItem;
                }
            }
        }
        throw new NoDeckException();
    }

    public List<CardPlayer> getCardPlayers() {
        return cardPlayers;
    }

    public void setCardPlayers(List<CardPlayer> cardPlayers) {
        this.cardPlayers = cardPlayers;
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }

    public CardGameConfig getCardGameConfig() {
        return cardGameConfig;
    }

    public void setCardGameConfig(CardGameConfig cardGameConfig) {
        this.cardGameConfig = cardGameConfig;
    }
}
