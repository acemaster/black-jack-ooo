package com.bu.cs.component.cardgame;

import com.bu.cs.helper.GameConstants;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private List<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
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
}
