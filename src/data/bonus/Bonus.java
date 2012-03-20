package data.bonus;

import java.util.ArrayList;

/**
 * This class holds all the bonus questions
 * @author Ramesh
 *
 */
public class Bonus {

	static ArrayList<BonusQuestion> questions = new ArrayList<BonusQuestion>();
	
	/**
	 * DO NOT CALL THIS FUNCTION! IT IS CALLED AUTOMATICALLY BY BONUSQUESTION
	 * @param b
	 */
	public static void addNewQuestion(BonusQuestion b){
		questions.add(b);
	}
	
	/**
	 * Get all the questions. Probably not as useful as get by week
	 * @return
	 */
	public static ArrayList<BonusQuestion> getAllQuestions(){
		return questions;
	}
	
	/**
	 * Get the question that was asked on a certain week
	 * @param week 
	 * @return a possible null BonusQuestion
	 */
	public static BonusQuestion getQuestionByWeek(int week){
		for(BonusQuestion b: questions){
			if(b.getWeek()==week)
				return b;
		}
		return null;
		
	}
}
