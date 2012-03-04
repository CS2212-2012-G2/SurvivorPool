package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import json.JSONUtils;

import admin.Main;
import admin.Utils;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * GameData is the class that will be used to keep track of the important game
 * information, including the number of weeks passed, the lists of all/active
 * contestants, and the number of weeks remaining.
 * 
 * @author Graem Littleton, Ramesh Raj, Justin McDonald, Jonathan Demelo, Kevin
 *         Brightwell
 */

public class GameData {

	private int weeksRem, weeksPassed, numContestants; // keep track of weeks remaining/weeks
										// passed
	private boolean gameStarted, seasonMade = false; // true if game has started and admin can no
									// longer add players
	private ArrayList<Contestant> allContestants = new ArrayList<Contestant>(); // lits of
															// all/remaining
															// contestants
	private String[] tribeNames = new String[2]; // string array storing both tribe names

	// store the current running version.

	private static GameData currentGame = null;
	
	/**
	 * Constructor method that takes a set number of contestants. Will not
	 * proceed if numContestants is NOT between 6 and 15, inclusive. Sets number
	 * of weeks passed to 0 and weeks remaining to number of contestants - 3.
	 * 
	 * @param numContestants
	 *            number of contestants to be in game
	 */
	public GameData(int numContestants) {
		// check if within parameters
		if(numContestants > 15 || numContestants < 6)
			return; // if not, do not create GameData item
		
		weeksRem = numContestants - 3;
		weeksPassed = 0;
		this.numContestants = numContestants;
		allContestants = new ArrayList<Contestant>(numContestants);
		
		currentGame = this;
	}

	// ----------------- ACCESSOR METHODS -----------------//

	/**
	 * getActiveContestants returns an array (list) of the contestants that are
	 * still competing in the game.
	 * 
	 * @return The contestants active
	 */
	public ArrayList<Contestant> getActiveContestants() {
		ArrayList<Contestant> active = new ArrayList<Contestant>(allContestants.size());
		
		for (Contestant c: allContestants) 
			if (!c.isCastOff())
				active.add(c);
		
		return active;
	}

	/**
	 * getAllContestants returns a list of all current and former contestants
	 * that are/have been involved with the game.
	 * 
	 * @return this.allContestants
	 */
	public ArrayList<Contestant> getAllContestants() {
		return allContestants;
	}

	/**
	 * getContestant takes the first and last name of a contestant as input and
	 * searches the array of current contestants for him/her. Returns
	 * information found in the Contestant class to the caller.
	 * 
	 * @param first
	 * 				First name
	 * @param last
	 * 				Last name
	 * @return contestant or string object
	 */
	public Contestant getContestant(String first, String last) {
		Contestant j; 
		// loop through array
		for(int i = 0; i <= numContestants; i++){
			j = allContestants.get(i); // get Contestant object for comparison 
			if(first.equals(j.getFirstName()) && last.equals(j.getLastName())) { // ensure names match
				return j; // return info on player
			}
		}
		// otherwise return message saying contestant is no longer/is not in the game
		return null;
	}
	
	/**
	 * Adds a new contestant into the Game, this will maintain the list of 
	 * contestants as sorted by ID.
	 * @param c New contestant, will not add if ID of contestant is null.
	 */
	public void addContestant(Contestant c) {
		if (c.getID() == null || 
				c.getID().equals("")) {
			System.out.println("Contestant must have valid ID");
			return;
		}
		
		if (!isIDValid(c.getID())) {
			ArrayList<Person> a = new ArrayList<Person>(15);
			for (Contestant t: getAllContestants())
				a.add((Person)t);
			
			c.setID(StringUtil.generateID(c, a));
			System.out.println("Invalid contestant ID specified, generating.");
		}
		
		allContestants.add(c);
		Collections.sort(allContestants, new Contestant.ComparatorID());
	}
	
	/**
	 * getTribeName returns a String array with two entries: the name of the first tribe,
	 * and the name of the second tribe.
	 * 
	 * @return String array  tribe names
	 */
	
	public String[] getTribeNames(){
		return tribeNames;
	}

	/**
	 * weeksLeft returns the number of weeks remaining before the game ends.
	 * 
	 * @return this.weeksRem
	 */
	public int weeksLeft() {
		return weeksRem;
	}
	
