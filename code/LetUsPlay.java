// -----------------------------------------------------
// Assignment 4 (LetUsPlay class)
// Written by: Eli Samuel 40122277
// For COMP 248 Section EC B - December 2, 2019
// -----------------------------------------------------

/* This program allows two users to play a game via the console. The program has 4 classes: a class to create a 3D board,
 * a class to create players with names, energy, and location, and a class to roll two die. The game works by the players
 * taking turns to roll dice and move the amount of squares rolled. There are also challenges where one player lands on
 * the same square as the other player, in which case there is a possible challenge to take their place. The players also
 * gain or lose energy depending on how they move. If the player is at the second to last position, they are stuck, so
 * their next roll will take them backwards instead. The game ends when a player reaches the last square. */


import java.util.Scanner;
import java.util.Random;

public class LetUsPlay {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);             // Scanner to read user input
        Board board;                                        // Declares a Board object to later initialize
        Player[] player = new Player[3];                    // Creates a 3-long array of Player objects
        Dice dice = new Dice();                             // Declares dice and initialize them with their constructor
        Random rand = new Random();                         // Random number variable
        String name1, name2;                                // Names of the players
        int userBoard = 0;                                   // If the player wants to use the default board or build their own
        int numLevels = 3, numSize = 4;                     // Level and size variables for the board (set to default board dimensions)

        // Welcome banner
        System.out.println("\n* * * * * * * * * * * * * * * * * * * * * * * * *\n*\t\t\t\t\t\t*");
        System.out.println("*\tWelcome to the warrior game!\t\t*");
        System.out.println("*\t\t\t\t\t\t*\n* * * * * * * * * * * * * * * * * * * * * * * * *\n");

        // Asks the user whether they want to use the default board size or build their own
        System.out.print("The default game board has 3 levels and each level has a 4x4 board."
        + "\nYou can use this default board size or change the size."
        + "\n\t\t0 to use the default board size"
        + "\n\t\t-1 to enter your own board size"
        + "\n→ What do you want to do? ");

        userBoard = input.nextInt();    // User answer

        // Makes sure that the user inputs 0 or -1
        while (true) {
            // initializes the board to the default level and size
            if (userBoard == 0) {
                board = new Board();
                break;
            }
            else if (userBoard == -1) {
                System.out.print("How many levels would you like? (minimum size 3, max 10) ");  // Asks the user how many levels they want
                numLevels = input.nextInt();

                // Makes sure the user inputs a number between 3 and 10 inclusive, if not, calls the legalChoice method to ask the user to input again
                while (numLevels > 10 || numLevels < Board.MIN_LEVEL) numLevels = legalChoice(numLevels, input);

                System.out.print("What size do you want the nxn boards on each level to be? (minimum size 3x3, max 10x10) "
                + "\n→ enter the value of n: "); // Asks the user the size they want
                numSize = input.nextInt();

                // Makes sure the user inputs a number between 3 and 10 inclusive, if not, calls the legalChoice method to ask the user to input again
                while (numSize > 10 || numSize < Board.MIN_SIZE) numSize = legalChoice(numSize, input);

                // initializes the board to the level and size that the player asked for
                board = new Board(numLevels, numSize);
                break;
            }
            // Calls the legalChoice method to ask the user to input again
            else userBoard = legalChoice(userBoard, input);
        }

        System.out.println("\nYour " + numLevels + "D board has been set up and looks like this:\n" + board); // Prints the board

        System.out.print("What is player 1's name? (one word only) "); // Asks for player 1's name
        player[0] = new Player(input.next());

        System.out.print("What is player 2's name? (one word only) "); // Asks for player 2's name
        player[1] = new Player(input.next());

        System.out.print("\nThe game has started. ");

        // Generates a random integer and sets that index player to go first
        int goesFirst = rand.nextInt(2)+1;
        if (goesFirst == 1) {
            player[2] = new Player(player[0]);
            player[0] = new Player(player[1]);
            player[1] = new Player(player[2]);
        }
        System.out.println(player[0].getName() + " goes first.");
        System.out.println("==========================");

        // LET THE GAMES BEGIN
        int sumOfDice = 0, roundNum = 1;  // Holds the sum of the dice and the round number respectively
        boolean gameStatus = true;                      // If the game is in progress or not
        boolean move = true;                            // If a player can move or not

        // While loop containing the game, only ends when the game is no longer in progress
        while (gameStatus) {
            sumOfDice = 0; // Resets the sum of the dice to 0
            System.out.println("\nIt is " + player[0].getName() + "\'s turn:");

            // Checks to see if the player has no energy
            if (player[0].getEnergy() <= 0) {
                for (int i=0; i<3; i++) { // Rolls the dice 3x so that the player can recover energy if they roll doubles
                    sumOfDice = dice.rollDice();
                    System.out.println("\t" + player[0].getName() + " you rolled: " + dice);
                    if (dice.isDouble()) { // Checks to see if the player rolled doubles
                        player[0].setEnergy(player[0].getEnergy()+2); // Adds 2 to the player's energy
                        System.out.println("\tCongratulations, you rolled double " + (sumOfDice/2) + "s. Your energy went up by 2 units");
                    }
                }
            }
            // Now sees if the player has energy, then they move
            if (player[0].getEnergy() > 0) {
                sumOfDice = dice.rollDice(); // Rolls the dice
                System.out.println("\n\t" + player[0].getName() + " you rolled: " + dice);

                if (dice.isDouble()) { // Checks to see if the player rolled doubles
                    player[0].setEnergy(player[0].getEnergy()+2); // Adds 2 to the player's energy
                    System.out.println("\tCongratulations, you rolled double " + (sumOfDice/2) + "s. Your energy went up by 2 units");
                }

                int newX=0, newY=0, newLevel=player[0].getLevel(); // The possible new x, y, and level positions of the player

                // If player is at the second to last square of the last level
                if (player[0].getX() == board.getSize()-1 && player[0].getY() == board.getSize()-2 && player[0].getLevel() == board.getLevel()-1) {
                    System.out.println("\tSince you are at the second to last square, you will be moved backwards on this roll.");
                    newX = player[0].getX() - (sumOfDice/board.getSize()); // Calculates the new x
                    newY = player[0].getY() - (sumOfDice%board.getSize()); // Calculates the new y
                    if (newY < 0) { // Makes sure the player's possible y position is not off the board
                        newY = board.getSize() + newY; // Calculates the new y
                        newX--; // Calculates the new x
                    }
                }
                else {
                    newX = (sumOfDice/board.getSize()) + player[0].getX(); // Calculates the new x
                    newY = (sumOfDice%board.getSize()) + player[0].getY(); // Calculates the new y
                }

                move = true; // Allows the player to move

                // Makes sure that the possible x and y values are on the board
                while (newX >= board.getSize() || newY >= board.getSize()) {
                    // If x is off the board
                    if (newX >= board.getSize() && newY < board.getSize() && player[0].getLevel() < numLevels-1) {
                        newX %= board.getSize(); // Calculates the new x
                        newLevel = player[0].getLevel()+1;  // Calculates the new level
                    }
                    // If both x and y are off board
                    else if (newX >= board.getSize() && newY >= board.getSize() && player[0].getLevel() < numLevels) {
                        newX += newY/board.getSize(); // Calculates the new x
                        newY %= board.getSize(); // Calculates the new y
                    }
                    // If y is off the board
                    else if (newX < board.getSize() && newY >= board.getSize() && player[0].getLevel() < numLevels) {
                        newX += newY/board.getSize(); // Calculates the new x
                        newY %= board.getSize(); // Calculates the new y
                    }
                    // If both x and y are off the board and the player is at the max level
                    else if ((newX >= board.getSize() && newY < board.getSize()) && player[0].getLevel() >= numLevels-1) {
                        System.out.println("\tSorry, but you need to stay where you are. That throw takes you off the board. You lose 2 units of energy.");
                        player[0].setEnergy(player[0].getEnergy()-2); // Decreases energy by 2
                        move = false;                                 // Stops the player from moving
                        break;                                        // Breaks out of the while loop
                    }

                }

                // If the player can move
                if (move) {
                    Player potentialLocation = new Player(player[0]);   // Potential player location, as the player does not definitely move to the values calculated previously
                    changePos(potentialLocation, newX, newY, newLevel); // Sets the position to the locations caluclated using the changePos method
                    // If the player will potentially land on the square of the other player
                    if (potentialLocation.equals(player[1])) {
                        System.out.print("Player " + player[1].getName() + " is at your new location."
                                            + "\n\t\t0 to challenge and risk losing 50% of your energy units if"
                                            + " you lose \n\t\t  OR move to the new location and get 50% of " + player[1].getName()
                                            + "\'s energy units."
                                            + "\n\n\t\t1 to move down one level to the new location or move to (0, 0) if at"
                                            + "\n\t\t  level 0 and lose 2 energy units. \nWhat do you want to do? ");

                        int sameLocation = input.nextInt();
                        // Makes sure that the user inputs 0 or 1
                        while (true) {

                            // If input is 1
                            if (sameLocation == 1) {
                                // If the player isn't at the first level decrement the level and put them at the new x and y
                                if (player[0].getLevel() != 0) {
                                    changePos(player[0], newX, newY, player[0].getLevel()-1);
                                    player[0].setEnergy(player[0].getEnergy()+board.getEnergyAdj(player[0].getLevel(), player[0].getX(), player[0].getY())-2); // Subtract 2 from the energy
                                }
                                // If the player is at the first level, send them to (0, 0)
                                else {
                                    changePos(player[0], 0, 0, 0);
                                    player[0].setEnergy(player[0].getEnergy()-2); // Subtract 2 from the energy
                                }
                                break; // Breaks out of the while loop
                            }
                            // If input is 0
                            else if (sameLocation == 0) {
                                int win = rand.nextInt(9)+1; // Random number between 1 and 10

                                if (win < 6) { // The player loses the challenge
                                    System.out.println("\n\tSorry, but you lost the challenge. Your energy is halved and you stay at the same spot.");
                                    player[0].setEnergy(player[0].getEnergy()/2); // The challenging player's energy is halved
                                }
                                else  { // The player wins the challenge
                                    System.out.println("\n\tCongratulations! You won the challenge. You get half of " + player[1].getName() + "\'s energy and switch spots with them.");

                                    player[1].moveTo(player[0]); // Puts the non-turn player at the x, y, and level of the challenging player
                                    changePos(player[0], newX, newY, newLevel); // Sets the challenging player's location

                                    player[0].setEnergy(player[0].getEnergy() + (player[1].getEnergy()/2)); // The challenging player gets half of the non-turn player
                                    player[1].setEnergy(player[1].getEnergy()/2);                           // The non-turn player's energy is halved
                                }
                                break; // Breaks out of the while loop
                            }
                            else sameLocation = legalChoice(sameLocation, input); // Asks the user to give a new location
                        }
                    }
                    // If the players will not be at the same location, set the player to whatever was caluclated
                    else changePos(player[0], newX, newY, newLevel);

                    // Adjust the energy of the player based on what square they landed on
                    player[0].setEnergy(player[0].getEnergy() + board.getEnergyAdj(player[0].getLevel(), player[0].getX(), player[0].getY()));
                    System.out.println("\tYour energy is adjusted by " + board.getEnergyAdj(player[0].getLevel(), player[0].getX(), player[0].getY()) + " for landing at ("
                                        + player[0].getX() + ", " + player[0].getY() + ") at level " + player[0].getLevel());
                }

                // Checks to see if the player is at the last level, x, and y of the board (victory)
                if (player[0].won(board)) {
                    System.out.println("\n\nPlayer " + player[0].getName() + " wins!\n");
                    gameStatus = false; // Sets the game to be over
                    continue;           // Skips the rest of the code
                }
            }

            // Changes the player's turn
            player[2] = new Player(player[0]);
            player[0] = new Player(player[1]);
            player[1] = new Player(player[2]);

            roundNum++; // Sets the round number

            // After every second round (after both players have had a turn), display the toString() methods of the players
            if (roundNum%2 == 1) {
                System.out.print("\nAt the end of this round:\n\t" + player[0] + "\n\t" + player[1] + "\nPress any key to continue to the next round... ");
                String s = input.next();
            }
        }

        System.out.println("Thank you for playing the best game ever!");
    }

    /**
	* Changes the position of the player
    * @param p The passed Player object
    * @param x The passed x position
    * @param y The passed y position
    * @param level The passed level
	* @return void
	*/
    public static void changePos(Player p, int x, int y, int level) {
        p.setX(x);
        p.setY(y);
        p.setLevel(level);
    }

    /**
	* Changes the position of the player
    * @param i The old user input
    * @param input Scanner to read player input
	* @return int The new user input
	*/
    public static int legalChoice(int i, Scanner input) {
        System.out.print("Sorry, but " + i + " is not a legal choice. Try again: ");
        return input.nextInt();
    }

}
