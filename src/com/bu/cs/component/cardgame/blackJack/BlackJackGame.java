package com.bu.cs.component.cardgame.blackJack;

import com.bu.cs.component.Player;
import com.bu.cs.component.cardgame.CardGame;
import com.bu.cs.component.cardgame.card.Card;
import com.bu.cs.component.cardgame.card.Hand;
import com.bu.cs.helper.GameConstants;
import com.bu.cs.helper.GameFunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlackJackGame extends CardGame {

    private List<BlackJackPlayer> blackJackPlayers;
    private BlackjackDealer blackjackDealer;

    /**
     * Name of the game
     * @return name
     */
    @Override
    public String getName() {
        return "BlackJack";
    }

    /**
     * Summary of the game
     */
    @Override
    public void summary() {
        System.out.println("Round Summary");
        System.out.println("==============");
        int totalGames = 0;
        System.out.println("Dealer: ");
        System.out.printf("\tWins         : %d %n",blackjackDealer.getWins());
        System.out.printf("\tAccount Value: %d %n",blackjackDealer.getMoney());
        totalGames = totalGames + blackjackDealer.getWins();
        for(BlackJackPlayer blackJackPlayer:blackJackPlayers) {
            System.out.printf("Player %s: %n",blackJackPlayer.getName());
            System.out.printf("\tWins         : %d %n",blackJackPlayer.getWins());
            System.out.printf("\tAccount Value: %d %n",blackJackPlayer.getMoney());
            totalGames = totalGames + blackJackPlayer.getWins();
        }
        System.out.printf("Total Rounds played: %d %n",totalGames);
    }


    /**
     * Check if game is complete for the player
     * @param playerIndex
     * @return
     */
    @Override
    public boolean isGameComplete(int playerIndex) {
        List<Hand> hands = this.blackJackPlayers.get(playerIndex).getHands();
        for(Hand hand: hands){
            if(hand.currentHand() == this.cardGameConfig.getWinCondition())
                return true;
            if(hand.currentHand() > this.blackJackPlayers.get(playerIndex).getHands().get(0).currentHand())
                return true;
        }
        return false;
    }

    /**
     * Set of deal at the beginning of each round
     * @param scanner
     */
    public void dealAndBet(Scanner scanner){
        for(BlackJackPlayer blackJackPlayer: blackJackPlayers) {
            if(blackJackPlayer.getMoney() == 0) {
                blackJackPlayer.setMoney(GameFunctions.safeScanInt(scanner, String.format("Player %s: Please enter the initial money you want to convert to chips: ",blackJackPlayer.getName())));
            }
            for(Hand hand: blackJackPlayer.getHands()){
                hand.setBet(GameFunctions.safeScanInt(scanner,String.format("Player %s: Please place initial bet: ",blackJackPlayer.getName())));
            }
            blackJackPlayer.addCard(blackjackDealer.dealPlayer(decks, false));
            blackJackPlayer.addCard(blackjackDealer.dealPlayer(decks, false));
        }
        blackjackDealer.initialize(decks);
    }

    /**
     * Resetting the game
     */
    public void resetGame(){
        for(BlackJackPlayer blackJackPlayer: blackJackPlayers) {
            blackJackPlayer.resetPlayer();
        }
        blackjackDealer.resetHand();
    }

    /**
     * Checking if there are any remaining players
     * @return
     */
    public boolean isRemainingPlayers() {
        for(BlackJackPlayer blackJackPlayer: blackJackPlayers) {
            for(Hand hand: blackJackPlayer.getHands()) {
                if (blackJackPlayer.getMoney() > 0 && !hand.isStand() && !hand.isBust()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Start the game
     */
    @Override
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the game of BlackJack!!");
        initializeGame(scanner, GameConstants.BLACK_JACK_PLAYERS);
        while(true){
            // Round starts here
            //Initialize the game with all the details
            dealAndBet(scanner);
            int playerIndex = 0;
            while(isRemainingPlayers()){
                BlackJackPlayer currentPlayer = blackJackPlayers.get(playerIndex);
                //Summary of dealer and current player
                blackjackDealer.summary(false);
                currentPlayer.summary();
                playPlayerPass(scanner, currentPlayer);
                playerIndex = (playerIndex + 1)%cardGameConfig.getPlayerCount();
            }
            settleRound();
            scanner.nextLine();
            summary();
            if(!GameFunctions.safeScanString(scanner, "Do you want to continue?(Y/N)").equalsIgnoreCase("Y")){
                System.out.println("Thanks for playing!");
                break;
            }
            resetGame();
        }
    }

    private void playPlayerPass(Scanner scanner, BlackJackPlayer currentPlayer) {
        for(int i = 0; i< currentPlayer.getHands().size(); i++) {
            while (currentPlayer.getHands().get(i).currentHand() <= this.cardGameConfig.getWinCondition() && !currentPlayer.getHands().get(i).isStand() ){
                int upperLimit = 3;
                String message = "Player "+ currentPlayer.getName()+": Please enter one of the options:\n1. Hit\n2. Stand\n3. Double\n";
                if(currentPlayer.getHands().get(i).getCards().size() == 2) {
                    List<Card> handCards = currentPlayer.getHands().get(i).getCards();
                    if(handCards.get(0).equals(handCards.get(1))) {
                        message = message + "4.Split\n";
                        upperLimit = 4;
                    }
                }
                message = message + "Please enter the option: ";
                int option = GameFunctions.safeScanIntWithLimit(scanner,message , 1, upperLimit);
                switch (option) {
                    case 1:
                        currentPlayer.hit(decks, i, false);
                        break;
                    case 2:
                        currentPlayer.getHands().get(i).setStand(true);
                        break;
                    case 3:
                        currentPlayer.doubleUp(i, decks);
                        break;
                    case 4:
                        currentPlayer.split(i);
                        break;
                }
                currentPlayer.getHands().get(i).display();
                System.out.printf("%s Hand %d current Hand: %d %n", currentPlayer.getName(), 1, currentPlayer.getHands().get(i).currentHand());
                if(currentPlayer.getHands().get(i).currentHand() > this.cardGameConfig.getWinCondition()){
                    // Player Bust
                    System.out.printf("Player %s: The player's Hand has bust %n", currentPlayer.getName());
                    currentPlayer.getHands().get(i).setBust(true);
                }
            }
        }
    }

    /**
     * Initialize the game with the game and player details
     * @param scanner
     * @param playerCount
     */
    private void initializeGame(Scanner scanner,int playerCount) {
        blackJackPlayers = new ArrayList<>();
        this.cardGameConfig.setPlayerCount(playerCount);
        this.cardGameConfig.setNumberOfDecks(GameFunctions.safeScanInt(scanner,"Please enter the number of decks: "));
        this.cardGameConfig.setWinCondition(GameFunctions.safeScanInt(scanner,"Please enter the game win condition: "));
        initializeDeck();
        scanner.nextLine();
        blackjackDealer = new BlackjackDealer();
        blackjackDealer.setName("Dealer");
        for(int i=0;i<this.cardGameConfig.getPlayerCount();i++){
            BlackJackPlayer player = new BlackJackPlayer();
            player.setName(GameFunctions.safeScanString(scanner,"Please enter the name for player " + (i+1) + ": "));
            player.setPlayerId((i));
            blackJackPlayers.add(player);
        }
    }

    /**
     * Get the players
     * @return
     */
    @Override
    public Player[] getPlayers() {
        return new Player[0];
    }

    /**
     * Settle the round by checking the hands of the dealer and the players
     */
    @Override
    public void settleRound() {
        System.out.println("Dealer: Showing face down card and hitting until minValue");
        blackjackDealer.revealCards();
        while (blackjackDealer.getHands().get(0).currentHand() <= blackjackDealer.getMinValue()) {
            blackjackDealer.hit(decks, false);
        }
        blackjackDealer.summary();
        if(blackjackDealer.getHands().get(0).currentHand() > this.cardGameConfig.getWinCondition()){
            System.out.printf("Dealer: Current hand is more than %d so the dealer has bust. %n",blackjackDealer.getHands().get(0).currentHand());
            blackjackDealer.getHands().get(0).setBust(true);
        }
        for (BlackJackPlayer currentPlayer : blackJackPlayers) {
            for (int i = 0; i < currentPlayer.getHands().size(); i++) {
                int currentBet = currentPlayer.getHands().get(i).getBet();
                if(blackjackDealer.getHands().get(0).isBust() && !currentPlayer.getHands().get(i).isBust()){
                    //for dealer bust
                    System.out.printf("%s Hand %d: Wins since dealer is bust %n",currentPlayer.getName(),(i+1));
                    currentPlayer.addMoney(currentBet);
                    currentPlayer.incrementWins();
                    continue;
                }
                if(currentPlayer.getHands().get(i).isBust()){
                    // for player bust
                    System.out.printf("%s Hand %d: Loses since player is bust %n",currentPlayer.getName(),(i+1));
                    currentPlayer.removeMoney(currentBet);
                    blackjackDealer.addMoney(currentBet);
                    blackjackDealer.incrementWins();
                    continue;
                }
                if (blackjackDealer.getHands().get(0).currentHand() < currentPlayer.getHands().get(0).currentHand()) {
                    //For player win
                    System.out.printf("%s Hand %d: Wins since hand is greater %n",currentPlayer.getName(),(i+1));
                    currentPlayer.addMoney(currentBet);
                    currentPlayer.incrementWins();
                } else if(blackjackDealer.getHands().get(0).currentHand() > currentPlayer.getHands().get(0).currentHand()) {
                    //For dealer win
                    System.out.printf("%s Hand %d: Loses since hand value is less than dealer %n",currentPlayer.getName(),(i+1));
                    currentPlayer.removeMoney(currentBet);
                    blackjackDealer.addMoney(currentBet);
                    blackjackDealer.incrementWins();
                } else {
                    if(currentPlayer.isNaturalBlackJack(i,cardGameConfig.getWinCondition()) && !blackjackDealer.isNaturalBlackJack(0, cardGameConfig.getWinCondition())) {
                        System.out.printf("%s Hand %d: Wins due to Natural Black Jack %n",currentPlayer.getName(),(i+1));
                        currentPlayer.addMoney(currentBet);
                        currentPlayer.incrementWins();
                    }
                    System.out.printf("%s Hand %d: Draw since hand value is equal %n",currentPlayer.getName(),(i+1));
                }
            }
        }
    }
}
