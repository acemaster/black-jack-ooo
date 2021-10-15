package com.bu.cs.component.cardgame.blackJack;

import com.bu.cs.component.cardgame.CardPlayer;
import com.bu.cs.component.cardgame.card.Card;
import com.bu.cs.component.cardgame.card.CardValue;
import com.bu.cs.component.cardgame.card.Decks;
import com.bu.cs.component.cardgame.card.Hand;

import java.util.ArrayList;
import java.util.List;


public class BlackJackPlayer extends CardPlayer {

	public void split(int handIndex) {
		Hand currentHand = this.getHands().get(handIndex);
		if(currentHand.getCards().get(0).equals(currentHand.getCards().get(1))) {
			Hand newHand = new Hand(currentHand.getCards().get(1),currentHand.getBet());
			currentHand.removeCard(currentHand.getCards().get(1));
			this.addHand(newHand);
		}
	}
	
	public void doubleUp(int handIndex, Decks decks) {
		int betvalue = this.getHands().get(handIndex).getBet(); //assuming double up is not allowed after a split
		this.getHands().get(handIndex).setBet(betvalue * 2);
		this.hit(decks, handIndex, false);
		this.getHands().get(handIndex).setStand(true);
	}

	public  boolean isNaturalBlackJack(int handIndex, int winCondition){
		Hand hand = this.getHands().get(handIndex);
		List<CardValue> cardValues = new ArrayList<>();
		if(hand.currentHand() == winCondition && hand.getCards().size() == 2) {
			for(Card card: hand.getCards()) {
				cardValues.add(card.getCardValue());
			}
			if(cardValues.contains(CardValue.ACE) && (cardValues.contains(CardValue.KING) || cardValues.contains(CardValue.QUEEN) || cardValues.contains(CardValue.JACK))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void resetPlayer() {
		resetHand();
	}
}
