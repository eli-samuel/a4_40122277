// -----------------------------------------------------
// Assignment 4 (Board class)
// Written by: Eli Samuel 40122277
// For COMP 248 Section EC B - December 2, 2019
// -----------------------------------------------------

/* This is the Board class for the LetUsPlay.java class. This class contains 2 constructors: a no-argument constructor
 * which initiates the level to 3 and the size to 4, and a 2-argument constructor which allows the user to set
 * the size and level of their board in the driver class. This class also contains other methods which can be called
 * from other classes. */

public class Board {

    private int[][][] board;                    // 3D array which contains the energy values of the board
    public static final int MIN_LEVEL = 3;      // Minimum level of the board when setting
    public static final int MIN_SIZE = 3;       // Minimum size of the board when setting
    private int level;                          // How many levels the board has
    private int size;                           // Size representing the x and y dimensions of the board

	/**
	* Constructor that sets the level and size of the board to what the program passed in the driver class
    * and calls the createBoard method to create the board
	*/
	public Board(int l, int x) {
        level = l;
        size = x;
        createBoard(l, x);
	}

	/**
	* Constructor that sets the level to 3, the size to 4 and calls the createBoard method to create the board
	*/
	public Board() {
		level = 3;
		size = 4;
        createBoard(level, size);
	}

	/**
	* Returns value of level
	* @return int The max level of the board
	*/
	public int getLevel() {
		return level;
	}

	/**
	* Returns value of size
	* @return int The max dimension of the board (x or y)
	*/
	public int getSize() {
		return size;
	}

    /**
	* Creates the 3D board array using the passed dimensions
    * @param level The level of the board
    * @param size The size of the board
	* @return void
	*/
    private void createBoard(int level, int size) {
        board = new int[level][size][size];

        for (int i=0; i<board.length; i++) {
            for (int j=0; j<board[i].length; j++) {
                for (int k=0; k<board[i][j].length; k++) {
                    if ((i+j+k)%3 == 0 && i+j+k != 0) board[i][j][k] = -3;          // Checks to see if the sum of level, x, and y is a multiple of 3 and assigns -3 to it if so
                    else if ((i+j+k)%5 == 0 && i+j+k != 0) board[i][j][k] = -2;     // Checks to see if the sum of level, x, and y is a multiple of 5 and assigns -2 to it if so
                    else if ((i+j+k)%7 == 0 && i+j+k != 0) board[i][j][k] = 2;      // Checks to see if the sum of level, x, and y is a multiple of 7 and assigns 2 to it if so
                    else board[i][j][k] = 0;                                        // Assigns 0 to any sum that does not pass the tests (first square included)
                }
            }
        }
    }

    /**
    * @param l The level of the board
    * @param x The x-coordinate of the board
    * @param y The y-coordinate of the board
	* @return int The energy adjustment value stored in that location of the board
	*/
    public int getEnergyAdj(int l, int x, int y) {
        return board[l][x][y];
    }

	/**
	* Create string representation of the board for printing
	* @return String The energy adjustment values for each board at each level
	*/
    @Override
	public String toString() {

        String s = "";  // Variable in which everything will be concatenated to so that it can be returned and displayed

        for (int i=0; i<level; i++) {
            s = s.concat("\nLevel " + i + "\n-----------\n");
            for (int j=0; j<size; j++) {
                for (int k=0; k<size; k++) {
                    s = s.concat(String.format("%5d", board[i][j][k]));
                }
                s = s.concat("\n");
            }
        }

		return s;
	}
}
