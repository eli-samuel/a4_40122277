public class Board {

    private int[][][] board;
    private static final int MIN_LEVEL = 3;
    private static final int MIN_SIZE = 3;
    private int level;
    private int size;

	/**
	* Board constructor
	*/
	public Board(int l, int x) {
        createBoard(l, x);
	}

	/**
	* empty Board constructor
	*/
	public Board() {
		level = 3;
		size = 4;
        createBoard(level, size);
	}

	/**
	* Returns value of level
	* @return
	*/
	public int getLevel() {
		return level;
	}

	/**
	* Returns value of size
	* @return
	*/
	public int getSize() {
		return size;
	}

    /**
	* Description
    * @param
	* @return
	*/
    private void createBoard(int level, int size) {
        board = new int[level][size][size];

        for (int i=0; i<board.length; i++) {
            for (int j=0; j<board[i].length; j++) {
                for (int k=0; k<board[i][j].length; k++) {
                    if ((i+j+k)%3 == 0 && i+j+k != 0) board[i][j][k] = -3;
                    else if ((i+j+k)%5 == 0 && i+j+k != 0) board[i][j][k] = -2;
                    else if ((i+j+k)%7 == 0 && i+j+k != 0) board[i][j][k] = 2;
                    else board[i][j][k] = 0;
                }
            }
        }
    }

    /**
	* Description
    * @param
	* @return
	*/
    public int getEnergyAdj(int l, int x, int y) {
        return board[l][x][y];
    }

	/**
	* Create string representation of Board for printing
	* @return
	*/
    @Override
	public String toString() {

        String s = "";

        for (int i=0; i<board.length; i++) {
            s = s.concat("\nLevel " + i + "\n-----------\n");
            for (int j=0; j<board[i].length; j++) {
                for (int k=0; k<board[i][j].length; k++) {
                    s = s.concat(board[i][j][k] + "\t");
                }
                s = s.concat("\n");
            }
        }

		return s;
	}
}
