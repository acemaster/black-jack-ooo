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

    public  boolean isNaturalBlackJack(int playerIndex){
        List<Hand> hands = this.blackJackPlayers.get(playerIndex).getHands();
        for(Hand hand: hands){
            if(hand.currentHand() == this.cardGameConfig.getWinCondition())
                return true;
        }
        return false;
    }

    @Override
    public boolean isGameComplete(int playerIndex) {
        List<Hand> hands = this.blackJackPlayers.get(playerIndex).getHands();
        for(Hand hand: hands){
            if(hand.currentHand() == this.cardGameConfig.getWinCondition())
                return true;
            if(hand.currentHand() > this.blackJackPlayers.get(0).getHands().get(0).currentHand())
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
        initializeGame(scanner);
        while(true){ // new game
            //Initialize the game with all the details
            dealAndBet(scanner);
            int currentBet = blackJackPlayers.get(1).getHands().get(0).getBet();
            int playerIndex = 0;
            while(isRemainingPlayers()){
                BlackJackPlayer currentPlayer = blackJackPlayers.get(0); //Todo: Use this instead of cardPlayers.get(playerIndex)
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
                List<Card> cards = blackJackPlayers.get(playerIndex).getHands().get(0).getCards();
                System.out.println(cards.size());
                if(cards.get(0).equals(cards.get(1))){ //Todo: Might need to check if the size of cards is 2
                    System.out.println("Do you want to split?");
                    boolean choice = scanner.nextBoolean(); //Todo: Better get Y/N instead of boolean for better user understanding
                    if(choice)
                        blackJackPlayers.get(playerIndex).split();
                }
                int flag = 0;
                for(int i = 0; i< blackJackPlayers.get(playerIndex).getHands().size(); i++) { // Each hand
                    while (blackJackPlayers.get(playerIndex).getHands().get(i).currentHand() < 21){
                        System.out.println("Current hand value: " + blackJackPlayers.get(playerIndex).getHands().get(i).currentHand());
                        int option = GameFunctions.safeScanIntWithLimit(scanner, "Please enter one of the options:\n1. Hit\n2. Stand\n3. Double", 1, 3);
                        switch (option) {
                            case 1:
                                blackJackPlayers.get(playerIndex).hit(decks, i, false);
                                System.out.println("Current hand value: " + blackJackPlayers.get(playerIndex).getHands().get(i).currentHand());
                                break;
                            case 2:
                                //Todo: Better to set player state as stand
                                flag = 1;
                                break;
                            case 3:
                                //Todo: Call double function directly
                                blackJackPlayers.get(playerIndex).getHands().get(i).setBet(currentBet * 2);
                                blackJackPlayers.get(playerIndex).hit(decks, i, false);
                                System.out.println("Current hand value: " + blackJackPlayers.get(playerIndex).getHands().get(i).currentHand());
                                flag = 1;
                                break;
                        }
                        if (flag == 1)
                            break;
                    }
                    if(blackJackPlayers.get(playerIndex).getHands().get(i).currentHand() > 21){ //Bust
                        System.out.println("Hand " + (i+1) + " bust");
                        blackJackPlayers.get(1).removeMoney(currentBet);
                        System.out.println("Current balance: " + blackJackPlayers.get(1).getMoney());
                        continue;
                    }
                    if(flag == 1){
                        isGameComplete(1);
                    }
                }
                if(flag == 1){
                    //Todo: Separate out as function and
                    System.out.println("Initial dealer hand value: " + blackJackPlayers.get(0).getHands().get(0).currentHand());
                    while(blackJackPlayers.get(0).getHands().get(0).currentHand() <= 17){
                        blackJackPlayers.get(0).hit(decks, false);
                        System.out.println("Dealer hand value: " + blackJackPlayers.get(0).getHands().get(0).currentHand());
                    }
                    int dealerMoney = 0;
                    List<Hand> hands = this.blackJackPlayers.get(0).getHands();
                    for(Hand hand: hands){
                        if(hand.currentHand() > this.blackJackPlayers.get(0).getHands().get(0).currentHand()) {
                            System.out.println("Hand won");
                            blackJackPlayers.get(1).addMoney(currentBet * 2);
                            System.out.println("Current balance: " + blackJackPlayers.get(1).getMoney());
                        }
                        else{
                            dealerMoney += currentBet;
                        }
                    }
                    if(dealerMoney > 0){
                        System.out.println("Dealer won!");
                        blackJackPlayers.get(1).addMoney(dealerMoney);
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

    private void initializeGame(Scanner scanner) {
        blackJackPlayers = new ArrayList<>();
        this.cardGameConfig.setPlayerCount(GameConstants.BLACK_JACK_PLAYERS);
        this.cardGameConfig.setNumberOfDecks(GameFunctions.safeScanInt(scanner,"Please enter the number of decks: "));
        this.cardGameConfig.setWinCondition(GameFunctions.safeScanInt(scanner,"Please enter the game win condition: "));
        initializeDeck();
        blackjackDealer = new BlackjackDealer();
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
}
