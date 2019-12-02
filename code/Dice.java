// -----------------------------------------------------
// Assignment 4 (Dice class)
// Written by: Eli Samuel 40122277
// For COMP 248 Section EC B - December 2, 2019
// -----------------------------------------------------

/* This is the Dice class for the LetUsPlay.java class. This class contains one no-argument constructor which initiates both die
 * to a number (in this case 0). This class also contains other methods which can be called from other classes. */

import java.util.Random;

public class Dice {

    private int die1;   // The roll of the first die
    private int die2;   // The roll of the second die

	/**
	* Constructor that initiates both die to 0
	*/
	public Dice() {
        this.die1 = 0;
        this.die2 = 0;
	}

	/**
	* Returns value of die1
	* @return int The number rolled for die1
	*/
	public int getDie1() {
		return die1;
	}

	/**
	* Returns value of die2
	* @return int The number rolled for die2
	*/
	public int getDie2() {
		return die2;
	}

    /**
	* Simulates the rolling of 2 dice and assigns a value to die1 and die2 between 1 and 6
	* @return int The sum of die1 and die2
	*/
    public int rollDice() {
        Random rand = new Random();     // Random number variable
        final int SIDES = 6;            // Number of sides on a dice
        die1 = rand.nextInt(SIDES)+1;   // Generates random numbers between 1 and 6 for die1
        die2 = rand.nextInt(SIDES)+1;   // Generates random numbers between 1 and 6 for die2

        return die1+die2;
    }

    /**
	* Checks to see if the value of die1 is equal to die2
	* @return boolean Whether they are equal or not
	*/
    public boolean isDouble() {
        return (die1 == die2);
    }

	/**
	* Create string representation of Dice for printing
	* @return String All values of the dice
	*/
	@Override
	public String toString() {
		return "Die 1: " + die1 + ", Die 2: " + die2;
	}
}
