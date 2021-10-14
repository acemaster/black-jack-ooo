package com.bu.cs.component.cardgame.trianta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.bu.cs.component.Player;
import com.bu.cs.component.cardgame.CardGame;
import com.bu.cs.component.cardgame.Dealer;
import com.bu.cs.component.cardgame.blackJack.BlackJackPlayer;
import com.bu.cs.component.cardgame.blackJack.BlackjackDealer;
import com.bu.cs.component.cardgame.card.Hand;
import com.bu.cs.component.cardgame.card.Card;
import com.bu.cs.component.cardgame.card.CardValue;
import com.bu.cs.component.cardgame.card.Decks;
import com.bu.cs.helper.GameFunctions;

public class TriantaGame extends CardGame {
	private List<TriantaPlayer> triantaplayers;
	private TriantaDealer gameDealer = new TriantaDealer ();
	private int dealerIndex;
    @Override
    public String getName() {
        return "Trianta Enta";
    }

    @Override
    public void summary() {
    	//to be added in cardgame class
    } 
    
    //check
	@Override
	public boolean isGameComplete(int playerIndex) {
		// TODO Auto-generated method stub	
		int handIndex = 0;
		if((gameDealer.getHands().get(0).isWon() == true))//if dealer gets a natural Trianta Ena, he win no matter what
			triantaplayers.get(playerIndex).getHands().get(handIndex).setWon(false);	//Player loses even if he has a natural Trianta Ena
		else if(gameDealer.isBust()) {
			if(!triantaplayers.get(playerIndex).isBust()) {
				triantaplayers.get(playerIndex).getHands().get(handIndex).setWon(true);				
			}			
		}
		else {
			 if(triantaplayers.get(playerIndex).getHands().get(handIndex).currentHand() > gameDealer.getHands().get(0).currentHand()) {
				triantaplayers.get(playerIndex).getHands().get(handIndex).setWon(true);
			}
		}
		return triantaplayers.get(playerIndex).getHands().get(handIndex).isWon();
	}
	
	//Method to check for a natural Trianta Ena
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
    	if(triantaplayers.get(playerIndex).getHands().get(0).currentHand() > this.cardGameConfig.getWinCondition()) {
    		triantaplayers.get(playerIndex).setBust(true);
    		System.out.println("Busted!! Try your luck in the next round");
    	}
    }
    
    

    @Override
    public void startGame() {
    	try {
	    	System.out.println("Welcome to the Trianta");
	    	Scanner scanner = new Scanner(System.in);
	    	initializeGame(scanner); 
	        int playerMoney = GameFunctions.safeScanInt(scanner,"Enter the amount each player would like to start with ");
	        System.out.println("Dealer starts with "+playerMoney*2+" amount");
	        System.out.println("Please select a dealer from the list of players: ");
            for (int i = 0; i < triantaplayers.size(); i++) {
                System.out.printf("%d. %s %n",i + 1,triantaplayers.get(i).getName());                
            }
            dealerIndex = GameFunctions.safeScanInt(scanner,"Enter option: ")-1;
            gameDealer = (TriantaDealer)cardPlayers.get(dealerIndex);
	        //Each player gets a card including the dealer 
	        for(TriantaPlayer currPlayer:triantaplayers) {
	        	currPlayer.addCard(gameDealer.dealPlayer(decks, false));
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
	        		currPlayer.addCard(gameDealer.dealPlayer(decks, false));
	        		currPlayer.addCard(gameDealer.dealPlayer(decks, false));
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
		        		currPlayer.hit(decks, false);
		        		isPlayerBust(triantaplayers.indexOf(currPlayer));	
		        	}
		        	else
		        		currPlayer.setStand(true);
    			}//while
	        }//for
	        //after players are done, dealer plays until his mincount and check for a natural Trianta Ena
	        gameDealer.addCard(gameDealer.dealPlayer(decks, false));
	        gameDealer.addCard(gameDealer.dealPlayer(decks, false));
	        System.out.println("Dealer's hand");
	        gameDealer.getHands().get(0).display();
	        if(isTrianta(dealerIndex) == true) {
	        	gameDealer.getHands().get(0).setWon(true);
	        }
	        while(gameDealer.getHands().get(0).currentHand() < gameDealer.getMinvalue() && gameDealer.isBust() == false && gameDealer.getHands().get(0).isWon() == false) {
	        	gameDealer.addCard(gameDealer.dealPlayer(decks, false));
	        	gameDealer.getHands().get(0).display();
	        	isPlayerBust(dealerIndex);
	        }//while
	        //check if player won/lose, dealer wins if it is a draw
	        for(TriantaPlayer currPlayer:triantaplayers) {
	        	boolean isPlayerwin = isGameComplete(triantaplayers.indexOf(currPlayer));	        		
	        }
	        settleRound();
	        
    	}
    	catch(Exception e) {
    		
    	}
    }
    
    private void initializeGame(Scanner scanner) {
        cardPlayers = new ArrayList<>();
        this.cardGameConfig.setPlayerCount(GameFunctions.safeScanInt(scanner,"Please enter the number of players: "));
        this.cardGameConfig.setNumberOfDecks(2);
        this.cardGameConfig.setWinCondition(31);
        initializeDeck();
        for(int i=0;i<this.cardGameConfig.getPlayerCount();i++){
        	TriantaPlayer player = new TriantaPlayer();
            player.setName(GameFunctions.safeScanString(scanner,"Please enter the name for player " + (i+1) + ": "));
            player.setPlayerId((i));
            cardPlayers.add(player);
            triantaplayers.add(player);            
        }
    }

    @Override
    public Player[] getPlayers() {
        return new Player[0];
    }

	@Override
	public void settleRound() {
		// settle the bet checking if the player won or lost
		for(TriantaPlayer currPlayer:triantaplayers) {
			if(currPlayer.getHands().get(0).isWon() == true && currPlayer.isDealer() != true) {
				currPlayer.addMoney(currPlayer.getHands().get(0).getBet());
				gameDealer.removeMoney(currPlayer.getHands().get(0).getBet());
			}
			else {
				currPlayer.removeMoney(currPlayer.getHands().get(0).getBet());
				gameDealer.addMoney(currPlayer.getHands().get(0).getBet());
			}
		}
		List<Integer> playerMoney = null;
		for(TriantaPlayer currPlayer:triantaplayers) {
			playerMoney.add(currPlayer.getMoney());
		}
		Scanner scanner = new Scanner(System.in);
		boolean dealerSet = false;
		while(playerMoney.size()!=0 || dealerSet == false) {
			if(triantaplayers.get(playerMoney.get(Collections.max(playerMoney))).isDealer() == true)
				dealerSet = true;
			else {
				String playerAnswer = GameFunctions.safeScanString(scanner,triantaplayers.get(playerMoney.get(Collections.max(playerMoney))).getName()+", Would you like to be the dealer?Y/N");
				if(playerAnswer.equalsIgnoreCase("y")) {
					triantaplayers.get(playerMoney.get(Collections.max(playerMoney))).setDealer(true);
					dealerSet = true;
					triantaplayers.get(dealerIndex).setDealer(false);
				}
				else {
					playerMoney.remove(playerMoney.get(Collections.max(playerMoney)));
				}
			}
		}
	}
	
	public boolean endGame() {
		return false;
	}

}
