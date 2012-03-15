/**
 * 
 */
package admin;

/**
 * @author kevin
 *
 */
public interface GameDataDependant {

	/**
	 * Gets all fields that depend on GameData to refresh their values.
	 */
	public void refreshGameFields();
	
	
}
