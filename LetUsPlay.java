// Final assignment

import java.util.Scanner;
import java.util.Random;

public class LetUsPlay {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Board board;
        Player p1, p2, playerTurn = new Player();
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
            System.out.println(p1.getName() + " goes first.");
        }
        else if (goesFirst == 2) {
            playerTurn = new Player(p1);
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

                if (move == true) {
                    playerTurn.setX(newX);
                    playerTurn.setY(newY);
                }
                if (move == false) {
                    playerTurn.setEnergy(playerTurn.getEnergy()-2);
                }
            }

            System.out.println(playerTurn);
            break;
        }
    }
}
