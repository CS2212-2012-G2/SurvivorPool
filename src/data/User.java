package data;

import json.simple.JSONObject;
import json.simple.parser.ParseException;
import admin.Utils;
import data.InvalidFieldException.Field;

/**
 * The user class will be used to create an individual who will be participating
 * in the survivor pool.
 * 
 * @author Graem Littleton, Kevin Brightwell, Jonathan Demelo, Ramesh Raj
 */

public class User implements Person, Comparable<User> {

	private String firstName, lastName, unID; // first and last names and unique ID (UWO ID format)
	private int points; // point total, points user receives if that pick wins
	protected int ultPoints;
	private Contestant ultPick, weeklyPick; // users pick of the winner and their weekly pick
	private int numBonusAnswer = 0;
	
	protected static final String KEY_ID = "id",
								  KEY_FIRST_NAME = "first",
								  KEY_LAST_NAME = "last",
								  KEY_POINTS = "curr_points",
								  KEY_WIN_PICK_POINTS = "win_points",
								  KEY_ULT_PICK_ID = "ult_pick",
								  KEY_WEEKLY_PICK_ID = "week_pick",
								  KEY_NUM_BONUS_ANSWER = "num_bonus_answer";
	/**
	 * Constructor method for the type User sets names, initializes points
	 * 
	 * @param first      first name
	 * @param last       last name
	 * @param id         unique ID
	 * @throws InvalidFieldException       Thrown if any of the parameters passed are invalid
	 */
	public User(String first, String last, String id)
			throws InvalidFieldException {
		setFirstName(first);
		setLastName(last);
		setID(id);
		setPoints(0); // begin with 0 points

	}

	/**
	 * Constructor for User with no information given.  Just sets points
	 * to 0, all other information is null.
	 */
	public User() {
		setPoints(0);
	}

	// -------------------- ACCESSOR METHODS ------------------ //

	/**
	 * getFirstName returns the first name of the user
	 * 
	 * @return this.firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * getID returns the users unique ID
	 * 
	 * @return this.unID
	 */
	public String getID() {
		return unID;
	}
	
	/**
	 * getLastName returns the users last name
	 * 
	 * @return this.lastName
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Get the number of bonus questions answered this week
	 * @return num questions answered
	 */
	public int getNumBonusAnswer() {
		return numBonusAnswer;
	}
	
	/**
	 * Returns the users current score
	 * @return
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * getWinPick returns the users selection for which contestant will win the
	 * game
	 * 
	 * @return this.winPick
	 */
	public Contestant getUltimatePick() {
		return ultPick;
	}
	
	/**
	 * Get the ultimate points based on ultimate pick
	 * 
	 * @return the ultimate points
	 */
	public int getUltimatePoints() {
		return ultPoints;
	}
	
	/**
	 * getWeeklyPick returns the users pick for which contestant will be
	 * eliminated @ return this.weeklyPick
	 */
	public Contestant getWeeklyPick() {
		return weeklyPick;
	}



	// ---------------- MUTATOR METHODS ----------------- //

	/**
	 * addPoints add points to a users current total
	 * 
	 * @param newPoints
	 *            points to be added
	 */
	public void addPoints(int newPoints) {
		points += newPoints;
	}

	/**
	 * setFirstName sets the users first name
	 * 
	 * @param first
	 *            users first name
	 * @throws InvalidFieldException
	 */
	public void setFirstName(String first) throws InvalidFieldException {
		first = first.trim();
		if (!Utils.checkString(first, REGEX_FIRST_NAME))
			throw new InvalidFieldException(Field.USER_FIRST,
					"Invalid First Name (User)");
		firstName = Utils.strCapitalize(first);
	}
	
	/**
	 * Sets the user ID.
	 */
	public void setID(String id) throws InvalidFieldException {
		if (id != null)
			id = id.toLowerCase().trim();
		if (!Utils.checkString(id, REGEX_PLAYER_ID))
			throw new InvalidFieldException(Field.USER_ID, "Invalid Player ID");

		unID = id;
	}

