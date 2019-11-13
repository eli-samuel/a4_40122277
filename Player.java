public class Player {

    private String name;
    private int level, x, y, energy;


	/**
	* Default empty Player constructor
	*/
	public Player() {
        name = "";
        level = x = y = 0;
        energy = 10;
	}

	/**
	* Default Player constructor
	*/
	public Player(String name) {
		setName(name);
        level = x = y = 0;
        energy = 10;
	}

    /**
	* Default Player constructor
	*/
	public Player(int level, int x, int y) {
		name = "";
        setLevel(level);
        setX(x);
        setY(y);
        energy = 10;
	}

    /**
    * Duplicator player constructor
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
	* @return
	*/
	public String getName() {
		return name;
	}

	/**
	* Sets new value of name
	* @param
	*/
	public void setName(String name) {
		this.name = name;
	}

	/**
	* Returns value of level
	* @return
	*/
	public int getLevel() {
		return level;
	}

	/**
	* Sets new value of level
	* @param
	*/
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	* Returns value of x
	* @return
	*/
	public int getX() {
		return x;
	}

	/**
	* Sets new value of x
	* @param
	*/
	public void setX(int x) {
		this.x = x;
	}

	/**
	* Returns value of y
	* @return
	*/
	public int getY() {
		return y;
	}

	/**
	* Sets new value of y
	* @param
	*/
	public void setY(int y) {
		this.y = y;
	}

	/**
	* Returns value of energy
	* @return
	*/
	public int getEnergy() {
		return energy;
	}

	/**
	* Sets new value of energy
	* @param
	*/
	public void setEnergy(int energy) {
        if (energy >= 0) this.energy = energy;
        else this.energy = 0;
	}

    /**
	* Description
    * @param
	* @return
	*/
    public void moveTo(Player p) {
        setLevel(p.level);
        setX(p.x);
        setY(p.y);
    }

    /**
	* Description
    * @param
	* @return
	*/
    public boolean won(Board b) {
        return (level == b.getLevel() && x == b.getSize() && y == b.getSize());
    }

    /**
	* Description
    * @param
	* @return
	*/
    public boolean equals(Player p) {
        return (p.level == this.level && p.x == this.x && p.y == this.y);
    }

	/**
	* Create string representation of Player for printing
	* @return
	*/
	@Override
	public String toString() {
		return (name + " is on level " + level + " at location (" + x + ", " + y + ") and has " + energy + " units of energy");
	}
}
