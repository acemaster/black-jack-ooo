package com.bu.cs.component.cardgame.blackJack;

import com.bu.cs.component.Player;
import com.bu.cs.component.cardgame.CardGame;
import com.bu.cs.component.cardgame.CardPlayer;
import com.bu.cs.component.cardgame.Dealer;
import com.bu.cs.component.cardgame.card.Card;
import com.bu.cs.component.cardgame.card.CardValue;
import com.bu.cs.component.cardgame.card.Hand;
import com.bu.cs.component.cardgame.exception.NoDeckException;
import com.bu.cs.helper.GameFunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlackJackGame extends CardGame {
    List<BlackJackPlayer> cardPlayers;
    @Override
    public String getName() {
        return "BlackJack";
    }

    @Override
    public void summary() {

    }

    public  boolean isBlackJack(int playerIndex){
        List<Hand> hands = this.cardPlayers.get(playerIndex).getHands();
        for(Hand hand: hands){
            if(hand.currentHand() == this.cardGameConfig.getWinCondition())
                return true;
        }
        return false;
    }

    @Override
    public boolean isGameComplete(int playerIndex) {
        //Todo: Replace with isBlackJack
        List<Hand> hands = this.cardPlayers.get(playerIndex).getHands();
        for(Hand hand: hands){
            if(hand.currentHand() == this.cardGameConfig.getWinCondition())
                return true;
            if(hand.currentHand() > this.cardPlayers.get(0).getHands().get(0).currentHand())
                return true;
        }
        return false;
    }

    public void dealInitialCards(){
        //Todo: Use dealer to get the cards
        cardPlayers.get(0).hit(decks, false);
        cardPlayers.get(0).hit(decks, false);
        for(int i=1;i<cardPlayers.get(1).getHands().size();i++) {
            for (int j = 0; j < 2; j++) {
                cardPlayers.get(1).hit(decks, i,false);
            }
        }
    }

    @Override
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the game of BlackJack!!");
        while(true){ // new game
            cardPlayers = new ArrayList<>();
            this.cardGameConfig.setPlayerCount(1);
//            this.cardGameConfig.setPlayerCount(GameFunctions.safeScanInt(scanner,"Please enter the number of players: "));
            this.cardGameConfig.setNumberOfDecks(GameFunctions.safeScanInt(scanner,"Please enter the number of decks: "));
            this.cardGameConfig.setWinCondition(GameFunctions.safeScanInt(scanner,"Please enter the game win condition: "));
            initializeDeck();
            cardPlayers.add(new BlackJackPlayer());
            for(int i=0;i<this.cardGameConfig.getPlayerCount();i++){
                scanner = new Scanner(System.in);
                BlackJackPlayer player = new BlackJackPlayer();
                player.setName(GameFunctions.safeScanString(scanner,"Please enter the name for player " + (i+1) + ": "));
                player.setPlayerId((i+1));
                System.out.println(player.getHands().size());
                for(Hand hand: player.getHands()){
                    hand.setBet(GameFunctions.safeScanInt(scanner,"Please place initial bet: "));
                }
                cardPlayers.add(player);
            }
//            dealInitialCards();
//            cardPlayers.get(0).getHands().get(0).display();
//            cardPlayers.get(1).getHands().get(0).display();

            while(true){// Until player has no money
                dealInitialCards();
                cardPlayers.get(0).getHands().get(0).display();
                cardPlayers.get(1).getHands().get(0).display();
                if (this.isBlackJack(1)) {
                        System.out.println("A natural Black Jack");
                        break;
                }
                List<Card> cards = cardPlayers.get(1).getHands().get(0).getCards();
                if(cards.get(0).equals(cards.get(1))){
                    System.out.println("Do you want to split?");
                    boolean choice = scanner.nextBoolean();
                    if(choice == true)
                        cardPlayers.get(1).split();
                }
                int flag = 0;
                for(int i=0;i<cardPlayers.get(1).getHands().size();i++) { // Each round
                    while (cardPlayers.get(1).getHands().get(i).currentHand() > 0){
                        System.out.println("Current hand value: " + cardPlayers.get(1).getHands().get(i).currentHand());
                        int option = GameFunctions.safeScanIntWithLimit(scanner, "Please enter one of the options:\n1. hit()\n2. Stand()\n3. Double", 1, 3);
                        switch (option) {
                            case 1:
                                cardPlayers.get(1).hit(decks, i, false);
                                break;
                            case 2:
                                flag = 1;
                                break;
                            case 3:
                                cardPlayers.get(1).getHands().get(i).setBet(cardPlayers.get(1).getHands().get(i).getBet() * 2);
                                cardPlayers.get(1).hit(decks, i, false);
                                flag = 1;
                                break;
                        }
                        if (flag == 1)
                            break;
                    }
                    if(flag == 1){
                        isGameComplete(1);
                    }
                }
            }
//            while (true) {
//                for (int i = 1; i < cardPlayers.size(); i++) {
//                    if (this.isBlackJack(i)) {
//                        cardPlayers.get(i).incrementWins();
//
//                        //todo add logic to add and remove money
//                        cardPlayers.remove(i);
//                        i--;
//                        break;
//                    }
//                }
//                if(cardPlayers.size() == 1){
//                    while(cardPlayers.get(0).getHands().get(0).currentHand() <= 17){
//                        cardPlayers.get(0).hit(decks, false);
//                    }
//                    if(isGameComplete(0)){
//                        System.out.println("Dealer won!");
//                        //todo add logic to add and remove money
//                        break;
//                    }
//                }
//            }

        }
    }

    @Override
    public Player[] getPlayers() {
        return new Player[0];
    }
}
