package data;

import json.simple.JSONObject;
import json.simple.parser.ParseException;
import admin.Utils;

/**
 * The contestant class will be used to create a person who will be competing in
 * the actual game of survivor, and can be chosen by people from the User class.
 * 
 * @author Kevin Brightwell, Jonathon Demelo, Graem Littleton, Ramesh Raj
 */

public class Contestant implements Person, Comparable<Contestant> {

	// player information
	protected String firstName, lastName, tribe, picture;
	protected int castDate = -1; // week that player was cast off
	protected String cID;
	private boolean toBeCast, isNull = false, picked;

	public final static String NULL_ID = "??";

	/**
	 * Keys represent the string that is to be stored and fetched when writing to a JSON object
	 * and reading one in.
	 * 
	 *                          KEY_FIRST_NAME = "first
	 *							KEY_LAST_NAME = "last",
	 *							KEY_ID = "id",
	 *							KEY_PICTURE = "picture",
     *							KEY_TRIBE = "tribe",
	 *							KEY_DATE = "date",
	 *							KEY_CHOSEN = "chosen",
	 */
	protected final static String KEY_FIRST_NAME = "first",
	 							KEY_LAST_NAME = "last",
	 							KEY_ID = "id",
	 							KEY_PICTURE = "picture",
     							KEY_TRIBE = "tribe",
	 							KEY_DATE = "date",
	 							KEY_CHOSEN = "chosen";

	/**
	 * Constructor method for type contestant sets player info
	 *
	 * @param _id            ID tag
	 * @param first          first name
	 * @param last           last name
	 * @param _tribe         contestant's tribe
	 */
	public Contestant(String _id, String first, String last, String _tribe)
			throws InvalidFieldException {
		setID(_id);
		setFirstName(first);
		setLastName(last);
		setTribe(_tribe);
		picked = false;
	}
	/**
	 * Constructor with no parameters.  Simply sets castDate to the uncast and
	 * picked to false.
	 */

	public Contestant() {
		castDate = -1;
		picked = false;
	}

	// ------------------ ACCESSOR METHODS -----------------//

	/**
	 * getFirstName returns the contestant's first name
	 * 
	 * @return this.firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * getLastName returns the contestant's lats name
	 * 
	 * @return this.lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * getPicture returns the contestant's picture information
	 * 
	 * @return this.picture
	 */
	public String getPicture() {
		return picture;
	}
	
	/**
	 * Returns whether or not a contestant has been chosen as a weekly or ultimate pick.
	 * 
	 * @return this.picked
	 */
	
	public boolean beenPicked(){
		return picked;
	}

	/**
	 * isCastOff returns the contestant's activity status
	 * 
	 * @return this.castOff
	 */
	public boolean isCastOff() {
		return castDate > -1;
	}

	/**
	 * Checks if a Contestant is null.
	 * 
	 * @return True if isNull is set, and ID is NULL_ID.
	 */
	public boolean isNull() {
		return isNull && getID().equals(NULL_ID);
	}


	/**
	 * Returns the tribe of the Contestant
	 * 
	 * @return Tribe name of the contestant.
	 */
	public String getTribe() {
		return tribe;
	}

	/**
	 * Returns the date the contestant was cast off on.
	 * 
	 * @return Date cast off, -1 if still active.
	 */
	public int getCastDate() {
		return castDate;
	}

	/**
	 * Returns the contestant's unique ID tag
	 * 
	 * @return assigned ID tag
	 */
	public String getID() {
		return cID;
	}

	// ----------------- MUTATOR METHODS -----------------//

	/**
	 * @return the toBeCast
	 */
	public boolean isToBeCast() {
		return toBeCast;
	}
	
	/**
	 * Sets a contestant's cast date.
	 */
	public void setCastDate(int date) {
		castDate = date;
	}
	
	/**
	 * castOff indicates that a contestant has been removed from the show
	 */
	public void castOff() {
		GameData g = GameData.getCurrentGame();
		castDate = g.getCurrentWeek();
		g.setElimCont(null);
		g.setElimExists(false);
	}
	
	/**
	 * Sets the contestants picked status to true.
	 */
	public void selected(){
		this.picked = true;
	}
	
	/**
	 * Sets the contestant's first name.
	 * 
	 * @param name
	 *            New first name of the contestant, must be alphabetic, between
	 *            1 and 20 chars.
	 * @throws InvalidFieldException
	 */
	public void setFirstName(String name) throws InvalidFieldException {
		name = name.trim();
		if (!Utils.checkString(name, REGEX_FIRST_NAME))
			throw new InvalidFieldException(
					InvalidFieldException.Field.CONT_FIRST,
					"Invalid First Name");

		firstName = Utils.strCapitalize(name);

	}
	
	/**
	 * Sets the user ID to the passed ID, checks that is is of the correct
	 * format
	 * 
	 * @param newID
	 *            the new User ID, must adhere to correct syntax.
	 * @throws InvalidFieldException
	 */
	public void setID(String newID) throws InvalidFieldException {
		newID = newID.toLowerCase().trim();
		if (!Utils.checkString(newID, REGEX_CONTEST_ID))
			throw new InvalidFieldException(
					InvalidFieldException.Field.CONT_ID,
					"Invalid contestant ID");

		cID = newID;
	}
	
	/**
	 * Sets the contestant's last name.
	 * 
	 * @param name
	 *            New last name of the contestant
	 */
	public void setLastName(String name) throws InvalidFieldException {
		name = name.trim();
		if (!Utils.checkString(name, REGEX_LAST_NAME))
			throw new InvalidFieldException(
					InvalidFieldException.Field.CONT_LAST, "Invalid Last Name");

		lastName = Utils.strCapitalize(name);
	}

