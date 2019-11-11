// Final assignment

import java.util.Scanner;

public class LetUsPlay {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Board board;
        Player p1;
        Player p2;
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

        System.out.println(p1);
        System.out.println(p2);


    }
}
