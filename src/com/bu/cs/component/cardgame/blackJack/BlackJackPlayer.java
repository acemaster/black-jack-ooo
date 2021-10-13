package com.bu.cs.component.cardgame.blackJack;

import com.bu.cs.component.cardgame.CardPlayer;
import com.bu.cs.component.cardgame.card.Hand;

import java.util.List;


public class BlackJackPlayer extends CardPlayer {
	
	public void split() {
		List<Hand> player_hand = this.getHands();
		if(player_hand.get(0).getCards().get(0).equals(player_hand.get(0).getCards().get(1))) {
			new Hand(player_hand.get(0).getCards().get(1),player_hand.get(0).getBet());
		}		
	}
	
	public void doubleup() {
		int betvalue = this.getHands().get(0).getBet(); //assuming double up is not allowed after a split
		this.getHands().get(0).setBet(betvalue * 2);
	}
	
}