	/**
	 * setFirstName sets the users last name
	 * 
	 * @param last
	 *            users last name
	 * @throws InvalidFieldException
	 */
	public void setLastName(String last) throws InvalidFieldException {
		last = last.trim();
		if (!Utils.checkString(last, REGEX_LAST_NAME))
			throw new InvalidFieldException(Field.USER_LAST,
					"Invalid Last Name (User)");
		lastName = Utils.strCapitalize(last);
	}
	
	/**
	 * Set the number of bonus questions answered this week.
	 * Set this to 0 after week has advanced!
	 * @param numBonusAnswer The number of bonus questions answered
	 */
	public void setNumBonusAnswer(int numBonusAnswer) {
		this.numBonusAnswer = numBonusAnswer;
	}
	
	/**
	 * Sets the users current score
	 * @param points
	 */
	public void setPoints(int points) {
		this.points = points;
	}
	
	/**
	 * setWinPick sets the users choice for which contestant will win the
	 * competition. Also determines how many points the user will receive if
	 * that player wins
	 * 
	 * @param winner
	 *            contestant choice
	 * @throws InvalidFieldException
	 *             If null, throws exception.
	 */
	public void setUltimatePick(Contestant winner) throws InvalidFieldException {
		setUltimatePickNoSetPts(winner);
		if (!winner.isNull())
			ultPoints = 2 * GameData.getCurrentGame().weeksLeft();
	}
	
	/**
	 * just sets the same as prior without setting pts.
	 * 
	 * @param winner
	 * @throws InvalidFieldException
	 */
	public void setUltimatePickNoSetPts(Contestant winner)
			throws InvalidFieldException {
		if (winner == null) {
			throw new InvalidFieldException(Field.USER_WEEKLY_PICK,
					"Weekly pick was null");
		}
	 
		ultPick = winner;
	}
	
	/**
	 * sets the ultimate points.
	 * 
	 * @param pts
	 */
	public void setUltimatePoints(int pts) {
		ultPoints = pts;
	}


	/**
	 * setWeeklyPick sets the users pick for which contestant will be eliminated
	 * 
	 * @param pick
	 *            contestant choice
	 * @throws InvalidFieldException
	 *             if Pick is null.
	 */
	public void setWeeklyPick(Contestant pick) throws InvalidFieldException {
		if (pick == null) {
			throw new InvalidFieldException(Field.USER_WEEKLY_PICK,
					"Weekly Pick was null");
		} 
		
		weeklyPick = pick;
	}

	
    // ----------------- HELPER METHODS ---------------- //
	
	/**
	 * Compares two User objects.
	 * 
	 * @return 0    Users are equal.
	 * @return -1   User A is greater than User B.
	 * @return 1    User B is greater than User A.
	 */
	public int compareTo(User otherU) {
		// ugly, but works. :)
		int result = getID().compareToIgnoreCase(otherU.getID());
		if (result == 0) {
			result = getPoints() - otherU.getPoints();
			if (result == 0) {
				result = getLastName().compareToIgnoreCase(otherU.getLastName());
				if (result == 0) {
					result = getFirstName().compareToIgnoreCase(otherU.getLastName());
				}
			}
		}
		
		return result;
	}

	
	/**
	 * toString returns a string of the contestant's information in JSON format.
	 */
	public String toString() {
		return new String("User<FN: " + "\"" + firstName + "\"" + ", LN: "
				+ "\"" + lastName + "\"" + ", Points: " + "\"" + points + "\""
				+ ", ID: " + "\"" + unID + "\">");
	}
	
