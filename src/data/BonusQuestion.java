package data;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * BonusQuestion is the class that will deal with the numerous bonus questions
 * that users can answer at their leisure during the competition.
 * 
 * @author Graem Littleton, Kevin Brightwell, Jonathan Demelo, Ramesh Raj, Justin McDonald
 *
 */

public abstract class BonusQuestion {

	private String type;
	// What object type is "prompt"?
	private String answer;
	private boolean active;
	
	// ----------------- ACCESSOR METHODS ----------------- //
	
	/**
	 * getAnswer is to be implemented by both the ShortAnswer and 
	 * MultipleChoice classes. It will return the correct answer to the Admin
	 * when a User has attempted to answer a question.
	 */
	
	public abstract String getAnswer();
	
	// I'm also not sure what the difference between the checkAnswer and getAnswer methods
	// is. Why just check it when we can get it and skip over that step? Unless I'm
	// misinterpreting it. 
	
	// I just wrote String here because I wasn't sure. 
	// This is just a palceholder method afterall.
	public String getPrompt()
	{
		throw new NotImplementedException();
	}
	
	/**
	 * getType returns the type of question that is to be asked, which can be either
	 * multiple choice of short answer.
	 * 
	 * @return this.type
	 */
	public String getType()
	{
		throw new NotImplementedException();
	}
	
	/**
	 * isActive returns true if the user is still able to answer a specific bonus
	 * question.
	 * 
	 * @return this.active
	 */
	public boolean isActive()
	{
		throw new NotImplementedException();
	}
	
	
}
