// Final assignment

import java.util.Scanner;
import java.util.Random;

public class LetUsPlay {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Board board;
        Player p1, p2, playerTurn = new Player(), playerNotTurn = new Player(), extra = new Player();
        Dice dice = new Dice();
        Random rand = new Random();
        String name1, name2;
        int userBoard = 0, numLevels = 3, numSize = 4;

        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * *");
        System.out.println("\tWelcome to banana game");
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * *");

        System.out.print("The default game board has 3 levels and each level has a 4x4 board."
        + "\nYou can use this default board size or change the size."
        + "\n\t\t0 to use the default board size"
        + "\n\t\t-1 to enter your own board size"
        + "\n→ What do you want to do? ");

        userBoard = input.nextInt();
        while (true) {
            if (userBoard == 0) {
                board = new Board();
                break;
            }
            else if (userBoard == -1) {
                System.out.print("How many levels would you like? (minimum size 3, max 10) ");
                numLevels = input.nextInt();

                while (numLevels > 10 || numLevels < Board.MIN_LEVEL) numLevels = legalChoice(numLevels, input);

                System.out.print("What size do you want the nxn boards on each level to be? (minimum size 3x3, max 10x10) "
                + "\n→ enter the value of n: ");
                numSize = input.nextInt();

                while (numSize > 10 || numSize < Board.MIN_SIZE) numSize = legalChoice(numSize, input);

                board = new Board(numLevels, numSize);
                break;
            }
            else userBoard = legalChoice(userBoard, input);
        }

        System.out.println("\nYour " + numLevels + "D board has been set up and looks like this:\n" + board);

        System.out.print("What is player 1's name? (one word only) ");
        playerTurn = new Player(input.next());

        System.out.print("What is player 2's name? (one word only) ");
        playerNotTurn = new Player(input.next());

        System.out.print("\nThe game has started. ");

        int goesFirst = rand.nextInt(2)+1;
        if (goesFirst == 1) {
            extra = new Player(playerTurn);
            playerTurn = new Player(playerNotTurn);
            playerNotTurn = new Player(extra);
        }
        System.out.println(playerTurn.getName() + " goes first.");


        System.out.println("==========================");

        // LET THE GAMES BEGIN
        int sumOfDice = 0, newLevel = 0, roundNum = 1;
        boolean gameStatus = true;

        boolean move = true;

