package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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
	private ArrayList<Contestant> allContestants, activeContestants; // lits of
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
	 * @return this.activeContestants
	 */
	public ArrayList<Contestant> getActiveContestants() {
		return activeContestants;
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
	 * @param target
	 *            desired contestant
	 * @return contestant or string object
	 */
	public Contestant getContestant(String first, String last) {
		Contestant j; 
		// loop through array
		for(int i = 0; i <= numContestants; i++){
			j = activeContestants.get(i); // get Contestant object for comparison 
			if(first.equals(j.getFirstName()) && last.equals(j.getLastName())) { // ensure names match
				return j; // return info on player
			}
		}
		// otherwise return message saying contestant is no longer/is not in the game
		return null;
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
	 * intGameData reads in a data file and builds a GameData object out
	 * of it, returning it to the user.
	 * 
	 * @param inputFile   file to be read in
	 * @return GameData object made out of file or null if season not created
	 * 
	 */
	public static GameData initGameData(String inputFile){
		readFile(inputFile);
		return currentGame;
	}
	
	/**
	 * Uses the stored first and last name, and the currently running Game to 
	 * generate a unique user ID.
	 * @param c contestant to use data from.
	 */
	public String generateContestantID(Contestant c) {
		if (c.getLastName() == null ||
				c.getFirstName() == null) {
			System.out.println("generateContestantID: first or last name null");
			return new String();
		}
		
		// build all the currently used IDs
		ArrayList<String> idArr = new ArrayList<String>(allContestants.size());
		for (Contestant cind: allContestants) {
			if (cind.getID() != null) 
				idArr.add(cind.getID());
		}
		
		String newID;
		String lastName = c.getLastName().replaceAll("\\s+","");
		int lastSub = Math.min(6, lastName.length());
		int num = 0;
		do {
			// take the first letter of first name
			// take substring of lastName length 6 or full name
			newID = c.getFirstName().charAt(0) 
					+ lastName.substring(0, lastSub);
			if (num != 0) {
				newID += Integer.toString(num);
			}
			num++;
		} while (idArr.indexOf(newID) != -1); // check if the ID is present
		
		System.out.println("Generated: " + newID);
		return newID;
	}
	
	/**
	 * reads in file and (supposed) to fill in appropriate game data
	 * @param file the file that contains the data
	 */
	private static void readFile(String file) {
		try {
			/*
			 * first line num cont
			 * second line tribe 1 name
			 * 3rd line tribe 2 name
			 */
			Scanner scan = new Scanner(new File(file));
			scan.next(); //key value
			int numContestants =scan.nextInt();
			
			currentGame = new GameData(numContestants);
			
			scan.next();		//tribe 1 name
			String t1 = scan.next();
			
			scan.next();		//tribe 2 name
			String t2 = scan.next();
			
			currentGame.setTribeNames(t1, t2);
			
			
			//TODO:might not need seasonmade..
			currentGame.seasonMade();
		} catch (FileNotFoundException e) {
					
		}
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
