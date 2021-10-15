-----
##Team
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
1. CardGame: Abstract class which is used for both the games
2. CardGameConfig: Configuration for the card game. This includes player count, win condition and number of decks.
3. CardPlayer: Abstract class used for other players
4. Dealer: Class with basic dealer functions

**com.bu.cs.component.cardgame.card**
1. Card: Contains the card definition with suit and card value objects
2. CardValue: Enum Used to define the value of the card
3. Deck: Used to store list of cards
4. Decks: Used to store list of decks
5. Hand: Used to represent the list of cards with players/Dealers
6. Suit: Used to represent the suit of the card

**com.bu.cs.component.cardgame.blackJack**
1. BlackJackDealer: Defines the black jack dealer functions
2. BlackJackPlayer: Defines the black jack player functions
3. BlackJackGame: Defines the black jack game logic

**com.bu.cs.component.cardgame.trianta**
1. TriantaDealer: Defines the Trianta dealer functions
2. TriantaPlayer: Defines the Trianta player functions
3. TriantaGame: Defines the Trianta game logic

**com.bu.cs.helper**
1. GameFunctions: Contains the functions that are used to drive the game. This includes getting player turns, printing the board and checking win conditions.
2. GameConstants: Contains the constants used in the card games. This includes constants for reset colour and BlackJack win condition.

**com.bu.cs.Main**: Main program that runs the Arcade.

###How to play
1. Once extracted run the follow commands in the extracted directory:
    1. cd out/production/ticTacToe
    2. java com.bu.cs.Main
2. Select the game by entering the option number
3. **Black Jack**: This is the orginal black jack
    1. Enter the number of players
    2. Enter the number of decks for the game
    3. Enter the details for each player, including money and bet amount
    4. Cards are dealt and the player can select if he needs to hit,stand,split or double down.
    5. Once the game is over, a summary is shown and a prompt to play a next game or exit is shown
    6. Once all the games are done, a prompt is shown for the game exit
4. **Trianta**: This is a variant of black jack
    1. Enter the number of players
    2. Enter the starting amount for each player
    3. Cards are dealt and the player can select if he needs to hit or stand. 
    4. Once the game is over, a summary is shown and a prompt to play a next game or exit is shown
    5. Once all the games are done, a prompt is shown for the game exit
    6. Once all the games are done a summary of the session is shown and the program exits

###Bonus Features
1. Cards are shown with colored text for diamonds and hearts
2. Black jack can be played with any number of players. Limits are defined in Game constants

###Compile and run
**Extract the zip file into a folder.
All the below commands are run in the extracted directory.
The code is compiled in a separate directory for cleaner builds and easy recreates**
1. mkdir out
2. cd src
3. javac -d ../out/ com/bu/cs/Main.java
4. cd ../out/
5. java com.bu.cs.Main
