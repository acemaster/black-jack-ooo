package com.bu.cs.component.cardgame.trianta;

import com.bu.cs.component.cardgame.CardGameConfig;
import com.bu.cs.component.cardgame.Dealer;

public class TriantaDealer extends Dealer{
	private int minvalue = 27;
	private boolean isBust = false;

	/**
	 * Initialize card player with a new hand and card game configuration
	 *
	 * @param cardGameConfig
	 */
	public TriantaDealer(CardGameConfig cardGameConfig) {
		super(cardGameConfig);
	}

	/**
	 * Get the min value
	 * @return
	 */
	public int getMinvalue() {
		return minvalue;
	}

	/**
	 * Check if bust
	 * @return
	 */
	public boolean isBust() {
		return isBust;
	}

	/**
	 * Set the bust value
	 * @param isBust
	 */
	public void setBust(boolean isBust) {
		this.isBust = isBust;
	}

	@Override
	public void summary() {
		System.out.println("Dealer Hand: ");
		this.getHands().get(0).display();
		System.out.printf("Dealer: Current Hand: %d %n",getHands().get(0).currentHand());
	}
}
