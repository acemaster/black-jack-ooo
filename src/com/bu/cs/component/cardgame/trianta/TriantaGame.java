package com.bu.cs.component.cardgame.trianta;

import com.bu.cs.component.Player;
import com.bu.cs.component.cardgame.CardGame;

public class TriantaGame extends CardGame {
    @Override
    public String getName() {
        return null;
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

    }

    @Override
    public Player[] getPlayers() {
        return new Player[0];
    }
}
