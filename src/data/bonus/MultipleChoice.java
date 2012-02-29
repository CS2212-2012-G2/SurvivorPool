package data.bonus;

/**
 * MultipleChoice.java represents the case that a bonus question is of type
 * multiple choice.
 * 
 * @author Graem Littleton, Ramesh Raj, Justin McDonald, Jonathan Demelo, Kevin
 *         Brightwell
 * 
 */
public class MultipleChoice extends BonusQuestion {

	
	
	private String[] options; // stores the choices that the user can make

	/**
	 * Creates a new object with the specified parameters.
	 * @param prompt	New prompt
	 * @param answer	New answer
	 * @param options	New options
	 * @param active	Whether the question is active
	 */
	public MultipleChoice(String prompt, String answer, String[] options,
			boolean active) {
		this.prompt = prompt;
		this.answer = answer;
		this.options = options;

		type = BonusQuestion.TYPE_MULTI;
	}
	
	/**
	 * getOptions returns the list of options to the caller.
	 * 
	 * @return this.options
	 */
	public String[] getOptions() {
		return options;
	}
}
