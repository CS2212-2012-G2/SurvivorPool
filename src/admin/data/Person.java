/**
 * 
 */
package admin.data;

/**
 * @author kevin
 *
 */
public interface Person {
	
	public final static String REGEX_CONTEST_ID = "^[a-z]{2,7}[\\d]*$";
	public final static String REGEX_FIRST_NAME = "^[A-z]{1,20}$";
	public final static String REGEX_LAST_NAME  = "^[A-z\\s]{1,20}$";
	
	public String getID();
	
	public String getFirstName();
	
	public String getLastName();
	
	public void setFirstName(String name);
	
	public void setLastName(String name);
	
	public void setID(String id);
}
