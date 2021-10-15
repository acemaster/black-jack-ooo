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
	private TriantaDealer gameDealer;
	private int dealerIndex;
    @Override
    public String getName() {
        return "Trianta Enta";
    }

    @Override
    public void summary() {//to add properly
    	for(TriantaPlayer currPlayer:triantaplayers) {
    		System.out.println(currPlayer.getName()+" won "+currPlayer.getWins()+" games\nCurrent balance: "+currPlayer.getMoney());
    	}
    } 
    
    //check
	@Override
	public boolean isGameComplete(int playerIndex) {
		// TODO Auto-generated method stub	
		int handIndex = 0;
		if((gameDealer.getHands().get(0).isWon() == true)){//if dealer gets a natural Trianta Ena, he win no matter what
			triantaplayers.get(playerIndex).getHands().get(handIndex).setWon(false);	//Player loses even if he has a natural Trianta Ena
			System.out.println("Dealer has a natural Trianta Ena.\nDealer wins!!!");
		}
		else if (triantaplayers.get(playerIndex).getHands().get(handIndex).isWon()) {
			System.out.println(triantaplayers.get(playerIndex).getName()+" has a natural Trianta Ena.\n"+triantaplayers.get(playerIndex).getName()+" wins!!!");
		}
		else if(gameDealer.isBust()) {
			if(!triantaplayers.get(playerIndex).getHands().get(0).isBust()) {
				triantaplayers.get(playerIndex).getHands().get(handIndex).setWon(true);	
				System.out.println(triantaplayers.get(playerIndex).getName()+" wins");
			}			
		}
		else {
			 if(triantaplayers.get(playerIndex).getHands().get(handIndex).currentHand() >= gameDealer.getHands().get(0).currentHand()) {
				triantaplayers.get(playerIndex).getHands().get(handIndex).setWon(true);
				System.out.println(triantaplayers.get(playerIndex).getName()+" wins");
			}
		}
		return triantaplayers.get(playerIndex).getHands().get(handIndex).isWon();
	}
	
	//Method to check for a natural Trianta Ena
    public boolean isTrianta(int playerIndex) {
    	int handIndex = 0;
    	List<CardValue> playerCardValues = new ArrayList<>();
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
    		triantaplayers.get(playerIndex).getHands().get(0).setBust(true);
    		triantaplayers.get(playerIndex).getHands().get(0).display();
    		System.out.println("Busted!! Try your luck in the next round");
    	}
    }
    
    

    @Override
    public void startGame() {
    	try {
	    	System.out.println("Welcome to the Trianta");
	    	Scanner scanner = new Scanner(System.in);
	    	initializeGame(scanner); 
	        int playerMoney = GameFunctions.safeScanInt(scanner,"Enter the amount each player would like to start with: ");
	        scanner.nextLine();
	        System.out.println("Dealer starts with $"+playerMoney*3);
	        System.out.println("Please select a dealer from the list of players: ");
            for (int i = 0; i < triantaplayers.size(); i++) {
                System.out.printf("%d. %s %n",i + 1,triantaplayers.get(i).getName());                
            }
            dealerIndex = GameFunctions.safeScanInt(scanner,"Enter option: ")-1;
            scanner.nextLine();
            //gameDealer = (TriantaDealer)cardPlayers.get(dealerIndex);
            gameDealer.setName(cardPlayers.get(dealerIndex).getName());
            triantaplayers.get(dealerIndex).setDealer(true);
            gameDealer.addMoney(playerMoney*3);
            for (int i = 0; i < triantaplayers.size()-dealerIndex; i++) {
            	triantaplayers.get(i).addMoney(playerMoney);
            }
	        //Each player gets a card including dealer
            boolean isEndGame = false;
            while(!isEndGame) {
                System.out.println("Let's play!!");
    	        for(TriantaPlayer currPlayer:triantaplayers) {
    	        	if(currPlayer.isDealer() == false)
        	        	currPlayer.addCard(gameDealer.dealPlayer(decks, false));
    	        }
    	        gameDealer.addCard(gameDealer.dealPlayer(decks, false));
    	        
    	        
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
    		        	else {
    		        		currPlayer.setIsfold(true);
    		        		System.out.println(currPlayer.getName()+", chose to fold");
    		        	}
    	        	}
    	        }
    	        //Once the players bet deals two more cards and check if its a natural Trianta Ena and wait for dealers hand
    	        for(TriantaPlayer currPlayer:triantaplayers) {
    	        	if(currPlayer.isfold() == false && currPlayer.isDealer() == false) {
    	        		//System.out.println(currPlayer.getName()+", here are your cards");
    	        		currPlayer.addCard(gameDealer.dealPlayer(decks, false));
    	        		currPlayer.addCard(gameDealer.dealPlayer(decks, false));
    	        		//currPlayer.getHands().get(0).display();
    	        		if(isTrianta(triantaplayers.indexOf(currPlayer))) {
    	        			currPlayer.getHands().get(0).setWon(true);
    	        			currPlayer.getHands().get(0).display();
    	        			System.out.println(currPlayer.getName()+", you have a natural Trianta Ena. Let's see what the dealer has.");
    	        		}	        			
    		        	else
    		        		isPlayerBust(triantaplayers.indexOf(currPlayer));		        		
    	        		}
    	        	}
    	        //Each player can either chose hit till bust or stand
    	        for(TriantaPlayer currPlayer:triantaplayers) {
    	        	while((currPlayer.getHands().get(0).isBust() == false && currPlayer.getHands().get(0).isStand() == false) && currPlayer.isfold() == false && currPlayer.getHands().get(0).isWon() == false && currPlayer.isDealer() == false) {
    	        		currPlayer.getHands().get(0).display();
    	        		System.out.println(currPlayer.getName()+",choose the action you would like to take\n1.Hit\n2.Stand");
    		        	int playerOption = GameFunctions.safeScanInt(scanner,"Enter option: ");	
    		        	if(playerOption == 1) {
    		        		currPlayer.hit(decks, false);
    		        		isPlayerBust(triantaplayers.indexOf(currPlayer));	
    		        	}
    		        	else
    		        		currPlayer.getHands().get(0).setStand(true);
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
    	        	triantaplayers.get(dealerIndex).resetHand();//reset and added later to keep in sync
    	        	gameDealer.addCard(gameDealer.dealPlayer(decks, false));
    	        	gameDealer.getHands().get(0).display();
    	        	triantaplayers.get(dealerIndex).addHand(gameDealer.getHands().get(0));//just to check isbust logic based on player class
    	        	isPlayerBust(dealerIndex);
    	        }//while
    	        //check if player won/lose, dealer wins if it is a draw
    	        for(TriantaPlayer currPlayer:triantaplayers) {
    	        	boolean isPlayerwin = isGameComplete(triantaplayers.indexOf(currPlayer));	        		
    	        }
    	        settleRound();
    	        isEndGame = endGame();
            }//while    
    	}
    	catch(Exception e) {
    		System.out.println(e);
    	}
    }
    
    private void initializeGame(Scanner scanner) {
    	triantaplayers = new ArrayList<>();
        cardPlayers = new ArrayList<>();
        gameDealer = new TriantaDealer ();
        this.cardGameConfig.setPlayerCount(GameFunctions.safeScanInt(scanner,"Please enter the number of players: "));
        scanner.nextLine();
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
			if(currPlayer.isDealer() != true) {
				if(currPlayer.getHands().get(0).isWon() == true) {
					currPlayer.addMoney(currPlayer.getHands().get(0).getBet());
					currPlayer.incrementWins();
					gameDealer.removeMoney(currPlayer.getHands().get(0).getBet());
				}
				else {
					currPlayer.removeMoney(currPlayer.getHands().get(0).getBet());
					gameDealer.addMoney(currPlayer.getHands().get(0).getBet());
				}
			}			
			currPlayer.resetHand();
		}
		gameDealer.resetHand();
		triantaplayers.get(dealerIndex).setMoney(gameDealer.getMoney());//showing dealer wallet as a player
		summary();//display player summary before asking for dealer change
		Collections.sort(triantaplayers);
		Collections.reverse(triantaplayers);//sort in decreasing order of their money

		Scanner scanner = new Scanner(System.in);
		boolean dealerSet = false;
		int playerIndex = 0;
		while(dealerSet == false) {
			if(triantaplayers.get(playerIndex).isDealer() == true)
				dealerSet = true;
			else {
				String playerAnswer = GameFunctions.safeScanString(scanner,triantaplayers.get(playerIndex).getName()+", Would you like to be the dealer?Y/N:");
				if(playerAnswer.equalsIgnoreCase("y")) {
					triantaplayers.get(playerIndex).setDealer(true);
					dealerSet = true;
					triantaplayers.get(dealerIndex).setDealer(false);
					dealerIndex = playerIndex;
					gameDealer.setName(cardPlayers.get(dealerIndex).getName());
				}
				else
					playerIndex++;
			}
		}//while
	}
	
	public boolean endGame() {
		for(TriantaPlayer currPlayer:triantaplayers) {
			if(currPlayer.getMoney()<=0) {
				System.out.println(currPlayer.getName()+", you are out of money. Thankyou for playng");
				currPlayer.setCashout(true);
			}
			else {
				Scanner scanner = new Scanner(System.in);
				String playerAnswer = GameFunctions.safeScanString(scanner,currPlayer.getName()+", would you like to cashout?Y/N:");
				if(playerAnswer.equalsIgnoreCase("y")) {
					currPlayer.setCashout(true);
				}
			}
		}
		int remainingPlayers = this.cardGameConfig.getPlayerCount();
		for(TriantaPlayer currPlayer:triantaplayers) {
			if(currPlayer.isCashout()== true)
				remainingPlayers--;
		}
		if(remainingPlayers == 0)
			return true;
		else
			return false;
		
	}

}
