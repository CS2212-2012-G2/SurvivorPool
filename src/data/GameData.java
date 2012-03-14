package data;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

import common.Utils;
//import admin.data.JSONUtils;
import data.me.json.*;

/**
 * GameData is the class that will be used to keep track of the important game
 * information, including the number of weeks passed, the lists of all/active
 * contestants, and the number of weeks remaining.
 * 
 * @author Graem Littleton, Ramesh Raj, Justin McDonald, Jonathan Demelo, Kevin
 *         Brightwell
 */

public abstract class GameData {

	protected int weeksRem, weeksPassed; // keep track of weeks remaining/weeks passed
	protected int numContestants;
										
	protected boolean seasonStarted= false, elimExists = false; 
	
	protected String[] tribeNames = new String[2]; // string array storing both tribe names

	protected Vector allContestants;
	
	protected Vector allUsers;
	
	// store the current running version
	protected static GameData currentGame = null;
	// store contestant who was cast off
	protected Contestant elimCont;
	
	/**
	 * JSON Keys
	 */
	protected static final String KEY_CONTESTANTS = "cons"; 
	protected static final String KEY_NUM_CONTEST	= "cons_num";
	
	protected static final String KEY_USERS = "users";
	
	protected static final String KEY_WEEKS_REMAIN = "weeks_rem";
	protected static final String KEY_WEEKS_PASSED = "weeks_pass";
	
	protected static final String KEY_TRIBES = "tribes_arr";
	
	protected static final String KEY_SEASON_STARTED = "season_started";
	
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
		
		allContestants = new Vector(numContestants);
		
		allUsers = new Vector();
		
