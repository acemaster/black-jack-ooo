package com.bu.cs.component.cardgame.trianta;

import com.bu.cs.component.Player;
import com.bu.cs.component.cardgame.CardGame;
import com.bu.cs.component.cardgame.card.Card;
import com.bu.cs.component.cardgame.card.CardValue;
import com.bu.cs.helper.GameConstants;
import com.bu.cs.helper.GameFunctions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Trianta Game
 */
public class TriantaGame extends CardGame {

	private List<TriantaPlayer> triantaPlayers;
	private TriantaDealer gameDealer;
	private int dealerIndex;

	/**
	 * Get the name of the game
	 * @return
	 */
    @Override
    public String getName() {
        return "Trianta Enta";
    }

	/**
	 * Summary of the game
	 */
    @Override
    public void summary() {
		//to add properly
		System.out.println("Game Summary");
		System.out.println("==============");
		int totalGames = 0;
		for(TriantaPlayer currPlayer: triantaPlayers) {
			if(currPlayer.getPlayerId() == gameDealer.getPlayerId()) {
				System.out.printf("Dealer %s: %n",currPlayer.getName());
				System.out.printf("\tAccount Value: %d %n",currPlayer.getMoney());
			} else {
				System.out.printf("Player %s: %n", currPlayer.getName());
				System.out.printf("\tWins         : %d %n", currPlayer.getWins());
				System.out.printf("\tAccount Value: %d %n", currPlayer.getMoney());
			}
			totalGames = totalGames + currPlayer.getWins();
		}
    }


	/**
	 * Check the game is complete
	 * @param playerIndex
	 * @return
	 */
	@Override
	public boolean isGameComplete(int playerIndex) {
		int handIndex = 0;
		if (triantaPlayers.get(playerIndex).getHands().get(handIndex).isWon() == true) {
			System.out.println(triantaPlayers.get(playerIndex).getName()+" has a natural Trianta Ena.\n"+ triantaPlayers.get(playerIndex).getName()+" wins!!!");
		}
		else if(gameDealer.isBust() == true) {
			if(triantaPlayers.get(playerIndex).getHands().get(0).isBust() == false) {
				triantaPlayers.get(playerIndex).getHands().get(handIndex).setWon(true);
				System.out.println(triantaPlayers.get(playerIndex).getName()+" wins");
			}			
		}
		else {
			 if((triantaPlayers.get(playerIndex).getHands().get(handIndex).currentHand() > gameDealer.getHands().get(0).currentHand()) && triantaPlayers.get(playerIndex).getHands().get(0).isBust() == false) {
				triantaPlayers.get(playerIndex).getHands().get(handIndex).setWon(true);
				System.out.println(triantaPlayers.get(playerIndex).getName()+" wins");
			}
		}
		return triantaPlayers.get(playerIndex).getHands().get(handIndex).isWon();
	}

	/**
	 * Check if it is a natural trianta
	 * @param playerIndex
	 * @return
	 */
    public boolean isTrianta(int playerIndex) {
    	int handIndex = 0;
    	List<CardValue> playerCardValues = new ArrayList<>();
    	for(int i = 0; i< triantaPlayers.get(playerIndex).getHands().get(handIndex).getCards().size(); i++) {
    		playerCardValues.add(triantaPlayers.get(playerIndex).getHands().get(handIndex).getCards().get(i).getCardValue());
    	}
    	int aceOccurences = Collections.frequency(playerCardValues, CardValue.ACE);
    	int kingOccurences = Collections.frequency(playerCardValues, CardValue.KING);
    	int queenOccurences = Collections.frequency(playerCardValues, CardValue.QUEEN);
    	int jackOccurences = Collections.frequency(playerCardValues, CardValue.JACK);
    	if(aceOccurences == 1 && kingOccurences+queenOccurences+jackOccurences == 2)
    		triantaPlayers.get(playerIndex).getHands().get(handIndex).setWon(true);
        return triantaPlayers.get(playerIndex).getHands().get(handIndex).isWon();
    }

