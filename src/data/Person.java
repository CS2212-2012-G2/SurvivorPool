
package data;

/**
 * Person interface will be implemented by both the User and Contestant classes.
 * 
 * @author Kevin Brightwell
 */
public interface Person {
	
	/**
	 * Regular expression keys that will be used to ensure the validity of information entered.
	 * Keys cover Contestant ID's, Player ID's, Contestant/Player names
	 * and tribe names.
	 */

	public final static String REGEX_CONTEST_ID = "^[A-z\\d]{2}$",
								REGEX_PLAYER_ID = "^[A-z]{2,7}[\\d]*$",
								REGEX_FIRST_NAME = "^[A-z]{1,20}$",
								REGEX_LAST_NAME = "^[A-z\\s]{1,20}$",
								TRIBE_PATTERN = "^[A-z\\s]{1,30}$";
	
	/**
	 * Returns a unique ID.
	 * @return String   unique id
	 */

	public String getID();
	
	/**
	 * Returns a person's first name.
	 * @return String   first name
	 */

	public String getFirstName();
	
	/**
	 * Returns a person's last name.
	 * @return String    last name
	 */

	public String getLastName();
	
	/**
	 * Used to set a person's first name.  Throws invalid field exception if it does not
	 * pass the REGEX check.
	 * @param name   first name
	 * @throws InvalidFieldException
	 */

	public void setFirstName(String name) throws InvalidFieldException;
	
	/**
	 * Used to set a person's last name.  Throws invalid field exception if it does not
	 * pass the REGEX check.
	 * @param name   last name
	 * @throws InvalidFieldException
	 */

	public void setLastName(String name) throws InvalidFieldException;
	
	/**
	 * Used to set a person's unique ID.  Throws invalid field exception if it does not
	 * pass the REGEX check.
	 * @param name   unique ID
	 * @throws InvalidFieldException
	 */

	public void setID(String id) throws InvalidFieldException;
}
