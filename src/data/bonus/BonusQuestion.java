package data.bonus;


/**
 * BonusQuestion is the class that will deal with the numerous bonus questions
 * that users can answer at their leisure during the competition.
 * 
 * @author Graem Littleton, Kevin Brightwell, Jonathan Demelo, Ramesh Raj,
 *         Justin McDonald
 * 
 */

public class BonusQuestion {
	/**
	 * Used to show a question is a multiple choice question.
	 */
	public static int TYPE_MULTI = 0;
	
	/**
	 * Used to show a question is a short answer question.
	 */
	public static int TYPE_SHORT = 1;
	
	protected String prompt;
	protected boolean active;
	protected String answer;

	protected int type;
	
	public BonusQuestion() {
		System.out.println("Not implemented");
	}

	// ----------------- ACCESSOR METHODS ----------------- //

	/**
	 * getAnswer is to be implemented by both the ShortAnswer and MultipleChoice
	 * classes. It will return the correct answer to the Admin when a User has
	 * attempted to answer a question.
	 */
	public String getAnswer() {
		return this.answer;
	}

	// I'm also not sure what the difference between the checkAnswer and
	// getAnswer methods
	// is. Why just check it when we can get it and skip over that step? Unless
	// I'm
	// misinterpreting it.

	// I just wrote String here because I wasn't sure.
	// This is just a palceholder method afterall.

	/**
	 * getType returns the type of question that is to be asked, which can be
	 * either multiple choice of short answer.
	 * 
	 * @return this.type
	 */
	public int getType() {
		return this.type;
	}

	/**
	 * isActive returns true if the user is still able to answer a specific
	 * bonus question.
	 * 
	 * @return this.active
	 */
	public boolean isActive() {
		return active;
	}
	
	/**
	 * Sets the active state to the value passed.
	 * @param active	The new state of the question.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	/**
	 * Checks whether an answer is correct against a stored value. Does this 
	 * case insensitively.
	 * @param test The answer to test with.
	 * @return True if correct.
	 */
	public boolean checkAnswer(String test) {
		return test.equalsIgnoreCase(this.answer);
	}	
}
