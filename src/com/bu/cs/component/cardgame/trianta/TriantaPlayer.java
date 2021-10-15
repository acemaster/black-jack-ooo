package com.bu.cs.component.cardgame.trianta;

import com.bu.cs.component.cardgame.CardPlayer;
import com.bu.cs.component.cardgame.card.Decks;

public class TriantaPlayer extends CardPlayer implements Comparable<TriantaPlayer>{
	private boolean isDealer = false;
	private boolean isfold = false;
	private boolean cashout = false;


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
	
	//cashOut can't be reset at the end of a round
	@Override
	public void resetPlayer() {
		setIsfold(false);
		getHands().get(0).setBust(false);
		getHands().get(0).setStand(false);		
	}

	@Override
	public int compareTo(TriantaPlayer o) {
		// TODO Auto-generated method stub
		return this.getMoney()-o.getMoney();
	}
}
