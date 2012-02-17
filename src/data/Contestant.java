package data;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * The contestant class will be used to create a person who
 * will be competing in the actual game of survivor, and can be
 * chosen by people from the User class.
 * 
 * @author Graem Littleton, Justin McDonald, Ramesh Raj, Kevin Brightwell, Jonathan Demelo
 */


public class Contestant {
	
	// player information
	private String firstName, lastName, tribe, picture;
	private boolean castOff; // true when player is no longer active
    private int weekCastOff; // week that player was cast off
    private GameData game;
	/**
	 * Constructor method for type contestant
	 * sets player info
	 * 
	 * @param first     first name
	 * @param last      last name
	 * @param tribe     contestant's tribe
	 */
	
	public Contestant(String first, String last, String tribe)
	{	
		throw new NotImplementedException();
	}
	
	// ------------------ ACCESSOR METHODS -----------------//
	
	/**
	 * getFirstName returns the contestant's first name
	 * @return this.firstName
	 */
	
	public String getFirstName()
	{
		throw new NotImplementedException();
	}
	
	/**
	 * getLastName returns the contestant's lats name
	 * @return this.lastName
	 */
	
	public String getLastName()
	{
		throw new NotImplementedException();
	}
	
	/**
	 * getPicture returns the contestant's picture information
	 * @return this.picture
	 */
	
	public String getPicture()
	{
		throw new NotImplementedException();
	}
	
	/**
	 * isCastOff returns the contestant's activity status
	 * @return this.castOff
	 */
	
	public boolean isCastOff()
	{
		throw new NotImplementedException();
	}
	
	
	// ----------------- MUTATOR METHODS -----------------//
	
	/**
	 * castOff indicates that a contestant has been removed from
	 * the show
	 */
	
	public void castOff()
	{	
		throw new NotImplementedException();
	}
	
	
	/**
	 * setPicture sets a contestant's picture
	 * @param pic           
	 */
	public void setPicture(String pic)
	{
		throw new NotImplementedException();
	}
	
	/**
	 * setTribe sets the contestant's current tribe
	 * 
	 * @param name  name of new tribe
	 */
	
	public void setTribe(String name)
	{
		throw new NotImplementedException();
	}
}
