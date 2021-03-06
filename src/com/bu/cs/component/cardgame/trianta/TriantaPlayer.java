package com.bu.cs.component.cardgame.trianta;

import com.bu.cs.component.cardgame.CardGameConfig;
import com.bu.cs.component.cardgame.CardPlayer;

/**
 * Trianta player class
 */
public class TriantaPlayer extends CardPlayer implements Comparable<TriantaPlayer>{
	private boolean isDealer = false;
	private boolean isfold = false;
	private boolean cashout = false;

	/**
	 * Initialize card player with a new hand and card game configuration
	 *
	 * @param cardGameConfig
	 */
	public TriantaPlayer(CardGameConfig cardGameConfig) {
		super(cardGameConfig);
	}


	public boolean isDealer() {
		return isDealer;
	}

	public void setDealer(boolean isDealer) {
		this.isDealer = isDealer;
	}

	public boolean isfold() {
		return isfold;
	}

	public void setIsfold(boolean isfold) {
		this.isfold = isfold;
	}

	public boolean isCashout() {
		return cashout;
	}

	public void setCashout(boolean cashout) {
		this.cashout = cashout;
	}

	/**
	 * Reset the player
	 */
	@Override
	public void resetPlayer() {
		setIsfold(false);
		resetHand();
	}

	/**
	 * Compare two trianta players
	 * @param o
	 * @return
	 */
	@Override
	public int compareTo(TriantaPlayer o) {
		return this.getMoney()-o.getMoney();
	}

	@Override
	public void summary() {
		System.out.println(name + " Hand: ");
		this.getHands().get(0).display();
		System.out.printf("%s: Current Hand: %d %n", name, getHands().get(0).currentHand());
	}
}
