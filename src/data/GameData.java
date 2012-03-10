package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;



import admin.ComparatorFactory;
import admin.Utils;
import admin.data.Contestant;
import admin.json.JSONObject;
import admin.json.JSONUtils;
import admin.json.parser.ParseException;

/**
 * GameData is the class that will be used to keep track of the important game
 * information, including the number of weeks passed, the lists of all/active
 * contestants, and the number of weeks remaining.
 * 
 * @author Graem Littleton, Ramesh Raj, Justin McDonald, Jonathan Demelo, Kevin
 *         Brightwell
 */

public abstract class GameData {

	protected int weeksRem, weeksPassed; // keep track of weeks remaining/weeks
	protected int numContestants;
										// passed
	protected boolean seasonStarted= false; // true if game has started and admin can no
									// longer add players
	
	protected String[] tribeNames = new String[2]; // string array storing both tribe names

	// store the current running version.

	protected static GameData currentGame = null;
	
	
	/**
	 * JSON Keys
	 */
	protected static final String KEY_CONTESTANTS = "cons"; 
	protected static final String KEY_NUM_CONTEST	= "cons_num";
	
	protected static final String KEY_WEEKS_REMAIN = "weeks_rem";
	protected static final String KEY_WEEKS_PASSED = "weeks_pass";
	
	protected static final String KEY_TRIBES = "tribes_arr";
	
	/**
	 * Constructor method that takes a set number of contestants. Will not
	 * proceed if numContestants is NOT between 6 and 15, inclusive. Sets number
	 * of weeks passed to 0 and weeks remaining to number of contestants - 3.
	 * 
	 * @param numContestants
	 *            number of contestants to be in game
	 */
	// TODO: Make throw exception, its not enough to return, the object is still created.
	public GameData(int numContestants) {
		// check if within parameters
		if(numContestants > 15 || numContestants < 6)
			return; // if not, do not create GameData item
		
		weeksRem = numContestants - 3;
		weeksPassed = 0;
		this.numContestants = numContestants;
		
		currentGame = this;
	}

	// ----------------- ACCESSOR METHODS -----------------//

	/**
	 * getActiveContestants returns an array (list) of the contestants that are
	 * still competing in the game.
	 * 
	 * @return The contestants active
	 */
	public abstract Contestant[] getActiveContestants();
	
	

	/**
	 * getAllContestants returns a list of all current and former contestants
	 * that are/have been involved with the game.
	 * 
	 * @return this.allContestants
	 */
	public abstract Contestant[] getAllContestants();

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
	public abstract Contestant getContestant(String first, String last);
	
	// TODO: Doc
	public abstract Contestant getContestant(String id);
	
	/**
	 * Adds a new contestant into the Game, this will maintain the list of 
	 * contestants as sorted by ID.
	 * @param c New contestant, will not add if ID of contestant is null.
	 */
	public abstract void addContestant(Contestant c);
	
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

	/**
	 * Checks if a season has been started
	 * @see startGame to set to true.
	 * @return true if a season has started(different from created)
	 */
	public boolean getSeasonStarted(){
		return seasonStarted;
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
	public abstract void removeContestant(Contestant target);

	/**
	 * startGame sets gameStarted to true, not allowing the admin to add any
	 * more players/Contestants to the pool/game.
	 */

	public void startSeason() {
		seasonStarted = true;
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
	protected abstract int getContestantIndexID(String id); 
	
	/**
	 * Tells whether an ID is in use.
	 * @param id The Contestant ID is in use.
	 * @return True if in use.
	 */
	public boolean isIDInUse(String id) {
		return (getContestantIndexID(id) >= 0);
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
	
	/**
	 * toString returns a string of the contestant's information in JSON format.
	 */
	public String toString() {
		return new String("GameData<WR: " + "\"" + weeksRem + "\"" + ", WP: " + "\"" + weeksPassed + "\"" + 
				", #C: " + "\"" + numContestants + "\"" + ", SS: " + "\"" + seasonStarted + "\"" + 
				", TN: " + "\"" + tribeNames[0] + "\"" + " + \"" + tribeNames[1] + "\">");
	}
	
	// TODO: DOC THESE THREE
	 
	
	public String toJSONString() {
		return toJSONObject().toJSONString();
	}
	
	public abstract JSONObject toJSONObject();
	
	public abstract void fromJSONObject(JSONObject o);
	
	public abstract void fromJSONString(String json) throws ParseException;
}
