package com.bu.cs.component.cardgame;

import com.bu.cs.component.Game;
import com.bu.cs.component.cardgame.card.Decks;

import java.util.ArrayList;
import java.util.List;

public abstract class CardGame implements Game {

    protected List<CardPlayer> cardPlayers;
    protected CardGameConfig cardGameConfig;
    protected Decks decks;

    /**
     * Card Game constructor
     */
    protected CardGame() {
        cardGameConfig = new CardGameConfig();
        cardPlayers = new ArrayList<>();
        decks = new Decks(1);
    }

    /**
     * Card game constructor with specific config
     * @param cardGameConfig
     */
    protected CardGame(CardGameConfig cardGameConfig) {
        this();
        this.cardGameConfig = cardGameConfig;
        initializeDeck();
    }

    /**
     * Initialize the deck
     */
    public void initializeDeck() {
        decks = new Decks(cardGameConfig.getNumberOfDecks());
    }

    /**
     * Get the card players
     * @return
     */
    public List<CardPlayer> getCardPlayers() {
        return cardPlayers;
    }

    /**
     * Set the card players
     * @param cardPlayers
     */
    public void setCardPlayers(List<CardPlayer> cardPlayers) {
        this.cardPlayers = cardPlayers;
    }

    /**
     * Get the card game config
     * @return
     */
    public CardGameConfig getCardGameConfig() {
        return cardGameConfig;
    }

    /**
     * Set the card game config
     * @param cardGameConfig
     */
    public void setCardGameConfig(CardGameConfig cardGameConfig) {
        this.cardGameConfig = cardGameConfig;
    }

    /**
     * Settle the round in the card game
     */
    public abstract void settleRound();
    	
}