		currentGame = this;
	}

	// ----------------- ACCESSOR METHODS -----------------//

	
	// ~~~~~~~~~~~~~~~~ CONTESTANT METHODS ~~~~~~~~~~~~~~~ //
	
	/**
	 * getActiveContestants returns an array (list) of the contestants that are
	 * still competing in the game.
	 * 
	 * @return The contestants active
	 */
	public Vector getActiveContestants() {
		//ArrayList<ContestantAdmin> active = new ArrayList<ContestantAdmin>(allContestants.size());
		
		Vector active = new Vector(allContestants.size());
		
		int newSize = 0;
		for (int i = 0; i < allContestants.size(); i++) {
			Contestant c = (Contestant)allContestants.get(i);
			if ((c != null) && !c.isCastOff()) {
				active.add((Object)c);
			}
		}
		
		return active;
	}
	
	

	/**
	 * getAllContestants returns a list of all current and former contestants
	 * that are/have been involved with the game.
	 * 
	 * @return this.allContestants
	 */
	public Vector getAllContestants() {
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
			j = (Contestant)allContestants.get(i); // get Contestant object for comparison 
			if(first.equals(j.getFirstName()) && last.equals(j.getLastName())) { // ensure names match
				return j; // return info on player
			}
		}
		// otherwise return message saying contestant is no longer/is not in the game
		return null;
	}
	
	/**
	 * Get contestant based on unique id
	 * @param id an unique id
	 * @return the Contestant that matches id or null
	 */
	public Contestant getContestant(String id) {
		int index = getContestantIndexID(id);
		
		return (index > -1 ? (Contestant)allContestants.get(index) : null);
	}
	
	/**
	 * Adds a new contestant into the Game, this will maintain the list of 
	 * contestants as sorted by ID.
	 * @param c New contestant, will not add if ID of contestant is null.
	 */
	public void addContestant(Contestant c) {
		if (c.getID() == null || 
				!isIDValid(c.getID())) {
			System.out.println("Contestant must have valid ID");
			return;
		}
		
		if (allContestants.size() == numContestants) {
			System.out.println("Too many contestants.");
			return;
		}
		
		allContestants.add((Object)c);
	}
	
	// ~~~~~~~~~~~~~~~~~~~ USER METHODS ~~~~~~~~~~~~~~~~~~ //
	
	/**
	 * Gets the vector of all users.
	 * @return Vector containing all users.
	 */
	public Vector getAllUsers() {
		return allUsers;
	}
	
	/**
	 * Adds a user to the list of users.
	 * @param u New user to add.
	 */
	public void addUser(User u) {
		allUsers.add((Object)u);
	}
	
	/**
	 * Removes a user from the list.
	 * @param u    User to remove.
	 */
	public void removeUser(User u) {
		for (Object o: allUsers) {
			User arrU = (User)o;
			if (u.getID().equals(arrU.getID())) {
				allUsers.remove(arrU);
				return;
			}
		}
	}
	
	/**
	 * Gets a user from the stored users by ID.
	 * @param ID User ID of the User to get from the stored data.
	 * @return User if ID found, null otherwise.
	 */
	public User getUser(String ID) {
		for (Object o: allUsers) {
			User u = (User)o;
			if (u.getID().equalsIgnoreCase(ID)) {
				return u;
			}
		}
		
		return null;
	}
	
	/**
	 * Iterates through all users on the list. 
	 * Allocates points based off of weekly elimination pick.
	 * 
	 * @param c  Contestant that was cast off
	 */
	
	public void allocatePoints(Contestant c){
		Iterator<User> itr = allUsers.iterator();
		User u;
		while(itr.hasNext()){
			u = itr.next();
			if(u.getWeeklyPick().equals(c)){
			   u.addPoints(20);
			   System.out.println("Added 20 points to " + u);
			}
		itr.next();
		}
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

	/**
	 * Checks if a season has been started
	 * @see startGame to set to true.
	 * @return true if a season has started(different from created)
	 */
	public boolean getSeasonStarted(){
		return seasonStarted;
	}
	
	/**
	 * Checks if there are any more weeks remaining
	 * @return true if weeks remaining = 0
	 */
	public boolean getSeasonEnded(){
		return weeksRem==0;
	}
	// ----------------- MUTATOR METHODS ------------------//

	/**
	 * advanceWeek sets the number of weeksPassed to weeksPassed + 1.
	 */
	public void advanceWeek() {
		if(elimExists == false)
			return;
		
		weeksRem -= 1;    // reduce num of weeks remaining
		weeksPassed += 1;  // increment number of weeks passed
		allocatePoints(elimCont);
		elimCont.castOff();
	}

	/**
	 * removeContestant takes a Contestant object as input and attempts to
	 * remove it from the array of active contestants. Maintains order of data
	 * 
	 * @param target
	 *            Contestant to remove
	 */
	@SuppressWarnings("unchecked")
	public void removeContestant(Contestant target) {
		// is the contestant there?
		// done this way incase its just a Contestant with ID passed
		for (int i = 0; i < numContestants && allContestants.get(i)	!= null; i++) {
			Contestant c = (Contestant)allContestants.get(i);
			if (target.getID().equalsIgnoreCase(c.getID())) {
				allContestants.remove(i);
				return;
			}
		}
	}

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
	protected int getContestantIndexID(String id) {
		// loop through array
		for(int i = 0; i < numContestants; i++){
			Contestant j = (Contestant)allContestants.get(i); // get Contestant object for comparison 
			if (j.getID().equals(id)) { // ensure names match
				return i; // return info on player
			}
		}
		// otherwise return message saying contestant is no longer/is not in the game
		return -1;
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
	public void endCurrentGame() {
		GameData.currentGame = null;
	}
	
	/**
	 * toString returns a string of the contestant's information in JSON format.
	 */
	public String toString() {
		return new String("GameData<WR:\"" + weeksRem + "\"" + ", WP:\"" + weeksPassed + "\"" + 
				", #C:\"" + numContestants + "\"" + ", SS: " + "\"" + seasonStarted + "\"" + 
				", TN: {" + "\"" + tribeNames[0] + "\", \"" + tribeNames[1] + "\"}>");
	}

	/**
	 * Convert GameData to a JSON object
	 * @return a JSONObject with all the relevant data
	 * @throws JSONException
	 */
	public JSONObject toJSONObject() throws JSONException {
		JSONObject obj = new JSONObject();
		
		obj.put(KEY_NUM_CONTEST, new Integer(numContestants));
		JSONArray cons = new JSONArray();
		for (Object o: allContestants) {
			if (o != null)
				cons.put(((Contestant) o).toJSONObject());
		}
		
		JSONArray users = new JSONArray();
		for (Object o: allUsers) {
			if (o != null)
				users.put(((User) o).toJSONObject());
		}
		
		JSONArray ts = new JSONArray();
		ts.put(tribeNames[0]);
		ts.put(tribeNames[1]);
		
		
		obj.put(KEY_CONTESTANTS, cons);
		obj.put(KEY_USERS, users);
		obj.put(KEY_TRIBES, ts);
		obj.put(KEY_WEEKS_REMAIN, weeksRem);
		obj.put(KEY_WEEKS_PASSED, weeksPassed);
		obj.put(KEY_SEASON_STARTED,seasonStarted);

		
		return obj;
	}
	
	/**
	 * Update GameData with values from JSONObject
	 * @param obj a JSONObject that contains all the values
	 * @throws JSONException
	 */
	public void fromJSONObject(JSONObject obj) throws JSONException {
		numContestants = ((Number)obj.get(KEY_NUM_CONTEST)).intValue();
				
		// tribes
		JSONArray ts = (JSONArray)obj.get(KEY_TRIBES);
		this.setTribeNames((String)ts.get(0),  (String)ts.get(1) );
		// week info:
		weeksRem = obj.getInt(KEY_WEEKS_REMAIN);
		weeksPassed = obj.getInt(KEY_WEEKS_PASSED);
		
		seasonStarted = obj.getBoolean(KEY_SEASON_STARTED);
		
		//Contestants must be loaded before users, but after others!
		allContestants = new Vector(numContestants);
		
		// if we can move this back into the subclass, put this after a super.fromJSONObject() call.
		
		// load the contestant array. 
		JSONArray cons = (JSONArray)obj.get(KEY_CONTESTANTS);
		for (int i =0;i<cons.length();i++) {
			Contestant c = new Contestant();
			c.fromJSONObject(cons.getJSONObject(i));
			addContestant(c);
		}
		
		// users:
		JSONArray users = (JSONArray)obj.get(KEY_USERS);
		for (int i = 0; i < users.length(); i++) {
			User u = new User();
			u.fromJSONObject(users.getJSONObject(i));
			addUser(u);
		}
	}

	public abstract void writeData();
	
}
