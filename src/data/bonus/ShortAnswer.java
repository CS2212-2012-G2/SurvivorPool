package data.bonus;

/**
 * ShortAnswer.java represents the case that a bonus question is of type short
 * answer.
 * 
 * @author Graem Littleton, Ramesh Raj, Justin McDonald, Jonathan Demelo, Kevin
 *         Brightwell
 * 
 */
public class ShortAnswer extends BonusQuestion {
	
	public ShortAnswer(String prompt, String answer, boolean active) {
		this.prompt = prompt;
		this.answer = answer;
		this.active = active;
		
		type = BonusQuestion.TYPE_SHORT;
	}

}