	/**
	 * Check if the player has bust
	 * @param playerIndex
	 */
	public boolean isPlayerBust(int playerIndex) {
    	if(triantaPlayers.get(playerIndex).getHands().get(0).currentHand() > this.cardGameConfig.getWinCondition()) {
    		triantaPlayers.get(playerIndex).getHands().get(0).setBust(true);
    		if(triantaPlayers.get(playerIndex).isDealer() == true)
    			System.out.println("Dealer's hand is a bust");
    		else {
    			triantaPlayers.get(playerIndex).summary();
    			System.out.println("Busted!! Try your luck in the next round");
    		}
    	}   		
    	return triantaPlayers.get(playerIndex).getHands().get(0).isBust();
    }

	/**
	 * Start the game
	 */
	@Override
    public void startGame() {
    	try {
	    	System.out.println("Welcome to the Trianta");
	    	Scanner scanner = new Scanner(System.in);
	    	initializeGame(scanner); 
	        int playerMoney = GameFunctions.safeScanInt(scanner,"Enter the amount each player would like to start with: ");
	        scanner.nextLine();
	        System.out.println("Dealer starts with $"+playerMoney*3);
			selectDealer(scanner, playerMoney);
			//Each player gets a card including dealer
            boolean isEndGame = false;
            while(!isEndGame) {
                System.out.println("Let's play!!");
				playRound(scanner);
				settleRound();
    	        isEndGame = endGame();
            }//while    
    	}
    	catch(Exception e) {
    		System.out.println(e);
    	}
    }

	/**
	 * Select the dealer
	 * @param scanner
	 * @param playerMoney
	 */
	private void selectDealer(Scanner scanner, int playerMoney) {
		System.out.println("Please select a dealer from the list of players: ");
		for (int i = 0; i < triantaPlayers.size(); i++) {
			System.out.printf("%d. %s %n",i + 1, triantaPlayers.get(i).getName());
		}
		dealerIndex = GameFunctions.safeScanIntWithLimit(scanner,"Enter option: ",1,this.cardGameConfig.getPlayerCount())-1;
		scanner.nextLine();
		gameDealer.setName(cardPlayers.get(dealerIndex).getName());
		gameDealer.setPlayerId(cardPlayers.get(dealerIndex).getPlayerId());
		triantaPlayers.get(dealerIndex).setDealer(true);
		gameDealer.addMoney(playerMoney *3);
		for (int i = 0; i < triantaPlayers.size(); i++) {
			if(i == dealerIndex) continue;
			triantaPlayers.get(i).addMoney(playerMoney);
		}
	}

	/**
	 * Play the round
	 * @param scanner
	 */
	private void playRound(Scanner scanner) {
		for(TriantaPlayer currPlayer: triantaPlayers) {
			if(currPlayer.isDealer() == false && currPlayer.isCashout() == false)
				currPlayer.addCard(gameDealer.dealPlayer(decks, false));
		}
		gameDealer.addCard(gameDealer.dealPlayer(decks, false));
		
		foldOrBet(scanner);		
		checkNaturalTriantaForPlayer();
		displayDealerPlayerHand(scanner);
		//after players are done, dealer plays until his mincount and check for a natural Trianta Ena
		gameDealer.addCard(gameDealer.dealPlayer(decks, false));
		gameDealer.addCard(gameDealer.dealPlayer(decks, false));
		gameDealer.summary();
		for(Card dealercards: gameDealer.getHands().get(0).getCards()) {
			triantaPlayers.get(dealerIndex).resetHand();
			triantaPlayers.get(dealerIndex).addCard(dealercards);//just to check natural Trianta Ena logic based on player class
		}
		if(isTrianta(dealerIndex) == true) {
			gameDealer.getHands().get(0).setWon(true);
		}
		else
			System.out.println("Dealer takes a hit until the card total reaches "+gameDealer.getMinvalue());
		while(gameDealer.getHands().get(0).currentHand() < gameDealer.getMinvalue() && gameDealer.isBust() == false && gameDealer.getHands().get(0).isWon() == false) {
			triantaPlayers.get(dealerIndex).resetHand();//reset and added later to keep in sync
			gameDealer.addCard(gameDealer.dealPlayer(decks, false));
			gameDealer.summary();
			for(Card dealercards: gameDealer.getHands().get(0).getCards()) {
				triantaPlayers.get(dealerIndex).addCard(dealercards);//just to check isbust logic based on player class
			}
			boolean isDealerBust = isPlayerBust(dealerIndex);
			if(isDealerBust)
				gameDealer.setBust(true);
		}//while
		//check if player won/lose, dealer wins if it is a draw
		if((gameDealer.getHands().get(0).isWon() == true)){//if dealer gets a natural Trianta Ena, he win no matter what
			System.out.println("Dealer has a natural Trianta Ena.\nDealer wins!!!");
			for(TriantaPlayer currPlayer: triantaPlayers) {
				if(currPlayer.isDealer() == true) continue;
				currPlayer.getHands().get(0).setWon(false);
			}
		}
		else {
			for(TriantaPlayer currPlayer: triantaPlayers) {
				if(currPlayer.isDealer() == false && currPlayer.isfold() == false && currPlayer.isCashout() == false) {
					boolean isPlayerwin = isGameComplete(triantaPlayers.indexOf(currPlayer));
				}
			}
		}
	}
	