	/**
	 * sets the contestant to null. 
	 */
	public void setNull() {
		cID = NULL_ID;
		isNull = true;
	}
	
	/**
	 * setPicture sets a contestant's picture
	 * 
	 * @param pic
	 */
	public void setPicture(String pic) {
		picture = pic;
	}
	
	/**
	 * @param toBeCast
	 *            the toBeCast to set
	 */
	public void setToBeCast(boolean toBeCast) {
		this.toBeCast = toBeCast;
	}
	
	/**
	 * setTribe sets the contestant's current tribe
	 * 
	 * @param name
	 *            name of new tribe
	 * @throws InvalidFieldException
	 *             If tribe is not used.
	 */
	public void setTribe(String name) throws InvalidFieldException {
		String[] s = GameData.getCurrentGame().getTribeNames();
		name = name.trim();

		if (!name.equalsIgnoreCase(s[0]) && !name.equalsIgnoreCase(s[1])) {
			throw new InvalidFieldException(
					InvalidFieldException.Field.CONT_TRIBE, "Invalid Tribe.");
		}

		tribe = Utils.strCapitalize(name);
	}

	
	// ----------------- HELPER METHODS ----------------- //
	

	/**
	 * Update current contestant with contestant in param
	 * 
	 * @param c
	 *            A contestant with values that you want to replace
	 * @throws InvalidFieldException
	 */
	public void update(Contestant c) throws InvalidFieldException {
		if (c.getFirstName() != null) {
			setFirstName(c.getFirstName());
		}

		if (c.getLastName() != null) {
			setLastName(c.getLastName());
		}

		if (c.getID() != null) {
			setID(c.getID());
		}

		if (c.getPicture() != null) {
			setPicture(c.getPicture());
		}

		if (c.getTribe() != null) {
			setTribe(c.getTribe());
		}

		if (c.isCastOff()) {
			setCastDate(c.getCastDate());
		}
	}

	/**
	 * toString returns a string of the contestant's information in JSON format.
	 */
	public String toDebugString() {
		return new String("Contestant<FN: " + "\"" + firstName + "\""
				+ ", LN: " + "\"" + lastName + "\"" + ", Tribe: " + "\""
				+ tribe + "\"" + ", ID: " + "\"" + cID + "\">");
	}

	/**
	 * Returns a string of the contestant's Last name, first name, and ID tag.
	 */
	public String toString() {
		if (!isNull)
			return String.format("[%s] %s, %s", getID(), getLastName(),
					getFirstName());
		else
			return "None";
	}

	// ----------------- JSON ----------------- //
	
	/**
	 * Converts Contestant object to a json object
	 * 
	 * @return a JSON object containing all the data needed
	 * @throws JSONException
	 */
	public JSONObject toJSONObject() throws ParseException {
		JSONObject obj = new JSONObject();

		obj.put(KEY_FIRST_NAME, getFirstName());
		obj.put(KEY_LAST_NAME, getLastName());
		obj.put(KEY_ID, getID());
		obj.put(KEY_PICTURE, getPicture());
		obj.put(KEY_TRIBE, getTribe());
		obj.put(KEY_DATE, getCastDate());
		obj.put(KEY_CHOSEN, beenPicked());
		return obj;
	}
	
	/**
	 * Converts a JSON object to a Contestant.
	 * 
	 * @param o   JSON object
	 */
	public void fromJSONObject(JSONObject o) {
		try {
			setID((String) o.remove(KEY_ID));
			setFirstName((String) o.remove(KEY_FIRST_NAME));
			setLastName((String) o.remove(KEY_LAST_NAME));
			setTribe((String) o.remove(KEY_TRIBE));
			setPicture((String) o.remove(KEY_PICTURE));
			setCastDate(Utils.numToInt(o.remove(KEY_DATE)).intValue());
		    if((Boolean)o.remove(KEY_CHOSEN) == true){
		    	selected();
		    }
		} catch (InvalidFieldException e) {
			System.out
					.println("Warning: InvalidFieldException in fromJSONObject");
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Compares two contestant objects.
	 * 
	 * @return 0      Contestant objects are equal (or null)
	 * @return -1     Contestant A is greater than Contestant B
	 * @return 1      Contestant B is greater than Contestant A
	 */
	@Override
	public int compareTo(Contestant otherC) {
		// ugly, but works. :)
		// account for null:
		if (isNull() && otherC.isNull()) {
			return 0;
		} else if (isNull() && !otherC.isNull()) {
			return -1;
		} else if (!isNull() && otherC.isNull()) {
			return 1;
		}
		
		// otherwise both are not null:
		int result = getID().compareToIgnoreCase(otherC.getID());
		if (result == 0) {
			result = getLastName().compareToIgnoreCase(otherC.getLastName());
			if (result == 0) {
				result = getFirstName().compareToIgnoreCase(otherC.getLastName());
				if (result == 0) {
					result = getTribe().compareTo(otherC.getTribe());
				}
			}
		}
		
		return result;
	}
	
	// ------------- Driver for Contestant JSON ------------//
	
	public static void main(String[] args) throws InvalidFieldException {
		Contestant c = new data.Contestant("ad", "Jon", "silver", "booby");

		try {
			System.out.println(c.toJSONObject().toString());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		try {
			Contestant p = new data.Contestant();
			p.fromJSONObject(c.toJSONObject());
			System.out.println(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
