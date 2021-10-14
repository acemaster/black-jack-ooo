package com.bu.cs.component.cardgame.trianta;

import com.bu.cs.component.cardgame.Dealer;

public class TriantaDealer extends Dealer{
	private int minvalue = 27;
	private boolean isBust = false;

	public int getMinvalue() {
		return minvalue;
	}

	public boolean isBust() {
		return isBust;
	}

	public void setBust(boolean isBust) {
		this.isBust = isBust;
	}
}