	//Each player can either chose hit till bust or stand
	private void displayDealerPlayerHand(Scanner scanner) {
		for(TriantaPlayer currPlayer: triantaPlayers) {
			while((currPlayer.getHands().get(0).isBust() == false && currPlayer.getHands().get(0).isStand() == false) && currPlayer.isfold() == false && currPlayer.getHands().get(0).isWon() == false && currPlayer.isDealer() == false && currPlayer.isCashout() == false) {
				currPlayer.summary();
				System.out.println(currPlayer.getName()+",choose the action you would like to take\n1.Hit\n2.Stand");
				int playerOption = GameFunctions.safeScanIntWithLimit(scanner,"Enter option: ",1,2);
				if(playerOption == 1) {
					currPlayer.hit(decks, false);
					boolean isPlayer_Bust = isPlayerBust(triantaPlayers.indexOf(currPlayer));
				}
				else
					currPlayer.getHands().get(0).setStand(true);
			}//while
		}//for
	}
	
	//Once the players bet deals two more cards and check if its a natural Trianta Ena and wait for dealers hand
	private void checkNaturalTriantaForPlayer() {
		for(TriantaPlayer currPlayer: triantaPlayers) {
			if(currPlayer.isfold() == false && currPlayer.isDealer() == false && currPlayer.isCashout() == false) {
				currPlayer.addCard(gameDealer.dealPlayer(decks, false));
				currPlayer.addCard(gameDealer.dealPlayer(decks, false));
				if(isTrianta(triantaPlayers.indexOf(currPlayer))) {
					currPlayer.getHands().get(0).setWon(true);
					currPlayer.summary();
					System.out.println(currPlayer.getName()+", you have a natural Trianta Ena. Let's see what the dealer has.");
				}
			}
		}
	}
	
	//Display the dealer and players hand to let him choose either fold or bet
	private void foldOrBet(Scanner scanner) {
		for(TriantaPlayer currPlayer: triantaPlayers) {
			if(currPlayer.isDealer() == false && currPlayer.isCashout() == false) {
				gameDealer.summary();
				currPlayer.summary();
				System.out.println(currPlayer.getName()+", Choose the action you would like to take\n1.Fold\n2.Bet");
				int playerOption = GameFunctions.safeScanIntWithLimit(scanner,"Enter option: ",1,2);
				if(playerOption == 2) {
					int playerBet = GameFunctions.safeScanIntWithLimit(scanner,"Enter amount you would like to bet: ",1,currPlayer.getMoney());
					currPlayer.getHands().get(0).setBet(playerBet);
				}
				else {
					currPlayer.setIsfold(true);
					System.out.println(currPlayer.getName()+", chose to fold");
				}
			}
		}
	}