	/**
	 * Updates the stored user with any not null information in the passed user
	 * 
	 * @param u
	 *            The user to update from.
	 * @throws InvalidFieldException
	 *             Thrown if anything is of the wrong format.
	 * 
	 */
	public void update(User u) throws InvalidFieldException {
		if (u.getFirstName() != null) {
			setFirstName(u.getFirstName());
		}

		if (u.getLastName() != null) {
			setLastName(u.getLastName());
		}

		if (u.getID() != null) {
			setID(u.getID());
		}

		if (u.getPoints() != getPoints()) {
			setPoints(u.getPoints());
		}

		if (u.getWeeklyPick() != null && !u.getWeeklyPick().isNull()) {
			setWeeklyPick(u.getWeeklyPick());
		}

		if (u.getUltimatePick() != null && !u.getUltimatePick().isNull()) {
			setUltimatePickNoSetPts(u.getUltimatePick());
		}

		if (u.getUltimatePoints() != getUltimatePoints()) {
			setUltimatePoints(u.getUltimatePoints());
		}
	}

	// ----------------- JSON ----------------- //
	
	/**
	 * Turns a User into a JSON object.
	 * 
	 * @return obj   JSON object
	 */

	public JSONObject toJSONObject() throws ParseException {
		JSONObject obj = new JSONObject();

		obj.put(KEY_FIRST_NAME, getFirstName());
		obj.put(KEY_LAST_NAME, getLastName());
		obj.put(KEY_ID, getID());
		obj.put(KEY_POINTS, getPoints());

		Contestant c = getWeeklyPick();
		if (c == null) {
			c = new Contestant();
			c.setNull();
		}
		obj.put(KEY_WEEKLY_PICK_ID, c.getID());

		c = getUltimatePick();
		if (c == null) {
			c = new Contestant();
			c.setNull();
		}
		obj.put(KEY_ULT_PICK_ID, c.getID());

		obj.put(KEY_WIN_PICK_POINTS, new Integer(getUltimatePoints()));
		obj.put(KEY_NUM_BONUS_ANSWER,getNumBonusAnswer());
		return obj;
	}
	
	/**
	 * Turns a JSON object into a User.
	 * 
	 * @param o      JSON object
	 */

	public void fromJSONObject(JSONObject o) {
		try {
			setFirstName((String) o.remove(KEY_FIRST_NAME));
			setLastName((String) o.remove(KEY_LAST_NAME));
			setID((String) o.remove(KEY_ID));
			setPoints(Utils.numToInt(o.remove(KEY_POINTS)));

			String id = (String) o.remove(KEY_WEEKLY_PICK_ID);
			Contestant c = null;
			
			GameData g = GameData.getCurrentGame();
			
			if (id.equals(Contestant.NULL_ID)) {
				c = new Contestant();
				c.setNull();
			} else {
				c = g.getContestant(id);

			}
			setWeeklyPick(c);

			id = (String) o.remove(KEY_ULT_PICK_ID);
			if (id.equals(Contestant.NULL_ID)) {
				c = new Contestant();
				c.setNull();
			} else {
				c = g.getContestant(id);
			}
			setUltimatePick(c);

			setUltimatePoints(Utils.numToInt(o.remove(KEY_WIN_PICK_POINTS)));
			setNumBonusAnswer(((Number) o.remove(KEY_NUM_BONUS_ANSWER)).intValue());
		} catch (InvalidFieldException e) {
			System.out.println("Warning: InvalidFieldException in fromJSONObject");
			e.printStackTrace();
		}

	}

	
	
	
	// ====== TEST DRIVER ===== //
	public static void main(String[] args) {
		User u = null;
		try {
			u = new User("bob", "builder", "bbuilde");
		} catch (InvalidFieldException e) {
		}

		Contestant c = new Contestant();
		Contestant ul = new Contestant();
		try {
			c.setID("aa");
			ul.setID("ab");
			u.setWeeklyPick(c);
			u.setUltimatePick(ul);
		} catch (InvalidFieldException e1) {
			e1.printStackTrace();
		}

		try {
			System.out.println(u.toJSONObject().toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
}
