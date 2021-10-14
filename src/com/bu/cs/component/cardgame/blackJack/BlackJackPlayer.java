package com.bu.cs.component.cardgame.blackJack;

import com.bu.cs.component.cardgame.CardPlayer;
import com.bu.cs.component.cardgame.card.Hand;

import java.util.List;


public class BlackJackPlayer extends CardPlayer {

	private boolean isStand;
	private boolean isBust;
	private boolean isNaturalBlackJack;
	
	public void split() {
		List<Hand> player_hand = this.getHands();
		if(player_hand.get(0).getCards().get(0).equals(player_hand.get(0).getCards().get(1))) {
			new Hand(player_hand.get(0).getCards().get(1),player_hand.get(0).getBet());
		}		
	}
	
	public void doubleUp(int handIndex) {
		int betvalue = this.getHands().get(handIndex).getBet(); //assuming double up is not allowed after a split
		this.getHands().get(handIndex).setBet(betvalue * 2);
		isStand = true;
	}

	public boolean isStand() {
		return isStand;
	}

	public void setStand(boolean stand) {
		isStand = stand;
	}

	public boolean isBust() {
		return isBust;
	}

	public void setBust(boolean bust) {
		isBust = bust;
	}

	public boolean isNaturalBlackJack() {
		return isNaturalBlackJack;
	}

	public void setNaturalBlackJack(boolean naturalBlackJack) {
		isNaturalBlackJack = naturalBlackJack;
	}

	@Override
	public void resetPlayer() {
		isStand = false;
		isBust = false;
		isNaturalBlackJack = false;
		resetHand();
	}
}
