package com.bu.cs.component.cardgame.blackJack;

import com.bu.cs.component.Player;
import com.bu.cs.component.cardgame.CardGame;
import com.bu.cs.component.cardgame.card.Card;
import com.bu.cs.component.cardgame.card.Hand;
import com.bu.cs.helper.GameFunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlackJackGame extends CardGame {

    private List<BlackJackPlayer> cardPlayers;
    private BlackjackDealer blackjackDealer;

    @Override
    public String getName() {
        return "BlackJack";
    }

    @Override
    public void summary() {

    }

    public  boolean isNaturalBlackJack(int playerIndex){
        List<Hand> hands = this.cardPlayers.get(playerIndex).getHands();
        for(Hand hand: hands){
            if(hand.currentHand() == this.cardGameConfig.getWinCondition())
                return true;
        }
        return false;
    }

    @Override
    public boolean isGameComplete(int playerIndex) {
        List<Hand> hands = this.cardPlayers.get(playerIndex).getHands();
        for(Hand hand: hands){
            if(hand.currentHand() == this.cardGameConfig.getWinCondition())
                return true;
            if(hand.currentHand() > this.cardPlayers.get(playerIndex).getHands().get(0).currentHand())
                return true;
        }
        return false;
    }

    public void dealAndBet(Scanner scanner){
        for(BlackJackPlayer blackJackPlayer:cardPlayers) {
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
        for(BlackJackPlayer blackJackPlayer:cardPlayers) {
            blackJackPlayer.resetPlayer();
        }
        blackjackDealer.resetHand();
    }

    public boolean isRemainingPlayers() {
        for(BlackJackPlayer blackJackPlayer: cardPlayers) {
            if(blackJackPlayer.getMoney() > 0 && !blackJackPlayer.isStand() && !blackJackPlayer.isBust()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the game of BlackJack!!");
        initializeGame(scanner,1);
        while(true){ // new game
            //Initialize the game with all the details
            dealAndBet(scanner);
            System.out.println(cardPlayers.size());
            int playerIndex = 0;
            int currentBet = cardPlayers.get(playerIndex).getHands().get(0).getBet();
            while(isRemainingPlayers()){
                BlackJackPlayer currentPlayer = cardPlayers.get(0); //Todo: Use this instead of currentPlayer
                //Skips the players who have no remaining money
                if(currentPlayer.getMoney() <= 0 || currentPlayer.isBust() || currentPlayer.isStand()) {
                    continue;
                }
                //Summary of dealer and current player
                blackjackDealer.summary();
                currentPlayer.summary();
                if (this.isNaturalBlackJack(playerIndex)) {
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
                    while (currentPlayer.getHands().get(i).currentHand() < 21){
                        System.out.println("Current hand value: " + currentPlayer.getHands().get(i).currentHand());
                        int option = GameFunctions.safeScanIntWithLimit(scanner, "Please enter one of the options:\n1. Hit\n2. Stand\n3. Double", 1, 3);
                        switch (option) {
                            case 1:
                                currentPlayer.hit(decks, i, false);
//                                System.out.println("Current hand value: " + currentPlayer.getHands().get(i).currentHand());
                                break;
                            case 2:
                                currentPlayer.setStand(true);
                                break;
                            case 3:
                                currentPlayer.doubleUp(i, decks);
                                System.out.println("Current hand value: " + currentPlayer.getHands().get(i).currentHand());
                                break;
                        }
                        if (currentPlayer.isStand())
                            break;
                    }
                    if(currentPlayer.getHands().get(i).currentHand() > 21){ //Bust
                        System.out.println("Hand " + (i+1) + " bust");
                        currentPlayer.setBust(true);
//                        currentPlayer.removeMoney(currentBet);
//                        System.out.println("Current balance: " + currentPlayer.getMoney());
                        continue;
                    }
                    if(currentPlayer.isStand()){
                        isGameComplete(1);
                    }
                }
                if(currentPlayer.isStand()){
                    //Todo: Separate out as function and
                    System.out.println("Initial dealer hand value: " + currentPlayer.getHands().get(0).currentHand());
                    while(currentPlayer.getHands().get(0).currentHand() <= 17){
                        currentPlayer.hit(decks, false);
                        System.out.println("Dealer hand value: " + currentPlayer.getHands().get(0).currentHand());
                    }
                    int dealerMoney = 0;
                    List<Hand> hands = currentPlayer.getHands();
                    for(Hand hand: hands){
                        if(hand.currentHand() > currentPlayer.getHands().get(0).currentHand()) {
                            System.out.println("Hand won");
                            cardPlayers.get(1).addMoney(currentBet * 2);
                            System.out.println("Current balance: " + cardPlayers.get(1).getMoney());
                        }
                        else{
                            dealerMoney += currentBet;
                        }
                    }
                    if(dealerMoney > 0){
                        System.out.println("Dealer won!");
                        blackjackDealer.addMoney(dealerMoney);
//                        System.out.println("Current dealer balance: " + cardPlayers.get(0).getMoney());
                    }
//                    if(isGameComplete(0)){
//                        System.out.println("Dealer won!");
//                        //todo add logic to add and remove money
//                        cardPlayers.get(1).addMoney(currentBet);
//                        break;
//                    }
                }
                resetGame();
                playerIndex = (playerIndex + 1)%cardGameConfig.getPlayerCount();

            }
//                System.out.println("Do you want to continue?");
            if(!GameFunctions.safeScanString(scanner, "Do you want to continue?(Y/N)").equalsIgnoreCase("Y")){
                System.out.println("Thanks for playing!");
                break;
            }
        }
    }

    private void initializeGame(Scanner scanner,int playerCount) {
        scanner = new Scanner(System.in);
        cardPlayers = new ArrayList<>();
        this.cardGameConfig.setPlayerCount(playerCount);
//      this.cardGameConfig.setPlayerCount(GameFunctions.safeScanInt(scanner,"Please enter the number of players: "));
        this.cardGameConfig.setNumberOfDecks(GameFunctions.safeScanInt(scanner,"Please enter the number of decks: "));
        this.cardGameConfig.setWinCondition(GameFunctions.safeScanInt(scanner,"Please enter the game win condition: "));
        initializeDeck();
        blackjackDealer = new BlackjackDealer();
//        cardPlayers.add(blackjackDealer);
        for(int i=0;i<this.cardGameConfig.getPlayerCount();i++){
            BlackJackPlayer player = new BlackJackPlayer();
            player.setName(GameFunctions.safeScanString(scanner,"Please enter the name for player " + (i+1) + ": "));
            player.setPlayerId((i));
            System.out.println(player.getHands().size());
            cardPlayers.add(player);
        }
    }

    @Override
    public Player[] getPlayers() {
        return new Player[0];
    }
}
