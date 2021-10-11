package com.bu.cs.component;

public abstract class Player {

    protected int playerId;
    protected String name;
    protected int wins;
    protected boolean isHuman;

    /**
     * Create new player win 0 wins
     */
    public Player() {
        wins = 0;
    }

    /**
     * Create new player with unique playerId
     * @param playerId
     */
    public Player(int playerId) {
        this.playerId = playerId;
        wins = 0;
    }

    /**
     * Get Name of the player
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set Name of the player
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Increment Wins
     */
    public void incrementWins() {
        wins++;
    }

    /**
     * Get wins of the player
     * @return wins
     */
    public int getWins() {
        return wins;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public boolean isHuman() {
        return isHuman;
    }

    public void setHuman(boolean human) {
        isHuman = human;
    }
}
