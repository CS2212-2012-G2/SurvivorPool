package data;

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

	private int weeksRem, weeksPassed; // keep track of weeks remaining/weeks
										// passed
	private boolean gameStarted, seasonMade = false; // true if game has started and admin can no
									// longer add players
	private Contestant[] allContestants, activeContestants; // lits of
															// all/remaining
															// contestants
	private String tribeOneName, tribeTwoName; // names of participating tribes
	private String[] tribeNames; // string array storing both tribe names

	/**
	 * Constructor method that takes a set number of contestants. Will not
	 * proceed if numContestants is NOT between 6 and 15, inclusive. Sets number
	 * of weeks passed to 0 and weeks remaining to number of contestants - 3.
	 * 
	 * @param numContestants
	 *            number of contestants to be in game
	 */
	public GameData(int numContestants) {
		throw new NotImplementedException();
	}

	// ----------------- ACCESSOR METHODS -----------------//

	/**
	 * getActiveContestants returns an array (list) of the contestants that are
	 * still competing in the game.
	 * 
	 * @return this.activeContestants
	 */
	public Contestant[] getActiveContestants() {
		throw new NotImplementedException();
	}

	/**
	 * getAllContestants returns a list of all current and former contestants
	 * that are/have been involved with the game.
	 * 
	 * @return this.allContestants
	 */
	public Contestant[] getAllContestants() {
		throw new NotImplementedException();
	}

	/**
	 * getContestant takes an active contestant in the game as input and
	 * searches the array of current contestants for him/her. Returns
	 * information found in the Contestant class to the caller.
	 * 
	 * @param target
	 *            desired contestant
	 * @return contestant object
	 */
	public Contestant getContestant(Contestant target) {
		throw new NotImplementedException();
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
		throw new NotImplementedException();
	}

	// ----------------- MUTATOR METHODS ------------------//

	/**
	 * advanceWeek sets the number of weeksPassed to weeksPassed + 1.
	 */
	public void advanceWeek() {
		throw new NotImplementedException();
	}

	/**
	 * removeContestant takes a Contestant object as input and attempts to
	 * remove it from the array of active contestants.
	 * 
	 * @param target
	 *            eliminated contestant
	 */
	public void removeContestant(Contestant target) {
		throw new NotImplementedException();
	}

	/**
	 * startGame sets gameStarted to true, not allowing the admin to add any
	 * more players/Contestants to the pool/game.
	 */

	private void startGame() {
		throw new NotImplementedException();
	}
	
	/**
	 * seasonCreated sets the seasonMade boolean to true, indicating that there exists
	 * a GameData object to be used.
	 */
	
	private void seasonMade(){
		seasonMade = true;
	}
	
	/**
	 * setTribeNames sets both tribe names accordingly and stores them in 
	 * the tribeNames string array.
	 * 
	 * @param tribeOne   name of tribe one
	 * @param tribeTwo   name of tribe two
	 */
	private void setTribeNames(String tribeOne, String tribeTwo){
		throw new NotImplementedException();
	}

}
