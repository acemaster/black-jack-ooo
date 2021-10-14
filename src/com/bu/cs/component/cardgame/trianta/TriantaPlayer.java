package com.bu.cs.component.cardgame.trianta;

import com.bu.cs.component.cardgame.CardPlayer;
import com.bu.cs.component.cardgame.card.Decks;

public class TriantaPlayer extends CardPlayer {
	private boolean isDealer = false;
	private boolean isfold = false;
	private boolean cashout = false;
	private boolean isBust = false;
	private boolean isStand = false;

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

	public boolean isBust() {
		return isBust;
	}

	public void setBust(boolean isBust) {
		this.isBust = isBust;
	}

	public boolean isStand() {
		return isStand;
	}

	public void setStand(boolean isStand) {
		this.isStand = isStand;
	}
	
	//cashOut can't be reset at the end of a round
	@Override
	public void resetPlayer() {
		setIsfold(false);
		setBust(false);
		setStand(false);
	}
}