        while (gameStatus) { // Giant while loop containing the game
            sumOfDice = 0;
            System.out.println("\nIt is " + playerTurn.getName() + "\'s turn:");

            if (playerTurn.getEnergy() <= 0) {
                for (int i=0; i<3; i++) {
                    sumOfDice = dice.rollDice();
                    System.out.println("\t" + playerTurn.getName() + " you rolled: " + dice);
                    if (dice.isDouble()) {
                        playerTurn.setEnergy(playerTurn.getEnergy()+2);
                        System.out.println("\tCongratulations, you rolled double " + (sumOfDice/2) + "s. Your energy went up by 2 units");
                    }
                }
            }
            // Player moves
            else if (playerTurn.getEnergy() > 0) {
                sumOfDice = dice.rollDice();
                System.out.println("\n" + playerTurn.getName() + " you rolled: " + dice);

                if (dice.isDouble()) {
                    playerTurn.setEnergy(playerTurn.getEnergy()+2);
                    System.out.println("\tCongratulations, you rolled double " + (sumOfDice/2) + "s. Your energy went up by 2 units");
                }

                int newX = (sumOfDice/board.getSize()) + playerTurn.getX();
                int newY = (sumOfDice%board.getSize()) + playerTurn.getY();
                move = true;

                while (newX >= board.getSize() || newY >= board.getSize()) {
                    // if x is off the board
                    if (newX >= board.getSize() && newY < board.getSize() && playerTurn.getLevel() < numLevels-1) {
                        newX %= board.getSize();
                        newLevel = playerTurn.getLevel()+1;
                    }
                    // if both off board
                    else if (newX >= board.getSize() && newY >= board.getSize() && playerTurn.getLevel() < numLevels) {
                        newX += newY/board.getSize();
                        newY %= board.getSize();
                    }
                    // if y off the board
                    else if (newX < board.getSize() && newY >= board.getSize() && playerTurn.getLevel() < numLevels) {
                        newX += newY/board.getSize();
                        newY %= board.getSize();
                    }
                    else if ((newX >= board.getSize() && newY >= board.getSize()) || (newX >= board.getSize() && newY < board.getSize()) && playerTurn.getLevel() >= numLevels-1) {
                        System.out.println("\tSorry, but you need to stay where you are. That throw takes you off the board. You lose 2 units of energy.");
                        playerTurn.setEnergy(playerTurn.getEnergy()-2);
                        move = false;
                        break;
                    }
                }

                Player potentialLocation = new Player(playerTurn);
                changePos(potentialLocation, newX, newY, newLevel);

                if (move == true) {
                    // If they land on the same square
                    if (potentialLocation.equals(playerNotTurn)) {
                        System.out.print("Player " + playerNotTurn.getName() + " is at your new location."
                                            + "\n\t\t0 to challenge and risk losing 50% of your energy units if"
                                            + " you lose \n\t\t  OR move to the new location and get 50% of " + playerNotTurn.getName()
                                            + "\'s energy units."
                                            + "\n\n\t\t1 to move down one level to the new location or move to (0, 0) if at"
                                            + "\n\t\t  level and lose 2 energy units. \nWhat do you want to do? ");
                        while (true) {
                            int sameLocation = input.nextInt();

                            if (sameLocation == 1) {
                                if (playerTurn.getLevel() != 0) {
                                    changePos(playerTurn, newX, newY, playerTurn.getLevel()-1);
                                    playerTurn.setEnergy(playerTurn.getEnergy()+board.getEnergyAdj(playerTurn.getLevel(), playerTurn.getX(), playerTurn.getY())-2);
                                }
                                else {
                                    changePos(playerTurn, 0, 0, 0);
                                    playerTurn.setEnergy(playerTurn.getEnergy()-2);
                                }
                                break;
                            }
                            else if (sameLocation == 0) {
                                int win = rand.nextInt(9)+1; // number between 1 and 10

                                if (win < 6) { // A loses
                                    System.out.println("\n\tSorry, but you lost the challenge. Your energy is halved and you stay at the same spot.");
                                    playerTurn.setEnergy(playerTurn.getEnergy()/2);
                                }
                                else  { // A wins
                                    System.out.println("\n\tCongratulations! You won the challenge. You get half of " + playerNotTurn.getName() + "\'s energy and switch spots with them.");

                                    changePos(playerNotTurn, playerTurn.getX(), playerTurn.getY(), playerTurn.getLevel());
                                    changePos(playerTurn, newX, newY, newLevel);

                                    playerTurn.setEnergy(playerTurn.getEnergy() + (playerNotTurn.getEnergy()/2));
                                    playerNotTurn.setEnergy(playerNotTurn.getEnergy()/2);
                                }
                                break;
                            }
                            else System.out.print("\nSorry but " + sameLocation + " is not a legal choice. Try again: ");
                        }
                    }
                    else changePos(playerTurn, newX, newY, newLevel);
                    playerTurn.setEnergy(playerTurn.getEnergy() + board.getEnergyAdj(playerTurn.getLevel(), playerTurn.getX(), playerTurn.getY()));
                    System.out.println("\tYour energy is adjusted by " + board.getEnergyAdj(playerTurn.getLevel(), playerTurn.getX(), playerTurn.getY()) + " for landing at ("
                                        + playerTurn.getX() + ", " + playerTurn.getY() + ") at level " + playerTurn.getLevel());
                }

                if (playerTurn.getX() == board.getSize()-1 && playerTurn.getY() == board.getSize()-1 && playerTurn.getLevel() == board.getLevel()-1) {
                    System.out.println("\n");
                    for (int i=0; i<10; i++) {
                        System.out.println("Player " + playerTurn.getName() + " wins!");
                    }
                    System.out.println("\n");
                    gameStatus = false;
                    continue;
                }
            }
            // This could probably be made into a method
            extra = new Player(playerTurn);
            playerTurn = new Player(playerNotTurn);
            playerNotTurn = new Player(extra);

            roundNum++;
            if (roundNum%2 == 1) {
                System.out.print("\nAt the end of this round:\n\t" + playerTurn + "\n\t" + playerNotTurn + "\nPress any key to continue to the next round... ");
                String s = input.next();
            }
        }
    }

    public static void changePos(Player p, int x, int y, int level) {
        p.setX(x);
        p.setY(y);
        p.setLevel(level);
    }

    public static int legalChoice(int i, Scanner input) {
        System.out.print("Sorry, but " + i + " is not a legal choice. Try again: ");
        return input.nextInt();
    }

}
