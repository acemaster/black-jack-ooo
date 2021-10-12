
# BlackJack
TBD
### Classes needed:
1. CardGame
2. BlackJack
3. TriEtna
4. CardGameConfig
5. Dealer
6. CardGamePlayer
7. BlackJackPlayer
8. Dealer
### Functions required
1. deal() -> Dealer class cards would be given to each person face up and 2 cards to dealer as well
2. hit() -> BlackJackPlayer, get new random card
3. stand() -> Stop getting cards and check with dealer
4. split() -> Convert to 2 hands only if they have 2 cards of same rank
5. doubleUp() -> Double the bet, single hit and stand
6. endGame() -> Dealer function, once all players stand it can be called.
7. cashout() -> Player wants to quit the game and cashout
### Compile and run
**Extract the zip file into a folder.
All the below commands are run in the extracted directory.
The code is compiled in a separate directory for cleaner builds and easy recreates**
1. mkdir out
2. cd src
3. javac -d ../out/ com/bu/cs/Main.java
4. cd ../out/
5. java com.bu.cs.Main

