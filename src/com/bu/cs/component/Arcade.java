package com.bu.cs.component;

import com.bu.cs.helper.GameFunctions;

import java.util.Scanner;

public class Arcade {

    private Game[] games;

    public Arcade() {
        games = new Game[1];
    }

    public void startArcade() {
        boolean exit = false;
        do {
            System.out.println("Welcome to the Arcade");
            System.out.println("================================");
            System.out.println("Please select the option from the following games: ");
            for (int i = 0; i < games.length; i++) {
                System.out.printf("%d. %s %n",i + 1,games[i].getName());
            }
            System.out.printf("%d. Exit %n", games.length + 1);
            Scanner scanner = new Scanner(System.in);
            int choice = GameFunctions.safeScanInt(scanner,"Enter option: ");
            if (choice > games.length + 1) {
                System.out.println("Invalid input. Please try again.....");
                continue;
            }
            else if(choice == games.length + 1) {
                exit = true;
                continue;
            }
            System.out.println("You have selected " + games[choice - 1].getName());
            games[choice - 1].startGame();
        }while(!exit);
        System.out.println("Thank you for playing at the Arcade :)");
    }
}
