// THIS WORKS ALREADY NICE

import java.util.Random;

public class Dice {

    private int die1;
    private int die2;

	/**
	* Default Dice constructor
	*/
	public Dice(int die1, int die2) {
		this.die1 = die1;
		this.die2 = die2;
	}

	/**
	* Returns value of die1
	* @return
	*/
	public int getDie1() {
		return die1;
	}

	/**
	* Returns value of die2
	* @return
	*/
	public int getDie2() {
		return die2;
	}

    /**
	* Description
    * @param
	* @return
	*/
    public int rollDice() {
        Random rand = new Random();
        final int SIDES = 6;
        die1 = rand.nextInt(SIDES)+1;
        die2 = rand.nextInt(SIDES)+1;

        return die1+die2;
    }

    /**
	* Description
    * @param
	* @return
	*/
    public static boolean isDouble() {
        if (die1 == die2) return true;
        else return false;
    }

	/**
	* Create string representation of Dice for printing
	* @return
	*/
	@Override
	public String toString() {
		return "Die 1: " + die1 + ", Die 2: " + die2;
	}
}