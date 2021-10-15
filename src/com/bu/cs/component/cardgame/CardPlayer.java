package com.bu.cs.component.cardgame;

import com.bu.cs.component.Player;
import com.bu.cs.component.cardgame.card.Card;
import com.bu.cs.component.cardgame.card.Decks;
import com.bu.cs.component.cardgame.card.Hand;

import java.util.ArrayList;
import java.util.List;

public abstract class CardPlayer extends Player {

    private List<Hand> hands;
    private int money;

    /**
     * Initialize card player with a new hand
     */
    public CardPlayer() {
        hands = new ArrayList<>();
        hands.add(new Hand());
        money = 0;
    }

    /**
     * Get hands
     * @return
     */
    public List<Hand> getHands() {
        return hands;
    }

    /**
     * Set hands
     * @param hands
     */
    public void setHands(List<Hand> hands) {
        this.hands = hands;
    }

    /**
     * Add hand to the existing set
     * @param hand
     */
    public void addHand(Hand hand) {
        this.hands.add(hand);
    }

    /**
     * Add a card to the specific hand
     * @param card
     * @param handIndex
     */
    public void addCard(Card card, int handIndex) {
        hands.get(handIndex).addCard(card);
    }

    /**
     * Add a card to the first hand
     * @param card
     */
    public void addCard(Card card) {
        hands.get(0).addCard(card);
    }

    /**
     * Remove a card from the hand
     * @param card
     * @param handIndex
     */
    public void removeCard(Card card,int handIndex) {
        hands.get(handIndex).removeCard(card);
    }

    /**
     * Remove Card from hand
     * @param card
     */
    public void removeCard(Card card) {
        hands.get(0).removeCard(card);
    }

    /**
     * Summary of the hand based on the show Hand
     * @param showHand
     */
    public void summary(boolean showHand) {
        System.out.println(name + " summary: ");
        if (hands.size() == 1) {
            hands.get(0).display();
            if(showHand) {
                System.out.printf("%s Hand %d current Hand: %d %n", name, 1, hands.get(0).currentHand());
            }
        } else {
            for (int i = 0; i < hands.size(); i++) {
                System.out.println("Hand " + (i + 1));
                hands.get(i).display();
                if(showHand) {
                    System.out.printf("%s Hand %d current Hand: %d %n", name, (i + 1), hands.get(i).currentHand());
                }
            }
        }
    }

    /**
     * Summary of the hand
     */
    public void summary() {
        summary(true);
    }

    /**
     * Check the money of the card player
     * @return
     */
    public int getMoney() {
        return money;
    }

    /**
     * Set the money of the card player
     * @param money
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * Add money to the card player
     * @param value
     */
    public void addMoney(int value) {
        this.money = this.money + value;
    }

    /**
     * Remove money from the card player
     * @param value
     */
    public void removeMoney(int value) {
        this.money = this.money - value;
    }

    /**
     * Hit to get a card to the specific hand
     * @param decks
     * @param handIndex
     * @param facedown
     */
    public void hit(Decks decks,int handIndex, boolean facedown) {
        Card card = decks.getRandomCard();
        System.out.printf("%s has got %s of %ss %n",name,card.getCardValue().toString(),card.getSuit().toString());
        card.setFaceDown(facedown);
        hands.get(handIndex).addCard(card);
    }

    /**
     * Get a random card from the deck for the first hand
     * @param decks
     * @param facedown
     */
    public void hit(Decks decks, boolean facedown) {
        Card card = decks.getRandomCard();
        card.setFaceDown(facedown);
        hands.get(0).addCard(card);
    }

    /**
     * Reset the hands
     */
    public void resetHand() {
    	hands.clear();
        hands.add(new Hand());
    }

    /**
     * Abstract function to reset the player
     */
    public abstract void resetPlayer();
}
