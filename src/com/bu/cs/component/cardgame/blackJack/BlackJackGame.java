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

    @Override
    public String getName() {
        return "BlackJack";
    }

    @Override
    public void summary() {

    }



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

    public void dealAndBet(Scanner scanner){
        for(BlackJackPlayer blackJackPlayer: blackJackPlayers) {
            blackJackPlayer.setMoney(GameFunctions.safeScanInt(scanner,"Please enter the initial money you want to convert to chips: "));
            for(Hand hand: blackJackPlayer.getHands()){
                hand.setBet(GameFunctions.safeScanInt(scanner,"Please place initial bet: "));
            }
            blackJackPlayer.addCard(blackjackDealer.dealPlayer(decks, false));
            blackJackPlayer.addCard(blackjackDealer.dealPlayer(decks, false));
        }
        blackjackDealer.initialize(decks);
    }

    public void resetGame(){
        for(BlackJackPlayer blackJackPlayer: blackJackPlayers) {
            blackJackPlayer.resetPlayer();
        }
        blackjackDealer.resetHand();
    }

    public boolean isRemainingPlayers() {
        for(BlackJackPlayer blackJackPlayer: blackJackPlayers) {
            for(Hand hand: blackJackPlayer.getHands())
            if(blackJackPlayer.getMoney() > 0 && !hand.isStand() && !hand.isBust()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the game of BlackJack!!");
        initializeGame(scanner, GameConstants.BLACK_JACK_PLAYERS);
        while(true){ // new game
            //Initialize the game with all the details
            dealAndBet(scanner);
            System.out.println(blackJackPlayers.size());
            int playerIndex = 0;
            int currentBet = blackJackPlayers.get(playerIndex).getHands().get(0).getBet();
            while(isRemainingPlayers()){
                BlackJackPlayer currentPlayer = blackJackPlayers.get(0); //Todo: Use this instead of currentPlayer
                //Summary of dealer and current player
                blackjackDealer.summary();
                currentPlayer.summary();
                if (currentPlayer.isNaturalBlackJack(0, this.cardGameConfig.getWinCondition())) {
                        System.out.println("A natural Black Jack");
                        break;
                }
                List<Card> cards = currentPlayer.getHands().get(0).getCards();
                System.out.println(cards.size());
                if(cards.get(0).equals(cards.get(1)) && cards.size() >= 2){ //Todo: Might need to check if the size of cards is 2

                    if(GameFunctions.safeScanString(scanner, "Do you want to split?").equalsIgnoreCase("Y")){
                        currentPlayer.split();
                    }
                }
                for(int i=0;i<currentPlayer.getHands().size();i++) { // Each hand
                    while (currentPlayer.getHands().get(i).currentHand() < 21 && !currentPlayer.getHands().get(i).isStand()){
                        System.out.println("Current hand value: " + currentPlayer.getHands().get(i).currentHand());
                        int option = GameFunctions.safeScanIntWithLimit(scanner, "Please enter one of the options:\n1. Hit\n2. Stand\n3. Double", 1, 3);
                        switch (option) {
                            case 1:
                                currentPlayer.hit(decks, i, false);
//                                System.out.println("Current hand value: " + currentPlayer.getHands().get(i).currentHand());
                                break;
                            case 2:
                                currentPlayer.getHands().get(i).setStand(true);
                                break;
                            case 3:
                                currentPlayer.doubleUp(i, decks);
                                System.out.println("Current hand value: " + currentPlayer.getHands().get(i).currentHand());
                                break;
                        }
                    }
                    if(currentPlayer.getHands().get(i).currentHand() > 21){ //Bust
                        System.out.println("Hand " + (i+1) + " bust");
                        currentPlayer.getHands().get(i).setBust(true);
//                        currentPlayer.removeMoney(currentBet);
//                        System.out.println("Current balance: " + currentPlayer.getMoney());
                    }
                }
                resetGame();
                playerIndex = (playerIndex + 1)%cardGameConfig.getPlayerCount();

            }
            settleRound();


//                System.out.println("Do you want to continue?");
            if(!GameFunctions.safeScanString(scanner, "Do you want to continue?(Y/N)").equalsIgnoreCase("Y")){
                System.out.println("Thanks for playing!");
                break;
            }
        }
    }

    private void initializeGame(Scanner scanner,int playerCount) {
        scanner = new Scanner(System.in);
        blackJackPlayers = new ArrayList<>();
        this.cardGameConfig.setPlayerCount(playerCount);
//      this.cardGameConfig.setPlayerCount(GameFunctions.safeScanInt(scanner,"Please enter the number of players: "));
        this.cardGameConfig.setNumberOfDecks(GameFunctions.safeScanInt(scanner,"Please enter the number of decks: "));
        this.cardGameConfig.setWinCondition(GameFunctions.safeScanInt(scanner,"Please enter the game win condition: "));
        initializeDeck();
        blackjackDealer = new BlackjackDealer();
//        blackJackPlayers.add(blackjackDealer);
        for(int i=0;i<this.cardGameConfig.getPlayerCount();i++){
            BlackJackPlayer player = new BlackJackPlayer();
            player.setName(GameFunctions.safeScanString(scanner,"Please enter the name for player " + (i+1) + ": "));
            player.setPlayerId((i));
            System.out.println(player.getHands().size());
            blackJackPlayers.add(player);
        }
    }

    @Override
    public Player[] getPlayers() {
        return new Player[0];
    }

    @Override
    public void settleRound() {
        System.out.println("Initial dealer hand value: " + blackjackDealer.getHands().get(0).currentHand());
        while (blackjackDealer.getHands().get(0).currentHand() <= blackjackDealer.getMinValue()) {
            blackjackDealer.hit(decks, false);
            System.out.println("Dealer hand value: " + blackjackDealer.getHands().get(0).currentHand());
        }
        if(blackjackDealer.getHands().get(0).currentHand() > this.cardGameConfig.getWinCondition()){
            System.out.println("Dealer Bust!");
            blackjackDealer.getHands().get(0).setBust(true);
        }
        for (BlackJackPlayer currentPlayer : blackJackPlayers) {
            for (int i = 0; i < currentPlayer.getHands().size(); i++) {
                int currentBet = currentPlayer.getHands().get(i).getBet();
                if(blackjackDealer.getHands().get(0).isBust()){ // for dealer bust
                    currentPlayer.addMoney(currentBet);
                    continue;
                }
                if(currentPlayer.getHands().get(i).isBust()){ // for player bust
                    currentPlayer.removeMoney(currentBet);
                    System.out.println("Current balance: " + currentPlayer.getMoney());
                    continue;
                }
                if (blackjackDealer.getHands().get(0).currentHand() > currentPlayer.getHands().get(0).currentHand()) {
                    System.out.println(currentPlayer.getName() + " Hand" + (i+1) + " won!");
                    currentPlayer.addMoney(currentBet);
                    System.out.println("Current balance: " + blackJackPlayers.get(1).getMoney());
                } else {
                    blackjackDealer.addMoney(currentBet);
                }
//                if (dealerMoney > 0) {
//                    System.out.println("Dealer won!");
//                    blackjackDealer.addMoney(dealerMoney);
////                        System.out.println("Current dealer balance: " + blackJackPlayers.get(0).getMoney());
//                }
            }
        }
    }
}
