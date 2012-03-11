package data;

import data.me.json.*;
import common.Utils;


/**
 * The contestant class will be used to create a person who will be competing in
 * the actual game of survivor, and can be chosen by people from the User class.
 * 
 * @author Kevin Brightwell, Jonathon Demelo, Graem Littleton, Justin McDonald,
 * 			Ramesh Raj 
 */

public class Contestant implements Person {

	// player information
	protected String firstName, lastName, tribe, picture;
	protected int castDate = -1; // week that player was cast off
	protected String cID;
	
	protected final static String KEY_FIRST_NAME = "first";
	protected final static String KEY_LAST_NAME	= "last";
	protected final static String KEY_ID	= "id";
	protected final static String KEY_PICTURE = "picture";
	protected final static String KEY_TRIBE = "tribe";
	protected final static String KEY_DATE = "date";

	/**
	 * Constructor method for type contestant sets player info
	 * @param _id ID tag
	 * @param first
	 *            first name
	 * @param last
	 *            last name
	 * @param _tribe
	 *            contestant's tribe
	 */
	public Contestant(String _id, String first, String last, String _tribe) 
			throws InvalidFieldException {
		setID(_id);
		setFirstName(first);
		setLastName(last);
		setTribe(_tribe);
	}
	
	public Contestant() {
		castDate = -1;
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
	 * isCastOff returns the contestant's activity status
	 * 
	 * @return this.castOff
	 */
	public boolean isCastOff() {
		return castDate > -1;
	}

	// ----------------- MUTATOR METHODS -----------------//

	/**
	 * castOff indicates that a contestant has been removed from the show
	 */
	public void castOff() {
		castDate = GameData.getCurrentGame().getCurrentWeek();
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
	 * setTribe sets the contestant's current tribe
	 * 
	 * @param name
	 *            name of new tribe
	 * @throws InvalidFieldException If tribe is not used.
	 */
	public void setTribe(String name) throws InvalidFieldException {
		String[] s = GameData.getCurrentGame().getTribeNames();
		
		if (!name.equals(s[0]) && !name.equals(s[1])) {
			throw new InvalidFieldException("Invalid Tribe.");
		}
		
		tribe = name;
	}

	/**
	 * Sets the contestant's first name.
	 * @param name	New first name of the contestant, must be alphabetic, 
	 * 		between 1 and 20 chars.
	 * @throws InvalidFieldException 
	 */
	public void setFirstName(String name) throws InvalidFieldException {
		
		if (!Utils.checkString(name,REGEX_FIRST_NAME))
			throw new InvalidFieldException("Invalid First Name");
		
		firstName = name;
		
	}

	/**
	 * Sets the contestant's last name.
	 * @param name New last name of the contestant
	 */
	public void setLastName(String name) throws InvalidFieldException {
		if (!Utils.checkString(name,REGEX_LAST_NAME))
			throw new InvalidFieldException("Invalid Last Name");
		
		lastName = name;
	}

	/**
	 * Returns the tribe of the Contestant
	 * @return Tribe name of the contestant.
	 */
	public String getTribe() {
		return tribe;
	}

	/**
	 * Returns the date the contestant was cast off on. 
	 * @return Date cast off, -1 if still active.
	 */
	public int getCastDate() {
		return castDate;
	}

	/**
	 * Returns the contestant's unique ID tag
	 * @return assigned ID tag
	 */
	public String getID() {
		return cID;
	}
	
	/**
	 * Sets the user ID to the passed ID, checks that is is of the correct 
	 * format
	 * @param newID the new User ID, must adhere to correct syntax.
	 * @throws InvalidFieldException 
	 */
	public void setID(String newID) throws InvalidFieldException {
		newID = newID.toLowerCase();
		if (!Utils.checkString(newID,REGEX_CONTEST_ID))
			throw new InvalidFieldException("Invalid contestant ID");
		cID = newID;
	}
	
	public void setCastDate(int date) {
		castDate = date;
	}
	
	public void setCastDate(Integer date) {
		setCastDate(date.intValue());
	}
	
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
			castDate = c.getCastDate();
		}
	}
	
	/**
	 * toString returns a string of the contestant's information in JSON format.
	 */
	public String toString() {
		return new String("Contestant<FN: " + "\"" + firstName + "\"" + ", LN: " + "\"" + lastName + "\"" + 
				", Tribe: " + "\"" + tribe + "\"" + ", ID: " + "\"" + cID + "\">");
	}
	
	public JSONObject toJSONObject() throws JSONException {
		JSONObject obj = new JSONObject();
		
		obj.put(KEY_FIRST_NAME, getFirstName());
		obj.put(KEY_LAST_NAME, getLastName());
		obj.put(KEY_ID, getID());
		obj.put(KEY_PICTURE, getPicture());
		obj.put(KEY_TRIBE, getTribe());
		obj.put(KEY_DATE, new Integer(getCastDate()));
		System.out.println(obj.get(KEY_TRIBE)+"a");
		return obj;
	}
	
	
	// TODO: DOC THESE
	
	public String toJSONString() throws JSONException {
		return toJSONObject().toString();
	}
	
	public void fromJSONString(String json) throws JSONException{
		JSONObject o = new JSONObject(json);
		
		fromJSONObject(o);
	}
	
	public void fromJSONObject(JSONObject o) {
		try {
			setID((String)o.remove(KEY_ID));
			setFirstName((String)o.remove(KEY_FIRST_NAME));
			setLastName((String)o.remove(KEY_LAST_NAME));
			setTribe((String)o.remove(KEY_TRIBE));
			setPicture((String)o.remove(KEY_PICTURE));
			setCastDate(((Integer)o.remove(KEY_DATE)).intValue());
		} catch (InvalidFieldException e) {
			System.out.println("Warning: InvalidFieldException in fromJSONObject");
			System.out.println(e.getMessage());
		}
	}
	
	/// Driver for Contestant JSON 
		public static void main(String[] args) throws InvalidFieldException {
			Contestant c = new data.Contestant("ad", "Jon", "silver", "booby");
			
			try {
				System.out.println(c.toJSONString());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				Contestant p = new data.Contestant();
				p.fromJSONString(c.toJSONString());
				System.out.println(p);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
