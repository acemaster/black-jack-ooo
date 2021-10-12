package com.bu.cs.component.cardgame.blackJack;

import com.bu.cs.component.Player;
import com.bu.cs.component.cardgame.CardGame;
import com.bu.cs.component.cardgame.exception.NoDeckException;

public class BlackJackGame extends CardGame {
    @Override
    public String getName() {
        return "Black Jack";
    }

    @Override
    public void summary() {

    }

    @Override
    public boolean isGameComplete(int playerIndex) {
        return false;
    }

    @Override
    public void startGame() {
        try {
            cardGameConfig.setNumberOfDecks(1);
            initializeDeck();
            cardPlayers.add(new BlackJackPlayer());
            cardPlayers.get(0).addCard(decks.getRandomCard());
            cardPlayers.get(0).addCard(decks.getRandomCard());
            cardPlayers.get(0).addCard(decks.getRandomCard());
            cardPlayers.get(0).addMoney(10);
            cardPlayers.get(0).summary();
        } catch (NoDeckException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Player[] getPlayers() {
        return new Player[0];
    }
}
