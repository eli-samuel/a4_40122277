import java.util.Scanner;

public class LetUsPlay {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Board board;

        System.out.println("*******************************");
        System.out.println("Welcome to banana game");
        System.out.println("*******************************");

        System.out.print("The default game board has 3 levels and each level has a 4x4 board."
                            + "\nYou can use this default board size or change the size."
                            + "\n\t\t0 to use the default board size"
                            + "\n\t\t-1 to enter your own board size"
                            + "\n→ What do you want to do? ");
        int userBoard = input.nextInt();

        if (userBoard == 0) {
            board = new Board();
        }
        else if (userBoard == -1) {
            int numLevels, numSize;
            System.out.print("How many levels would you like? (minimum size 3, max 10) ");
            numLevels = input.nextInt();

            while (numLevels > 10 || numLevels < 3) {
                System.out.print("\nSorry but " + numLevels + " is not a legal choice. Try again: ");
                numLevels = input.nextInt();
            }

            System.out.print("What size do you want the nxn boards on each level to be? (minimum size 4x4, max 10x10) "
                                    + "\n→ enter the value of n: ");
            numSize = input.nextInt();

            while (numSize > 10 || numSize < 4) {
                System.out.print("\nSorry but " + numSize + " is not a legal choice. Try again: ");
                numSize = input.nextInt();
            }

            board = new Board(numLevels, numSize);
        }
        else {
            board = new Board();
        }

        System.out.println(board);

    }
}
