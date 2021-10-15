-----
Team
####Name: Vivek Unnikrishnan BUID: U53408550
####Name: Bhagyasri Kota BUID: U63334155
####Name: Sahana Subramanya Kowshik BUID: U43929102

-----

#The Arcade
This is an arcade program which has 2 games.
1. BlackJack: A card game with n players and a dealer.
2. TriantaEna: A card game with 7 or 9 players.

###Classes
**com.bu.cs.component**
1. Arcade: An arcade class which stores all the games to play.
3. Game: Interface that Represents the game as the whole. Contains important functions for any game.
5. Player: Represents the player. Stores the name, playerId and the wins for each player.

**com.bu.cs.component.cardgame**
1. CardGame: 
2. CardGameConfig: Configuration for the card game. This includes player count, win condition and number of decks.
3. CardPlayer
4. Dealer

**com.bu.cs.component.cardgame.card**
1. Card
2. CardValue
3. Deck
4. Decks
5. Hand
6. Suit

**com.bu.cs.component.cardgame.blackJack**
1. BlackJackDealer
2. BlackJackPlayer
3. BlackJackGame

**com.bu.cs.component.cardgame.trianta**
1. TriantaDealer
2. TriantaPlayer
3. TriantaGame

**com.bu.cs.helper**
1. GameFunctions: Contains the functions that are used to drive the game. This includes getting player turns, printing the board and checking win conditions.
2. GameConstants: Contains the constants used in the card games. This includes constants for reset colour and BlackJack win condition.

**com.bu.cs.Main**: Main program that runs the Arcade.

###Board format

Sample 3 x 3 board

|0,0|0,1|0,2|
|---|---|---|
|1,0|1,1|1,2|
|2,0|2,1|2,2|

###How to play
1. Once extracted run the follow commands in the extracted directory:
    1. cd out/production/ticTacToe
    2. java com.bu.cs.Main
2. Select the game by entering the option number
3. **Tic Tac Toe**: This is the orginal 3*3 version with 2 players
    1. Enter name of player 1
    2. Enter name of player 2
    3. For each player enter the corresponding cell number (Based on the board above)
    4. If the move is valid, the turn goes to the next player and the updated board is shown
    5. Once the game is over, a prompt to play a next game or exit is shown
    6. Once all the games are done a summary of the session is shown and the program exits
4. **Custom Tic Tac Toe**: This is the custom tic-tac-toe with any size board, any number of teams and players with CPU players as well
    1. Enter the size of the board (it is a square board)
    2. Enter the number of human players
    3. Enter the number of CPU players
    4. Enter the number of teams (players are auto divided into the team)
    5. Enter the details of the teams
    6. Enter the details of the human players
    7. For each player enter the corresponding cell number (Based on the board above)
    8. If the move is valid, the turn goes to the next player and the updated board is shown
    9. Once the game is over, a prompt to play a next game or exit is shown
    10. Once all the games are done a summary of the session is shown and the program exits
5. **Order and Chaos**: Order and Chaos game with 6*6 board and 2 players
    1. Enter the name of the player 1
    2. Enter the name of the player 2
    3. For each player enter the symbol x or o they want to play
    4. For each player enter the corresponding cell number (Based on the board above)
    5. If the move is valid, the turn goes to the next player and the updated board is shown
    6. Once the game is over, a prompt to play a next game or exit is shown
    7. Once all the games are done a summary of the session is shown and the program exits

###Bonus Features
1. Name of the players are added for more personal prompts
2. Custom Tic tac toe has the following features
    1. Custom board size
    2. Custom number of teams
    3. Can play against CPU players (can even have one on your team)
    4. Current limits for the players,board and teams are being set in GameConstants but it can be changed and recompiled if needed (Maximum is 50 rows/columns and 50 players)

###Compile and run
**Extract the zip file into a folder.
All the below commands are run in the extracted directory.
The code is compiled in a separate directory for cleaner builds and easy recreates**
1. mkdir out
2. cd src
3. javac -d ../out/ com/bu/cs/Main.java
4. cd ../out/
5. java com.bu.cs.Main
