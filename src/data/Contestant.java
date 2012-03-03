package data;

import java.util.Comparator;

/**
 * The contestant class will be used to create a person who will be competing in
 * the actual game of survivor, and can be chosen by people from the User class.
 * 
 * @author Kevin Brightwell, Jonathon Demelo, Graem Littleton, Justin McDonald,
 * 			Ramesh Raj 
 */

public class Contestant {

	// player information
	private String firstName, lastName, tribe, picture;
	private int castDate = -1; // week that player was cast off
	private String cID;

	/**
	 * Constructor method for type contestant sets player info
	 * @param _id ID tag
	 * @param first
	 *            first name
	 * @param last
	 *            last name
	 * @param _id
	 * 			  The ID of the contestant, should be maintained from the game
	 * @param tribe
	 *            contestant's tribe
	 */
	public Contestant(String _id, String first, String last, String _tribe) {
		setID(_id);
		setFirstName(first);
		setLastName(last);
		setTribe(_tribe);
	}

	// ------------------ ACCESSOR METHODS -----------------//

	public Contestant() {
		castDate = -2;
	}

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
	 */
	public void setTribe(String name) {
		tribe = name;
	}

	/**
	 * Sets the contestant's first name.
	 * @param name	New first name of the contestant, must be alphabetic, 
	 * 		between 1 and 20 chars.
	 */
	public void setFirstName(String name) {
		firstName = name;
		
	}

	/**
	 * Sets the contestant's last name.
	 * @param name New last name of the contestant
	 */
	public void setLastName(String name) {
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
	 */
	public void setID(String newID) {
		newID = newID.toLowerCase();
		if (newID.matches(GameData.REGEX_CONTEST_ID))
			cID = newID;
	}
	
	//////////////////
	/// SUBCLASSES
	//////////////////
	
	// TODO: Fix the javadocs
	/**
	 * Compares two Contestants by ID for sorting.
	 * @author Kevin Brightwell, Jonathon Demelo, Graem Littleton, 
	 * 			Justin McDonald, Ramesh Raj 
	 *
	 */
	public static class ComparatorID implements Comparator<Contestant> {
		@Override
		public int compare(Contestant c1, Contestant c2) {
			return (c1.getID().compareTo(c2.getID()));
		}
	}
	
	/**
	 * Compares two Contestants by ID for sorting.
	 * @author Kevin Brightwell, Jonathon Demelo, Graem Littleton, 
	 * 			Justin McDonald, Ramesh Raj 
	 *
	 */
	public static class ComparatorFirstName implements Comparator<Contestant> {
		@Override
		public int compare(Contestant c1, Contestant c2) {
			String f1 = c1.getFirstName().toLowerCase();
			String f2 = c2.getFirstName().toLowerCase();
			return (f1.compareTo(f2));
		}
	}
	
	/**
	 * Compares two Contestants by ID for sorting.
	 * @author Kevin Brightwell, Jonathon Demelo, Graem Littleton, 
	 * 			Justin McDonald, Ramesh Raj 
	 *
	 */
	public static class ComparatorLastName implements Comparator<Contestant> {
		@Override
		public int compare(Contestant c1, Contestant c2) {
			String l1 = c1.getLastName().toLowerCase();
			String l2 = c2.getLastName().toLowerCase();
			return (l1.compareTo(l2));
		}
	}
	
	/**
	 * Compares two Contestants by ID for sorting.
	 * @author Kevin Brightwell, Jonathon Demelo, Graem Littleton, 
	 * 			Justin McDonald, Ramesh Raj 
	 *
	 */
	public static class ComparatorTribe implements Comparator<Contestant> {
		@Override
		public int compare(Contestant c1, Contestant c2) {
			return (c1.getTribe().compareTo(c2.getTribe()));
		}
	}
	
	/**
	 * Compares two Contestants by ID for sorting.
	 * @author Kevin Brightwell, Jonathon Demelo, Graem Littleton, 
	 * 			Justin McDonald, Ramesh Raj 
	 *
	 */
	public static class ComparatorDate implements Comparator<Contestant> {
		@Override
		public int compare(Contestant c1, Contestant c2) {
			return (c1.getCastDate() - c2.getCastDate());
		}
	}
	
	public void update(Contestant c) {
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
}
