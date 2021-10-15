package com.bu.cs.component.cardgame;

public class CardGameConfig {

    private int playerCount;
    private int winCondition;
    private int numberOfDecks;


    /**
     * Get the player count
     * @return
     */
    public int getPlayerCount() {
        return playerCount;
    }

    /**
     * Set the player count
     * @param playerCount
     */
    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    /**
     * Get the win condition
     * @return
     */
    public int getWinCondition() {
        return winCondition;
    }

    /**
     * Set the win Condition
     * @param winCondition
     */
    public void setWinCondition(int winCondition) {
        this.winCondition = winCondition;
    }

    /**
     * Get the number of decks
     * @return
     */
    public int getNumberOfDecks() {
        return numberOfDecks;
    }

    /**
     * Set the number of decks
     * @param numberOfDecks
     */
    public void setNumberOfDecks(int numberOfDecks) {
        this.numberOfDecks = numberOfDecks;
    }
}
