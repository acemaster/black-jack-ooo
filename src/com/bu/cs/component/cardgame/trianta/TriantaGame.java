package com.bu.cs.component.cardgame.trianta;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.bu.cs.component.Player;
import com.bu.cs.component.cardgame.CardGame;
import com.bu.cs.component.cardgame.card.Hand;
import com.bu.cs.component.cardgame.card.Card;
import com.bu.cs.component.cardgame.card.CardValue;
import com.bu.cs.component.cardgame.card.Decks;
import com.bu.cs.helper.GameFunctions;

public class TriantaGame extends CardGame {
	private int gameWinTotal = 31;
	private int dealerMinCount = 27;
	private List<TriantaPlayer> triantaplayers;
    @Override
    public String getName() {
        return "Trianta Enta";
    }

    @Override
    public void summary() {
    	//to be added in cardgame class
    } 

	@Override
	public boolean isGameComplete(int playerIndex) {
		// TODO Auto-generated method stub
		return false;
	}
	
    public boolean isTrianta(int playerIndex) {
    	int handIndex = 0;
    	List<CardValue> playerCardValues = null;
    	for(int i=0; i<triantaplayers.get(handIndex).getHands().get(handIndex).getCards().size(); i++) {
    		playerCardValues.add(triantaplayers.get(handIndex).getHands().get(handIndex).getCards().get(i).getCardValue());
    	}
    	int aceOccurences = Collections.frequency(playerCardValues, CardValue.ACE);
    	int kingOccurences = Collections.frequency(playerCardValues, CardValue.KING);
    	int queenOccurences = Collections.frequency(playerCardValues, CardValue.QUEEN);
    	int jackOccurences = Collections.frequency(playerCardValues, CardValue.JACK);
    	if(aceOccurences == 1 && kingOccurences+queenOccurences+jackOccurences == 2)
    		triantaplayers.get(playerIndex).getHands().get(handIndex).setWon(true);
        return triantaplayers.get(playerIndex).getHands().get(handIndex).isWon();
    }
    
    public void isPlayerBust(int playerIndex) {
    	if(triantaplayers.get(playerIndex).getHands().get(0).currentHand() > gameWinTotal) {
    		triantaplayers.get(playerIndex).setBust(true);
    		System.out.println("Busted!! Try your luck in the next round");
    	}
    }

