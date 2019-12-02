// -----------------------------------------------------
// Assignment 4 (Player class)
// Written by: Eli Samuel 40122277
// For COMP 248 Section EC B - December 2, 2019
// -----------------------------------------------------

/* This is the Player class for the LetUsPlay.java class. This class contains 4 constructors: a no-argument constructor
 * which initiates the player name to an empty string, the level, x, and y to 0, and energy to 10, a 1-argument
 * constructor which allows the user to set their name in the driver class, a 3-argument constructor which allows
 * the user to set the level, x, and y, and a 1-argument constructor which takes a Player object and sets the new
 * player to the same variables as the passed Player. This class also contains other methods which can be called
 * from other classes. */

public class Player {

    private String name;                // Player name
    private int level, x, y, energy;    // Level, x, y, and energy of the player


	/**
	* Constructor that sets the player name to an empty string, the level, x, and y to 0, and energy to 10
	*/
	public Player() {
        name = "";
        level = x = y = 0;
        energy = 10;
	}

	/**
	* Constructor that sets the name of the player to what the program passed in the driver class
	*/
	public Player(String name) {
		setName(name);
        level = x = y = 0;
        energy = 10;
	}

    /**
	* Constructor that sets the level, x, and y of the player to what the program passed in the driver class
    * and assigns the name to an empty string, and energy to 0.
	*/
	public Player(int level, int x, int y) {
		name = "";
        setLevel(level);
        setX(x);
        setY(y);
        energy = 10;
	}

    /**
    * Duplicator constructor that sets the name, level, x, y, and energy of the player to  the same as the
    * Player object that was passed from the driver class
    */
    public Player(Player p) {
        setName(p.name);
        setLevel(p.level);
        setX(p.x);
        setY(p.y);
        setEnergy(p.energy);
    }


	/**
	* Returns value of name
	* @return String The player name
	*/
	public String getName() {
		return name;
	}

	/**
	* Sets new value of name
	* @param name The player name
	*/
	public void setName(String name) {
		this.name = name;
	}

	/**
	* Returns value of level
	* @return int The player level
	*/
	public int getLevel() {
		return level;
	}

	/**
	* Sets new value of level
	* @param level The player level
	*/
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	* Returns value of x
	* @return int The player x position
	*/
	public int getX() {
		return x;
	}

	/**
	* Sets new value of x
	* @param x The player x position
	*/
	public void setX(int x) {
		this.x = x;
	}

	/**
	* Returns value of y
	* @return int The player y position
	*/
	public int getY() {
		return y;
	}

	/**
	* Sets new value of y
	* @param y The player y position
	*/
	public void setY(int y) {
		this.y = y;
	}

	/**
	* Returns value of energy
	* @return int The player energy
	*/
	public int getEnergy() {
		return energy;
	}

	/**
	* Sets new value of energy
	* @param energy The player energy
	*/
	public void setEnergy(int energy) {
        if (energy >= 0) this.energy = energy;
        else this.energy = 0;
	}

    /**
	* Moves the calling Player object to the passed Player's location (level, x, y)
    * @param p The passed Player object
	* @return void
	*/
    public void moveTo(Player p) {
        setLevel(p.level);
        setX(p.x);
        setY(p.y);
    }

    /**
	* Checks to see if the calling object is at the last location of the board (max level, x, y)
    * @param b The passed Board object
	* @return boolean Whether the player is at the last location or not
	*/
    public boolean won(Board b) {
        return (level == b.getLevel()-1 && x == b.getSize()-1 && y == b.getSize()-1);
    }

    /**
	* Description Checks to see if the location of the calling object is the same location of
    * passed object
    * @param p The passed Player object
	* @return boolean Whether or not the players have the same level, x, and y
	*/
    public boolean equals(Player p) {
        return (p.level == this.level && p.x == this.x && p.y == this.y);
    }

	/**
	* Create string representation of the Player for printing
	* @return String All values of the Player object
	*/
	@Override
	public String toString() {
		return (name + " is on level " + level + " at location (" + x + ", " + y + ") and has " + energy + " units of energy");
	}
}
