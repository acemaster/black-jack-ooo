package com.bu.cs.component.cardgame.card;

import com.bu.cs.component.cardgame.CardPlayer;
import com.bu.cs.helper.GameConstants;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private List<Card> cards;
    private CardPlayer cardplayer;
    private int bet;//each hand can have a bet
    private boolean won = false;//each hand can win or lose and used to add/deduct money form player

    public Hand() {
        cards = new ArrayList<>();
    }
    
  //used to add new hand and place bet during split
    public Hand(Card new_card, int betvalue) {
        addCard(new_card);
        setBet(betvalue);
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.add(card);
    }

    public int currentHand() {
        int sum = 0;
        for(Card card:cards) {
            sum += card.getCardValue().getValue();
        }
        return sum;
    }

    public void display() {
        //Top of the card
        for (int i=0;i<cards.size();i++) {
            System.out.print("-------------");
            if(i!=cards.size()-1) {
                System.out.print("\t");
            }
            else {
                System.out.print("\n");
            }
        }

        // Body of the card
        for(int j=0;j<5;j++) {
            for (int i=0;i<cards.size();i++) {
                if (j == 1) {
                    System.out.printf("|" + cards.get(i).getSuit().getColorValue() + "%1$8s   " + GameConstants.RESET_COLOR + "|",cards.get(i).getSuit().toString());
                } else if (j == 2) {
                    System.out.printf("|%1$8s   |",cards.get(i).getCardValue().toString());
                } else {
                    System.out.print("|           |");
                }
                if (i != cards.size() - 1) {
                    System.out.print("\t");
                } else {
                    System.out.print("\n");
                }
            }
        }

        // Bottom of the card
        for (int i=0;i<cards.size();i++) {
            System.out.print("-------------");
            if(i!=cards.size()-1) {
                System.out.print("\t");
            }
            else {
                System.out.print("\n");
            }
        }
    }

	public int getBet() {
		return bet;
	}

	public void setBet(int bet) {
		this.bet = bet;
	}

	public void setWon(boolean won) {
		this.won = won;
		if(won == true)
			this.getCardplayer().addMoney(bet);
		else
			this.getCardplayer().removeMoney(bet);
	}

	public boolean isWon() {
		return won;
	}

	public CardPlayer getCardplayer() {
		return cardplayer;
	}

	public void setCardplayer(CardPlayer cardplayer) {
		this.cardplayer = cardplayer;
	}
	
	
}
