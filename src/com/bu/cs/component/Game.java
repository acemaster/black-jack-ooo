package com.bu.cs.component;

public interface Game {

    public String getName();
    /**
     * Get the summary of the game
     */
    public void summary();

    /**
     * Check if the game is complete for a specific player
     * @param playerIndex
     * @return
     */
    public boolean isGameComplete(int playerIndex);

    /**
     * Start the game
     */
    public void startGame();

    /**
     * Get Players of the game
     */
    public Player[] getPlayers();

}