	/**
	 * Get the current week in play. Starts from Week 1.
	 * @return Current week
	 */
	public int getCurrentWeek() {
		return weeksPassed+1;
	}

	// ----------------- MUTATOR METHODS ------------------//

	/**
	 * advanceWeek sets the number of weeksPassed to weeksPassed + 1.
	 */
	public void advanceWeek() {
		weeksRem -= 1;    // reduce num of weeks remaining
		weeksPassed += 1;  // increment number of weeks passed
	}

	/**
	 * removeContestant takes a Contestant object as input and attempts to
	 * remove it from the array of active contestants. Maintains sorted ID 
	 * order
	 * 
	 * @param target
	 *            eliminated contestant
	 */
	public void removeContestant(Contestant target) {
		// is the contestant there?
		int i = Collections.binarySearch(allContestants, target,
				new Contestant.ComparatorID());
		
		if (i < 0) {
			// i < 0 implies not found.
			return;
		}
		
		allContestants.remove(i);
		Collections.sort(allContestants, new Contestant.ComparatorID());
	}

	/**
	 * startGame sets gameStarted to true, not allowing the admin to add any
	 * more players/Contestants to the pool/game.
	 */

	public void startGame() {
		gameStarted = true;
	}
	
	/**
	 * seasonCreated sets the seasonMade boolean to true, indicating that there exists
	 * a GameData object to be used.
	 */
	
	public void seasonMade(){
		seasonMade = true;
	}
	
	/**
	 * setTribeNames sets both tribe names accordingly and stores them in 
	 * the tribeNames string array.
	 * 
	 * @param tribeOne   name of tribe one
	 * @param tribeTwo   name of tribe two
	 */
	public void setTribeNames(String tribeOne, String tribeTwo){
		tribeNames[0] = tribeOne;
		tribeNames[1] = tribeTwo;
	}
	
	// ----------------- HELPER METHODS ----------------- //
	
	
	/**
	 * Checks if an ID string passed in is valid amongst the currently loaded
	 * ID tags. Also checks if the syntax is valid.
	 * @param id The ID tag to check
	 * @return True if valid to use
	 */
	public boolean isIDValid(String id) {
		// build all the currently used IDs
		return Utils.checkString(id, Person.REGEX_CONTEST_ID) 
				&& !isIDInUse(id);
	}
	
	/**
	 * Helper method to get the index of a contestant ID in the 
	 * activeContestants array
	 * @param id Search Contestant ID
	 * @return Index in activeContestants where ID is stored, else < 0.
	 */
	private int getContestantIndexID(String id) {
		Contestant t = new Contestant();
		t.setID(id);
		
		return Collections.binarySearch(allContestants, t, 
				new Contestant.ComparatorID());
	}
	
	/**
	 * Tells whether an ID is in use.
	 * @param id The Contestant ID is in use.
	 * @return True if in use.
	 */
	public boolean isIDInUse(String id) {
		return (getContestantIndexID(id) >= 0);
	}
	
	/**
	 * intGameData reads in a data file and builds a GameData object out
	 * of it, returning it to the user.
	 * 
	 * @param inputFile   file to be read in
	 * @return GameData object made out of file or null if season not created
	 * 
	 */
	public static GameData initGameData(){
		try {
			JSONUtils.readSeasonFile();
		} catch (FileNotFoundException e) {
			return null; 
		}
		currentGame = new GameData(JSONUtils.getContestants());
		currentGame.setTribeNames(JSONUtils.getTribe1(), JSONUtils.getTribe2());
		try {
			JSONUtils.readContestantFile();
			JSONUtils.readPlayerFile();
		} catch (FileNotFoundException e) { 
		}
		return currentGame;
	}
	
	
	/**
	 * Returns the currently stored Game, this removed need to reference the
	 * game data all the time. But also allows objects to read data, cleanly.
	 * @return Currently started game, null if none present.
	 */
	public static GameData getCurrentGame() {
		return GameData.currentGame;
	}
	
	/**
	 * Nulls the current game stored, allows a new game to start.
	 */
	public static void endCurrentGame() {
		GameData.currentGame = null;
		//TODO:remove data persistence file
	}
}
