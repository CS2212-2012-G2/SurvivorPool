package data;

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
	private int points, winPoints; // point total, points user receives if that
									// pick wins
	private Contestant winPick, weeklyPick; // users pick of the winner and
											// their weekly pick
	private GameData game; // game information

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

	/**
	 * getGame returns the game information that the user is associated with
	 * 
	 * @return this.game
	 */
	public GameData getGame() {
		return this.game;
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
		winPoints = 2 * game.weeksLeft();
	}

	@Override
	public void setID(String id) {
		id = id.toLowerCase();
		if (id.matches(REGEX_CONTEST_ID))
			unID = id;
		
	}

	// ----------------- HELPER METHODS ----------------- //

}
