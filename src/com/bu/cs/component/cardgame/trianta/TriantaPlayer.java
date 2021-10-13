package com.bu.cs.component.cardgame.trianta;

import com.bu.cs.component.cardgame.CardPlayer;
import com.bu.cs.component.cardgame.card.Decks;

public class TriantaPlayer extends CardPlayer {
	private boolean isBanker = false;
	private boolean fold = false;
	private boolean cashout = false;
	

	public boolean isBanker() {
		return isBanker;
	}

	public void setBanker(boolean isBanker) {
		this.isBanker = isBanker;
	}

	public boolean isfold() {
		return fold;
	}

	public void setIsfold(boolean isfold) {
		this.fold = isfold;
	}

	public boolean isCashout() {
		return cashout;
	}

	public void setCashout(boolean cashout) {
		this.cashout = cashout;
	}
	
}