    @Override
    public void startGame() {
    	try {
	    	System.out.println("Welcome to the Trianta");
	    	System.out.println("How many players would like to play");
	    	Scanner scanner = new Scanner(System.in);
	        int numOfPlayers = GameFunctions.safeScanInt(scanner,"Enter the number: ");
	        //code to read player inputs to be added
	        
	        
	        int playerMoney = GameFunctions.safeScanInt(scanner,"Enter the amount each player would like to start with ");
	        System.out.println("Dealer starts with "+playerMoney*2+" amount");
	        System.out.println("Please select a dealer from the list of players: ");
            for (int i = 0; i < triantaplayers.size(); i++) {
                System.out.printf("%d. %s %n",i + 1,triantaplayers.get(i).getName());                
            }
            int dealerIndex = GameFunctions.safeScanInt(scanner,"Enter option: ")-1;
            TriantaPlayer gameDealer = triantaplayers.get(dealerIndex);
	        Decks playingdeck  = new Decks(2);
	        //Each player gets a card including the dealer
	        for(TriantaPlayer currPlayer:triantaplayers) {
	        	currPlayer.addCard(playingdeck.getRandomCard());
	        }
	        //Display the dealer and players hand to let him choose either fold or bet
	        for(TriantaPlayer currPlayer:triantaplayers) {
	        	if(currPlayer.isDealer() == false) {
	        		System.out.println("Dealer's hand");
	        		gameDealer.getHands().get(0).display();
	        		System.out.println(currPlayer.getName()+", your hand");
		        	currPlayer.getHands().get(0).display();
		        	System.out.println(currPlayer.getName()+", Choose the action you would like to take\n1.Fold\n2.Bet");
		        	int playerOption = GameFunctions.safeScanInt(scanner,"Enter option: ");
		        	if(playerOption == 2) {
		        		int playerBet = GameFunctions.safeScanInt(scanner,"Enter amount you would like to bet: ");
		        		currPlayer.getHands().get(0).setBet(playerBet);
		        	}
		        	else
		        		currPlayer.setIsfold(true);
	        	}
	        }
	        //Once the players bet deals two more cards and check if its a natural Trianta Ena and wait for dealers hand
	        for(TriantaPlayer currPlayer:triantaplayers) {
	        	if(currPlayer.isfold() == false && currPlayer.isDealer() == false) {
	        		System.out.println(currPlayer.getName()+", here are your cards");
	        		currPlayer.addCard(playingdeck.getRandomCard());
	        		currPlayer.addCard(playingdeck.getRandomCard());
	        		currPlayer.getHands().get(0).display();
	        		if(isTrianta(triantaplayers.indexOf(currPlayer))) {
	        			currPlayer.getHands().get(0).setWon(true);
	        			System.out.println(currPlayer.getName()+", you have a great hand. Let's see what the dealer has.");
	        		}	        			
		        	else
		        		isPlayerBust(triantaplayers.indexOf(currPlayer));		        		
	        		}
	        	}
	        //Each player can either chose hit till bust or stand
	        for(TriantaPlayer currPlayer:triantaplayers) {
	        	while((currPlayer.isBust() == false || currPlayer.isStand() == false) && currPlayer.isfold() == false && currPlayer.getHands().get(0).isWon() == false && currPlayer.isDealer() == false) {
	        		currPlayer.getHands().get(0).display();
	        		System.out.println("Choose the action you would like to take\n1.Hit\n2.Stand");
		        	int playerOption = GameFunctions.safeScanInt(scanner,"Enter option: ");	
		        	if(playerOption == 1) {
		        		currPlayer.addCard(playingdeck.getRandomCard());
		        		if(isTrianta(triantaplayers.indexOf(currPlayer)))
		        			currPlayer.getHands().get(0).setWon(true);
			        	else
			        		isPlayerBust(triantaplayers.indexOf(currPlayer));	
		        	}
		        	else
		        		currPlayer.setStand(true);
    			}//while
	        }//for
	        //after players are done, dealer plays until his mincount and check for a natural Trianta Ena
	        gameDealer.addCard(playingdeck.getRandomCard());
	        gameDealer.addCard(playingdeck.getRandomCard());
	        System.out.println("Dealer's hand");
	        gameDealer.getHands().get(0).display();
	        if(isTrianta(dealerIndex) == true) {
	        	gameDealer.getHands().get(0).setWon(true);
	        }
	        while(gameDealer.getHands().get(0).currentHand() < dealerMinCount && gameDealer.isBust() == false && gameDealer.getHands().get(0).isWon() == false) {
	        	gameDealer.addCard(playingdeck.getRandomCard());
	        	gameDealer.getHands().get(0).display();
	        	isPlayerBust(dealerIndex);
		        if(gameDealer.isBust() == false && isTrianta(dealerIndex) == true) {
		        	gameDealer.getHands().get(0).setWon(true);
		        }
	        }//while
	        //if dealer gets a natural Trianta Ena, he win no matter what
	        if(gameDealer.getHands().get(0).isWon() == true) {
	        	for(TriantaPlayer currPlayer:triantaplayers) {
	        		currPlayer.getHands().get(0).setWon(false);
	        	}
	        }
	        //check each player against dealer hand and settle game
	        else
	        	for(TriantaPlayer currPlayer:triantaplayers) {
	        		if(isGameComplete(triantaplayers.indexOf(currPlayer)) == true) continue;
	        		
	        	}
    	}
    	catch(Exception e) {
    		
    	}
    }

    @Override
    public Player[] getPlayers() {
        return new Player[0];
    }

}
