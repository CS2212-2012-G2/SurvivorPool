package data;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * The user class will be used to create an individual who will be participating in the 
 * survivor pool.
 * 
 * @author Graem Littleton, Kevin Brightwell, Jonathan Demelo, Ramesh Raj, Justin McDonald
 */

public class User {
	
	private String firstName, lastName, unID; // first and last names and unique ID (UWO ID format) 
    private int points, winPoints; // point total, points user receives if that pick wins
    private Contestant winPick, weeklyPick; // users pick of the winner and their weekly pick
    private GameData game; // game information
    
    
    /**
     * Constructor method for the type User
     * sets names, initializes points
     * 
     * @param first    first name
     * @param last     last name
     * @param id       unique ID
     */
    
    public User(String first, String last, String id)
    {
    	throw new NotImplementedException();
    }
    
    // -------------------- ACCESSOR METHODS ------------------ //
    
    /**
     * getFirstName returns the first name of the user
     * @return this.firstName   
     */
    
    public String getFirstName()
    {
    	throw new NotImplementedException();
    }
    
    /**
     * getLastName returns the users last name
     * @return this.lastName
     */
    
    public String getLastName()
    {
    	throw new NotImplementedException();
    }
    
    /**
     * getID returns the users unique ID
     * @return this.unID
     */
    
    public String getID()
    {
    	throw new NotImplementedException();
    }
    
    /**
     * getWeeklyPick returns the users pick for which contestant will be eliminated
     * @ return this.weeklyPick
     */
    
    public Contestant getWeeklyPick()
    {
    	throw new NotImplementedException();
    }
    
    /**
     * getGame returns the game information that the user is associated with
     * @return this.game
     */
    
    public GameData getGame()
    {
    	throw new NotImplementedException();
    }
    
    /**
     * getWinPick returns the users selection for which contestant will win the game
     * @return this.winPick
     */
    
    public Contestant getWinPick()
    {
    	throw new NotImplementedException();
    }
    
    
    
    
    // ---------------- MUTATOR METHODS ----------------- //
    
    /**
     * addPoints add points to a users current total 
     * 
     * @param newPoints   points to be added
     */
    
    public void addPoints(int newPoints)
    {
    }
  
    /**
     * setFirstName sets the users first name
     * 
     * @param first    users first name
     */
    
    public void setFirstName(String first)
    {
    }
    
    /**
     * setGame sets the assigns the desired GameData object to the user,
     * allowing him/her to follow the same game as all the others.
     * 
     * @param comp   the GameData being set
     */
    
    public void setGame(GameData comp)
    {
    }
    
    /**
     * setFirstName sets the users last name
     * 
     * @param last    users last name
     */
    
    public void setLastName(String last)
    {
    }
    
    /**
     * setWeeklyPick sets the users pick for which contestant
     * will be eliminated
     * 
     * @param pick   contestant choice
     */
    
    public void setWeeklyPick(Contestant pick)
    {
    }
    
    /**
     * setWinPick sets the users choice for which contestant will
     * win the competition. Also determines how many points the
     * user will receive if that player wins
     * 
     * @param winner   contestant choice
     */
    
    public void setWinPick(Contestant winner)
    {
    }
    
    
    // ----------------- HELPER METHODS ----------------- //
  
   
}
