/**
 * 
 */
package data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kevin
 *
 */
public class StringUtil {

	/**
	 * Uses the stored first and last name, and the currently running Game to 
	 * generate a unique user ID.
	 * @param prsn person to use data from.
	 * @param data Data to check through
	 */
	public static String generateID(Person prsn, List<Person> data) {
		if (prsn.getLastName() == null ||
				prsn.getFirstName() == null) {
			System.out.println("generateContestantID: first or last name null");
			return new String();
		}
		
		ArrayList<String> ids = new ArrayList<String>(data.size());
		for (Person p: data)
			if (p.getID() != null)
				ids.add(p.getID());
		
		String newID;
		String lastName = prsn.getLastName().toLowerCase().replaceAll("\\s+","");
		String firstName = prsn.getFirstName().toLowerCase();
		int lastSub = Math.min(6, lastName.length());
		int num = 0;
		boolean valid = false;
		
		do {
			// take the first letter of first name
			// take substring of lastName length 6 or full name
			newID = firstName.charAt(0) 
					+ lastName.substring(0, lastSub);
			if (num != 0) {
				newID += Integer.toString(num);
			}
			num++;
			
			valid = true;
			for (String id: ids) 
				if (id.equalsIgnoreCase(newID)) {
					valid = false;
					break;
				}	
		} while (!valid); // check if the ID is present
		
		System.out.println("Generated: " + newID);
		return newID;
	}
	
}
