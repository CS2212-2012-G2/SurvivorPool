package data;

import common.Utils;

import admin.data.GameData;
import data.me.json.*;

/**
 * The user class will be used to create an individual who will be participating
 * in the survivor pool.
 * 
 * @author Graem Littleton, Kevin Brightwell, Jonathan Demelo, Ramesh Raj,
 *         Justin McDonald
 */

public class User implements Person {

	private String firstName, lastName, unID; // first and last names and unique
												// ID (UWO ID format)
	private int points; // point total, points user receives if that
									// pick wins
	protected int winPoints;
	
	private Contestant winPick, weeklyPick; // users pick of the winner and
										    // their weekly pick
	
	// JSON Keys:
	
	protected static final String KEY_ID = "id";
	protected static final String KEY_FIRST_NAME = "first";
	protected static final String KEY_LAST_NAME = "last";
	protected static final String KEY_POINTS = "curr_points";
	protected static final String KEY_WIN_PICK_POINTS = "win_points";
	protected static final String KEY_ULT_PICK_ID	= "ult_pick";
	protected static final String KEY_WEEKLY_PICK_ID = "week_pick";

	/**
	 * Constructor method for the type User sets names, initializes points
	 * 
	 * @param first
	 *            first name
	 * @param last
	 *            last name
	 * @param id
	 *            unique ID
	 */
	public User(String first, String last, String id) {
		firstName = first;
		lastName = last;
		unID = id;
		points = 0; // begin with 0 points

	}

	public User() {
		// TODO Auto-generated constructor stub
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
	 * getLastName returns the users last name
	 * 
	 * @return this.lastName
	 */
	public String getLastName() {
		return lastName;
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
	 * getWeeklyPick returns the users pick for which contestant will be
	 * eliminated @ return this.weeklyPick
	 */
	public Contestant getWeeklyPick() {
		return weeklyPick;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}


	/**
	 * getWinPick returns the users selection for which contestant will win the
	 * game
	 * 
	 * @return this.winPick
	 */
	public Contestant getWinPick()  {
		return winPick;
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
		if (!Utils.checkString(first, REGEX_FIRST_NAME))
			throw new InvalidFieldException("Invalid First Name (User)");
		firstName = first;
	}

	/**
	 * setFirstName sets the users last name
	 * 
	 * @param last
	 *            users last name
	 * @throws InvalidFieldException 
	 */
	public void setLastName(String last) throws InvalidFieldException {
		if (!Utils.checkString(last, REGEX_LAST_NAME))
			throw new InvalidFieldException("Invalid Last Name (User)");
		lastName = last;
	}

	/**
	 * setWeeklyPick sets the users pick for which contestant will be eliminated
	 * 
	 * @param pick
	 *            contestant choice
	 */
	public void setWeeklyPick(Contestant pick)  {
		weeklyPick = pick;
	}

	/**
	 * setWinPick sets the users choice for which contestant will win the
	 * competition. Also determines how many points the user will receive if
	 * that player wins
	 * 
	 * @param winner
	 *            contestant choice
	 */
	public void setWinPick(Contestant winner)  {
		winPick = winner;
		//winPoints = 2 * GameData.getCurrentGame().weeksLeft();
		winPoints=10;
	}
	
	// just sets the same as prior without setting pts.
	public void setWinPickNoSetPts(Contestant winner)  {
		winPick = winner;
	}
	
	// TODO: Doc
	public int getWinPoints() {
		return winPoints;
	}
	
	public void setWinPoints(int winPts) {
		winPoints = winPts;
	}

	public void setID(String id) throws InvalidFieldException {
		id = id.toLowerCase();
		if (Utils.checkString(id,REGEX_PLAYER_ID))
			unID = id;
		else 
			throw new InvalidFieldException("Invalid Player ID");
	}
	
	/**
	 * toString returns a string of the contestant's information in JSON format.
	 */
	public String toString() {
		return new String("User<FN: " + "\"" + firstName + "\"" + ", LN: " + "\"" + lastName + "\"" + 
				", Points: " + "\"" + points + "\"" + ", ID: " + "\"" + unID + "\">");
	}

	// ----------------- JSON ----------------- //
	
	


	public JSONObject toJSONObject() throws JSONException {
		JSONObject obj = new JSONObject();
		
		obj.put(KEY_FIRST_NAME, getFirstName());
		obj.put(KEY_LAST_NAME, getLastName());
		obj.put(KEY_ID, getID());
		obj.put(KEY_POINTS, getPoints());
		
		Contestant c = getWeeklyPick();
		if (c != null)
			obj.put(KEY_WEEKLY_PICK_ID, c.getID());
		else 
			obj.put(KEY_WEEKLY_PICK_ID, null);
		
		c = getWinPick();
		if (c != null) 
			obj.put(KEY_ULT_PICK_ID, c.getID());
		else
			obj.put(KEY_ULT_PICK_ID, null);
		
		obj.put(KEY_WIN_PICK_POINTS, new Integer(getWinPoints()));
		
		return obj;
	}
	
	public void fromJSONObject(JSONObject o) {
		try {
			setFirstName((String)o.remove(KEY_FIRST_NAME));
			setLastName((String)o.remove(KEY_LAST_NAME));
			setID((String)o.remove(KEY_ID));
			setPoints(((Integer)o.remove(KEY_POINTS)).intValue());
			
			String id = (String)o.remove(KEY_WEEKLY_PICK_ID);
			Contestant c = GameData.getCurrentGame().getContestant(id);
			setWeeklyPick(c);
			
			id = (String)o.remove(KEY_ULT_PICK_ID);
			c = GameData.getCurrentGame().getContestant(id);
			setWinPick(c);
			
			setPoints(((Integer)o.remove(KEY_WIN_PICK_POINTS)).intValue());
		} catch (InvalidFieldException e) {
			System.out.println("Warning: InvalidFieldException in fromJSONObject");
			System.out.println(e.getMessage());
		}

	}
	
	public static void main(String[] args) {
		User u = new User("bob", "builder", "bbuilde");
		Contestant c = new Contestant();
		Contestant ul = new Contestant();
		try {
			c.setID("aa");
			ul.setID("ab");
		} catch (InvalidFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		u.setWeeklyPick(c);
		u.setWinPick(ul);
		try {
			System.out.println(u.toJSONObject().toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
