package com.bu.cs.component.cardgame;

import com.bu.cs.component.Player;
import com.bu.cs.component.cardgame.card.Card;
import com.bu.cs.component.cardgame.card.Hand;

import java.util.ArrayList;
import java.util.List;

public abstract class CardPlayer extends Player {

    private List<Hand> hands;
    private int money;

    public CardPlayer() {
        hands = new ArrayList<>();
        hands.add(new Hand());
        money = 0;
    }


    public List<Hand> getHands() {
        return hands;
    }

    public void setHands(List<Hand> hands) {
        this.hands = hands;
    }

    public void addCard(Card card, int handIndex) {
        hands.get(handIndex).addCard(card);
    }

    public void addCard(Card card) {
        hands.get(0).addCard(card);
    }

    public void removeCard(Card card,int handIndex) {
        hands.get(handIndex).removeCard(card);
    }

    public void removeCard(Card card) {
        hands.get(0).removeCard(card);
    }

    public void summary() {
        if(hands.size() == 1) {
            hands.get(0).display();
        }
        else {
            for(int i=0;i<hands.size();i++) {
                System.out.println("Hand "+(i+1));
                hands.get(i).display();
            }
        }
        System.out.println("Total money: "+money);
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void addMoney(int value) {
        this.money = this.money + value;
    }

    public void removeMoney(int value) {
        this.money = this.money - value;
    }
}