/**
 * 
 */
package admin.panel.person;

import data.InvalidFieldException;
import data.Person;

/**
 * Interface used to amalgamate the Player/Contestant Field objects.
 * 
 * @author Kevin Brightwell (@Nava2)
 *
 */
public interface PersonFields<P extends Person> {
	
	/**
	 * Sets the panel to store the information in the edit pane.
	 * @param p
	 */
	public void setEditPane(P p, boolean newPerson);
	
	/**
	 * Gets the information in the pane and stores it into the passed object.
	 * @param p
	 * @throws InvalidFieldException 
	 */
	public void getFromPane(P p) throws InvalidFieldException;
}
