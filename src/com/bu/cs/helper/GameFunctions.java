package com.bu.cs.helper;

import com.bu.cs.component.Game;

import java.util.List;
import java.util.Scanner;

public class GameFunctions {

    /**
     * Get the details of the player and set the desired piece
     * Conditions:
     * Player name should be unique
     * @param game
     * @param scanner
     * @param playerIndex
     */
    public static void getPlayerDetails(Game game, Scanner scanner, int playerIndex, List<String> players) {
        game.getPlayers()[playerIndex].setName(getUniqueInput(scanner,players,String.format("Enter name of player %d: ",playerIndex+1)));
        players.add(game.getPlayers()[playerIndex].getName());
    }

    public static String getUniqueInput(Scanner scanner, List<String> data,String message) {
        boolean validData = false;
        do {
            System.out.print(message);
            String returnData = scanner.next();
            if(data.contains(returnData)) {
                System.out.println("Please enter a unique string....");
            } else {
                return returnData;
            }
        }while (!validData);
        return "";
    }

    /**
     * Check if the player want to play the next game or not
     * @param scanner
     * @return
     */
    public static boolean isNextGame(Scanner scanner) {
        System.out.println("Do you want to play another round? (Y/N): ");
        String nextGame = scanner.next();
        if (nextGame.equalsIgnoreCase("y")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the next input parameter while having safety checks
     * @param scanner
     * @param message
     * @return
     */
    public static int safeScanInt(Scanner scanner,String message) {
        boolean invalidInput = false;
        int retValue = 0;
        do {
            try {
                System.out.print(message);
                retValue = scanner.nextInt();
                invalidInput = false;
            }catch (Exception e) {
                System.out.println("Invalid input. Please try again.....");
                scanner.next();
                invalidInput = true;
            }
        }while (invalidInput);
        return retValue;
    }

    /**
     * Get the next input parameter while having safety checks
     * @param scanner
     * @param message
     * @return
     */
    public static String safeScanString(Scanner scanner,String message) {
        boolean invalidInput = false;
        String retValue = "";
        do {
            try {
                System.out.print(message);
                retValue = scanner.nextLine();
                invalidInput = false;
            }catch (Exception e) {
                System.out.println("Invalid input. Please try again.....");
                scanner.next();
                invalidInput = true;
            }
        }while (invalidInput);
        return retValue;
    }

    /**
     * Get the next input int parameter while having safety checks and with limits
     * @param scanner
     * @param message
     * @return
     */
    public static int safeScanIntWithLimit(Scanner scanner,String message,int lowerLimit,int upperLimit) {
        boolean invalidInput = false;
        int retValue = 0;
        do {
            try {
                System.out.print(message);
                retValue = scanner.nextInt();
                if(retValue <lowerLimit || retValue>upperLimit) {
                    System.out.println("Input not within limits. Please try again.....");
                    invalidInput = true;
                    continue;
                }
                invalidInput = false;
            }catch (Exception e) {
                System.out.println("Invalid input. Please try again.....");
                scanner.next();
                invalidInput = true;
            }
        }while (invalidInput);
        return retValue;
    }
}
