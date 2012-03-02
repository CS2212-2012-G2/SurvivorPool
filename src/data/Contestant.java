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
	 * 
	 * @param _id
	 * 			  The ID of the contestant, should be maintained from the game
	 * @param first
	 *            first name
	 * @param last
	 *            last name
	 * @param tribe
	 *            contestant's tribe
	 */

	public Contestant(String first, String last, String _tribe) {
		firstName = first;
		lastName = last;
		tribe = _tribe;
	}

	// ------------------ ACCESSOR METHODS -----------------//

	public Contestant() {
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
		return castDate == -1;
	}

	// ----------------- MUTATOR METHODS -----------------//

	/**
	 * castOff indicates that a contestant has been removed from the show
	 */

	public void castOff() {
		// TODO: Implement:
		//castDate = getGameData.date();
		// weekCastOff = game.weeksLeft();
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
	 * @param name	New first name of the contestant
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
			return (c1.getFirstName().compareTo(c2.getFirstName()));
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
			return (c1.getLastName().compareTo(c2.getLastName()));
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
			return (c1.getLastName().compareTo(c2.getLastName()));
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
	
	
	/**
	 * Uses the stored first and last name, and the currently running Game to 
	 * generate a unique user ID.
	 */
	// XXX: Could be abstracted into GameData?
	public void generateID() {
		// TODO Auto-generated method stub
		
	}
}
