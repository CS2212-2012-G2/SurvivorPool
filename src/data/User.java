package data;

import json.JSONAware;
import json.JSONObject;
import json.JSONValue;
import json.parser.ParseException;

/**
 * The user class will be used to create an individual who will be participating
 * in the survivor pool.
 * 
 * @author Graem Littleton, Kevin Brightwell, Jonathan Demelo, Ramesh Raj,
 *         Justin McDonald
 */

public class User implements Person, JSONAware {

	private String firstName, lastName, unID; // first and last names and unique
												// ID (UWO ID format)
	private int points, winPoints; // point total, points user receives if that
									// pick wins
	private Contestant winPick, weeklyPick; // users pick of the winner and
											// their weekly pick

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
	
	public void setPoints(Number n) {
		points = n.intValue();
	}

	/**
	 * getWinPick returns the users selection for which contestant will win the
	 * game
	 * 
	 * @return this.winPick
	 */
	public Contestant getWinPick() {
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
	 */
	public void setFirstName(String first) {
		firstName = first;
	}

	/**
	 * setFirstName sets the users last name
	 * 
	 * @param last
	 *            users last name
	 */
	public void setLastName(String last) {
		lastName = last;
	}

	/**
	 * setWeeklyPick sets the users pick for which contestant will be eliminated
	 * 
	 * @param pick
	 *            contestant choice
	 */
	public void setWeeklyPick(Contestant pick) {
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
	public void setWinPick(Contestant winner) {
		winPick = winner;
		winPoints = 2 * GameData.getCurrentGame().weeksLeft();
	}
	
	// TODO: Doc
	// just sets the same as prior without setting pts.
	public void setWinPickNoSetPts(Contestant winner) {
		winPick = winner;
	}
	
	public int getWinPoints() {
		return winPoints;
	}
	
	public void setWinPoints(int winPts) {
		winPoints = winPts;
	}
	
	public void setWinPoints(Number n) {
		setWinPoints(n.intValue());
	}

	@Override
	public void setID(String id) {
		id = id.toLowerCase();
		if (id.matches(REGEX_CONTEST_ID))
			unID = id;
		
	}

	// ----------------- JSON ----------------- //
	
	public static final String KEY_ID = "id";
	public static final String KEY_FIRST_NAME = "first";
	public static final String KEY_LAST_NAME = "last";
	public static final String KEY_POINTS = "curr_points";
	public static final String KEY_WIN_PICK_POINTS = "win_points";
	public static final String KEY_ULT_PICK_ID	= "ult_pick";
	public static final String KEY_WEEKLY_PICK_ID = "week_pick";
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		
		obj.put(KEY_FIRST_NAME, getFirstName());
		obj.put(KEY_LAST_NAME, getLastName());
		obj.put(KEY_ID, getID());
		obj.put(KEY_POINTS, new Integer(getPoints()));
		
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
	
	@Override
	public String toJSONString() {
		return toJSONObject().toJSONString();
	}
	
	public static User fromJSONString(String json) throws ParseException {
		JSONObject o = (JSONObject)JSONValue.parse(json);
		
		User u = new User();
		GameData g = GameData.getCurrentGame();
		
		u.setID((String)o.remove(KEY_ID));
		u.setFirstName((String)o.remove(KEY_FIRST_NAME));
		u.setLastName((String)o.remove(KEY_LAST_NAME));
		u.setPoints((Number)o.remove(KEY_POINTS));
		u.setWeeklyPick(g.getContestant((String)o.remove(KEY_WEEKLY_PICK_ID)));
		u.setWinPickNoSetPts(g.getContestant((String)o.remove(KEY_ULT_PICK_ID)));
		u.setWinPickPts((Number)o.remove(KEY_WIN_PICK_POINTS));
		
		return u;
	}

	private void setWinPickPts(Number n) {
		winPoints = n.intValue();
		
	}
	
	public static void main(String[] args) {
		User u = new User("bob", "builder", "bbuilde");
		
		System.out.println(u.toJSONString());
		
		try {
			User p  = User.fromJSONString(u.toJSONString());
			System.out.println(p);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
