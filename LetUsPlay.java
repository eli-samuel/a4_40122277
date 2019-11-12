// Final assignment

import java.util.Scanner;
import java.util.Random;

public class LetUsPlay {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Board board;
        Player p1, p2, playerTurn = new Player(), playerNotTurn = new Player();
        Dice dice = new Dice();
        Random rand = new Random();
        String name1, name2;
        int userBoard = 0, numLevels = 3, numSize = 4;

        System.out.println("*******************************");
        System.out.println("Welcome to banana game");
        System.out.println("*******************************");

        System.out.print("The default game board has 3 levels and each level has a 4x4 board."
        + "\nYou can use this default board size or change the size."
        + "\n\t\t0 to use the default board size"
        + "\n\t\t-1 to enter your own board size"
        + "\n→ What do you want to do? ");

        while (true) {
            userBoard = input.nextInt();
            if (userBoard == 0) {
                board = new Board();
                break;
            }
            else if (userBoard == -1) {
                System.out.print("How many levels would you like? (minimum size 3, max 10) ");
                numLevels = input.nextInt();

                while (numLevels > 10 || numLevels < 3) {
                    System.out.print("Sorry but " + numLevels + " is not a legal choice. Try again: ");
                    numLevels = input.nextInt();
                }

                System.out.print("What size do you want the nxn boards on each level to be? (minimum size 4x4, max 10x10) "
                + "\n→ enter the value of n: ");
                numSize = input.nextInt();

                while (numSize > 10 || numSize < 4) {
                    System.out.print("Sorry but " + numSize + " is not a legal choice. Try again: ");
                    numSize = input.nextInt();
                }

                board = new Board(numLevels, numSize);
                break;
            }
            else {
                System.out.print("Sorry, but " + userBoard + " is not a legal choice. Try again: ");
            }
        }

        System.out.println("\nYour " + numLevels + "D board has been set up and looks like this:");
        System.out.println(board);

        System.out.print("What is player 1's name? (one word only) ");
        name1 = input.next();

        System.out.print("What is player 2's name? (one word only) ");
        name2 = input.next();

        p1 = new Player(name1);
        p2 = new Player(name2);

        System.out.print("\nThe game has started. ");

        int goesFirst = rand.nextInt(2)+1;
        if (goesFirst == 1) {
            playerTurn = new Player(p1);
            playerNotTurn = new Player(p2);
            System.out.println(p1.getName() + " goes first.");
        }
        else if (goesFirst == 2) {
            playerTurn = new Player(p2);
            playerNotTurn = new Player(p1);
            System.out.println(p2.getName() + " goes first.");
        }

        // LET THE GAMES BEGIN

        boolean gameStatus = true;
        int sumOfDice = 0;
        while (gameStatus) { // Giant while loop containing the game
            if (playerTurn.getEnergy() <= 0) {
                for (int i=0; i<3; i++) {
                    dice.rollDice();
                    if (dice.isDouble()) {
                        playerTurn.setEnergy(playerTurn.getEnergy()+2);
                    }
                }
            }

            // Player moves
            if (playerTurn.getEnergy() > 0) {
                sumOfDice = dice.rollDice();

                System.out.println(sumOfDice);

                int newX = (sumOfDice/board.getSize()) + playerTurn.getX();
                int newY = (sumOfDice%board.getSize()) + playerTurn.getY();
                boolean move = true;

                newX = newY = 3;
                playerTurn.setLevel(2);

                while (newX >= board.getSize() || newY >= board.getSize()) {
                    // if x is off the board
                    if (newX >= board.getSize() && newY < board.getSize() && playerTurn.getLevel() < numLevels) {
                        newX %= board.getSize();
                        playerTurn.setLevel(playerTurn.getLevel()+1);
                    }
                    // if both off board
                    else if (newX >= board.getSize() && newY >= board.getSize() && playerTurn.getLevel() < numLevels) {
                        newX += newY/board.getSize();
                        newY %= board.getSize();
                    }
                    else if (newX >= board.getSize() && newY >= board.getSize() && playerTurn.getLevel() >= numLevels) {
                        move = false;
                    }
                }

                Player potentialLocation = new Player(playerTurn);

                if (move == true) {
                    // If they land on the same square
                    if (potentialLocation.equals(playerNotTurn)) {
                        System.out.print("Player " + playerNotTurn.getName() + " is at your new location."
                                            + "\n\t\t0 to challenge and risk loosing 50% of your energy units if"
                                            + " you lose \n\t\t  OR move to the new location and get 50% of " + playerNotTurn.getName()
                                            + "\'s energy units."
                                            + "\n\n\t\t1 to move down one level to the new location or move to (0, 0) if at"
                                            + "\n\t\t  level and lose 2 energy units. \nWhat do you want to do? ");
                        while (true) {
                            int sameLocation = input.nextInt();

                            if (sameLocation == 1) {
                                if (playerTurn.getLevel() != 0) {
                                    playerTurn.setX(newX);
                                    playerTurn.setY(newY);
                                    playerTurn.setLevel(playerTurn.getLevel()-1);
                                    playerTurn.setEnergy(playerTurn.getEnergy()+board.getEnergyAdj(playerTurn.getLevel(), playerTurn.getX(), playerTurn.getY())-2);
                                }
                                else {
                                    playerTurn.setX(0);
                                    playerTurn.setY(0);
                                    playerTurn.setEnergy(playerTurn.getEnergy()-2);
                                }
                                break;
                            }
                            else if (sameLocation == 0) {
                                int win = rand.nextInt(9)+1; // number between 1 and 10

                                if (win < 6) { // A loses
                                    playerTurn.setEnergy(playerTurn.getEnergy()/2);
                                }
                                else  { // A wins
                                    playerNotTurn.setX(playerTurn.getX());
                                    playerNotTurn.setY(playerTurn.getY());

                                    playerTurn.setX(newX);
                                    playerTurn.setY(newY);

                                    playerTurn.setEnergy(playerTurn.getEnergy() + (playerNotTurn.getEnergy()/2));
                                    playerNotTurn.setEnergy(playerNotTurn.getEnergy()/2);
                                }
                                break;
                            }
                            else System.out.print("Sorry but " + sameLocation + " is not a legal choice. Try again: ");
                        }
                    }
                    else {
                        playerTurn.setX(newX);
                        playerTurn.setY(newY);
                        playerTurn.setEnergy(playerTurn.getEnergy() + board.getEnergyAdj(playerTurn.getLevel(), playerTurn.getX(), playerTurn.getY()));
                    }
                }
                if (move == false) {
                    playerTurn.setEnergy(playerTurn.getEnergy()-2);
                }

                if (playerTurn.getX() == board.getSize()-1 && playerTurn.getY() == board.getSize()-1 && playerTurn.getLevel() == board.getLevel()-1) {
                    System.out.println("Player " + playerTurn.getName() + " wins!");
                    gameStatus = false;
                }
                else {
                    
                }
            }

            System.out.println(playerTurn);
            break;
        }
    }
}
