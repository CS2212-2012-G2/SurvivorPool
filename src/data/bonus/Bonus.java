package data.bonus;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;

import json.simple.JSONArray;
import json.simple.JSONObject;
import json.simple.parser.ParseException;
import data.JSONUtils;

/**
 * This class holds all the bonus questions
 * 
 * @author Ramesh Raj
 * 
 */
public class Bonus extends Observable {

	private static List<BonusQuestion> questions = new ArrayList<BonusQuestion>();

	private static final String KEY_QUESTIONS = "questions";
	
	private static final Comparator<BonusQuestion> comp = new Comparator<BonusQuestion>() {

		public int compare(BonusQuestion o1, BonusQuestion o2) {
			int weekDiff = o1.getWeek()-o2.getWeek();
			
			if (weekDiff==0) {
				return o1.getNumber()-o2.getNumber();
			}
			
			return weekDiff;
		}
	};;
	
	/**
	 * DO NOT CALL THIS FUNCTION! Only used fromJSONObject or when a
	 * bonusquestion is created
	 * 
	 * @param b
	 */
	public static void addNewQuestion(BonusQuestion b) {
		questions.add(b);
		sortQuestions();
	}
	
	private static void sortQuestions(){
		Collections.sort(questions, comp);
	}
	
	/**
	 * like it says.
	 */
	public static void deleteAllQuestions(){
		questions.clear();
	}
	
	/**
	 * Get all the questions. Probably not as useful as get by week
	 * 
	 * @return
	 */
	public static List<BonusQuestion> getAllQuestions() {
		return questions;
	}

	/**
	 * Get the question that was asked on a certain week
	 * 
	 * @param week
	 * @param number The question number in the given week.(Index starts at 0)
	 * @return a possible null BonusQuestion
	 */
	public static BonusQuestion getQuestion(int week, int number) {
		int loc=Collections.binarySearch(questions, new BonusQuestion(week,number), comp);
				
		if(loc<0)
			return null;
		return questions.get(loc);	
	}

	/**
	 * Get the number of questions in a particular week
	 * @param week
	 * @return t: total number of questions in a week
	 */
	public static int getNumQuestionsInWeek(int week) {
		int t = 0;
		for (int i = 0; i < questions.size(); i++) {
			BonusQuestion b = questions.get(i);
			if (b.getWeek() == week) t++;
		}
		return t;
	}
	
	public static JSONObject toJSONObject() throws ParseException {
		JSONObject obj = new JSONObject();

		JSONArray qA = new JSONArray();
		for (BonusQuestion b : questions) {
			qA.add(b.toJSONObject());
		}
		obj.put(KEY_QUESTIONS, qA);
		return obj;
	}

	public static void fromJSONObject(JSONObject o) throws ParseException {
		if(o==null)
		    return;
		JSONArray qA = (JSONArray)o.get(KEY_QUESTIONS);
		for (int i = 0; i < qA.size(); i++) {
			BonusQuestion b = new BonusQuestion();
			b.fromJSONObject((JSONObject)qA.get(i));
			addNewQuestion(b);
		}
	}
	
	/**
	 * Initalize bonus
	 */
	public static void initBonus(){
		try {
			fromJSONObject(JSONUtils.readFile(JSONUtils.pathBonus));
		} catch (FileNotFoundException e) {
			System.out.println("could not read "+JSONUtils.pathBonus);
		} catch (ParseException	e) {
			System.out.println("could not convert to json object "+JSONUtils.pathBonus);
			e.printStackTrace();
		}
	}
	
	public static void writeData() {
		try {
			JSONUtils.writeJSON(JSONUtils.pathBonus, toJSONObject());
		} catch (ParseException p) {
			p.printStackTrace();
		}
	}

}