	private void initializeGame(Scanner scanner) {
    	triantaPlayers = new ArrayList<>();
        cardPlayers = new ArrayList<>();
        gameDealer = new TriantaDealer(cardGameConfig);
        this.cardGameConfig.setPlayerCount(GameFunctions.safeScanIntWithLimit(scanner,"Please enter the number of players: ", GameConstants.TRIANTA_PLAYER_LOWER_LIMIT,GameConstants.TRIANTA_PLAYER_UPPER_LIMIT));
        scanner.nextLine();
        this.cardGameConfig.setNumberOfDecks(GameConstants.TRIANTA_DECK_COUNT);
        this.cardGameConfig.setWinCondition(GameConstants.TRIANTA_WIN_CONDITION);
        initializeDeck();
        for(int i=0;i<this.cardGameConfig.getPlayerCount();i++){
        	TriantaPlayer player = new TriantaPlayer(cardGameConfig);
            player.setName(GameFunctions.safeScanString(scanner,"Please enter the name for player " + (i+1) + ": "));
            player.setPlayerId((i));
            cardPlayers.add(player);
            triantaPlayers.add(player);
        }
    }

    @Override
    public Player[] getPlayers() {
        return new Player[0];
    }

	@Override
	public void settleRound() {
		// settle the bet checking if the player won or lost
		for(TriantaPlayer currPlayer: triantaPlayers) {
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
			currPlayer.resetPlayer();
		}
		gameDealer.resetHand();
		triantaPlayers.get(dealerIndex).setMoney(gameDealer.getMoney());//showing dealer wallet as a player
		summary();//display player summary before asking for dealer change

		
		Collections.sort(triantaPlayers);
		Collections.reverse(triantaPlayers);//sort in decreasing order of their money

		Scanner scanner = new Scanner(System.in);
		boolean dealerSet = false;
		int playerIndex = 0;
		while(dealerSet == false) {
			if(triantaPlayers.get(playerIndex).isDealer() == true) {
				dealerSet = true;
				dealerIndex = playerIndex;
				System.out.println(triantaPlayers.get(playerIndex).getName()+" will continue to be the dealer for the next round");
			}
			else if(triantaPlayers.get(playerIndex).isCashout() == false){
				String playerAnswer = GameFunctions.safeScanString(scanner, triantaPlayers.get(playerIndex).getName()+", Would you like to be the dealer?Y/N:");
				if(playerAnswer.equalsIgnoreCase("y")) {
					for(TriantaPlayer currPlayer: triantaPlayers) {
						if(currPlayer.isDealer() == true)
							currPlayer.setDealer(false);
					}
					triantaPlayers.get(playerIndex).setDealer(true);
					dealerSet = true;
					dealerIndex = playerIndex;
					gameDealer.setName(cardPlayers.get(playerIndex).getName());
					gameDealer.setPlayerId(cardPlayers.get(playerIndex).getPlayerId());
					gameDealer.setMoney(triantaPlayers.get(playerIndex).getMoney());
				}
				else
					playerIndex++;
			}
		}
	}

	/**
	 * End the game
	 * @return
	 */
	public boolean endGame() {
		for(TriantaPlayer currPlayer: triantaPlayers) {
			if(currPlayer.getMoney()<=0 && currPlayer.isCashout() == false) {
				System.out.println(currPlayer.getName()+", you are out of money. Thankyou for playng");
				currPlayer.setCashout(true);
			}
			else {
				if(currPlayer.isDealer() == false && currPlayer.isCashout() == false) {
					Scanner scanner = new Scanner(System.in);
					String playerAnswer = GameFunctions.safeScanString(scanner,currPlayer.getName()+", would you like to cashout?Y/N:");
					if(playerAnswer.equalsIgnoreCase("y")) {
						currPlayer.setCashout(true);
					}
				}
			}
		}
		int remainingPlayers = this.cardGameConfig.getPlayerCount()-1;
		for(TriantaPlayer currPlayer: triantaPlayers) {
			if(currPlayer.isCashout()== true)
				remainingPlayers--;
		}
		if(remainingPlayers == 0)
			return true;
		else
			return false;
		
	}

}
