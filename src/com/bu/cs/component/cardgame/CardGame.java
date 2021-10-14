package com.bu.cs.component.cardgame;

import com.bu.cs.component.Game;
import com.bu.cs.component.cardgame.card.Decks;

import java.util.ArrayList;
import java.util.List;

public abstract class CardGame implements Game {

    protected List<CardPlayer> cardPlayers;
    protected CardGameConfig cardGameConfig;
    protected Decks decks;

    protected CardGame() {
        cardGameConfig = new CardGameConfig();
        cardPlayers = new ArrayList<>();
        decks = new Decks(1);
    }
    protected CardGame(CardGameConfig cardGameConfig) {
        this();
        this.cardGameConfig = cardGameConfig;
        initializeDeck();
    }

    public void initializeDeck() {
        decks = new Decks(cardGameConfig.getNumberOfDecks());
    }

    public List<CardPlayer> getCardPlayers() {
        return cardPlayers;
    }

    public void setCardPlayers(List<CardPlayer> cardPlayers) {
        this.cardPlayers = cardPlayers;
    }

    public CardGameConfig getCardGameConfig() {
        return cardGameConfig;
    }

    public void setCardGameConfig(CardGameConfig cardGameConfig) {
        this.cardGameConfig = cardGameConfig;
    }
    
    public abstract void settleRound();
    	
}
