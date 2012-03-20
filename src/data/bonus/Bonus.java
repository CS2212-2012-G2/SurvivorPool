package data.bonus;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import data.JSONUtils;
import data.me.json.JSONArray;
import data.me.json.JSONException;
import data.me.json.JSONObject;

/**
 * This class holds all the bonus questions
 * 
 * @author Ramesh
 * 
 */
public class Bonus {

	static ArrayList<BonusQuestion> questions = new ArrayList<BonusQuestion>();

	private static final String KEY_QUESTIONS = "questions";
	public static final String filePath = "res/data/bonus.dat";

	/**
	 * DO NOT CALL THIS FUNCTION! Only used fromJSONObject or when a
	 * bonusquestion is created
	 * 
	 * @param b
	 */
	public static void addNewQuestion(BonusQuestion b) {
		questions.add(b);
		// TODO: should sort it so get by week is quicker
	}

	/**
	 * Get all the questions. Probably not as useful as get by week
	 * 
	 * @return
	 */
	public static ArrayList<BonusQuestion> getAllQuestions() {
		return questions;
	}

	/**
	 * Get the question that was asked on a certain week
	 * 
	 * @param week
	 * @return a possible null BonusQuestion
	 */
	public static BonusQuestion getQuestionByWeek(int week) {
		for (BonusQuestion b : questions) {
			if (b.getWeek() == week)
				return b;
		}
		return null;

	}

	public static JSONObject toJSONObject() throws JSONException {
		JSONObject obj = new JSONObject();

		JSONArray qA = new JSONArray();
		for (BonusQuestion b : questions) {
			qA.put(b.toJSONObject());
		}
		obj.put(KEY_QUESTIONS, qA);
		return obj;
	}

	public static void fromJSONObject(JSONObject o) throws JSONException {
		if(o==null)
			return;
		JSONArray qA = o.getJSONArray(KEY_QUESTIONS);
		for (int i = 0; i < qA.length(); i++) {
			BonusQuestion b = new BonusQuestion();
			b.fromJSONObject(qA.getJSONObject(i));
			addNewQuestion(b);
		}
	}
	
	/**
	 * Initalize bonus
	 */
	public static void initBonus(){
		try {
			fromJSONObject(JSONUtils.readFile(filePath));
		} catch (FileNotFoundException e) {
			System.out.println("could not read "+filePath);
		} catch (JSONException e) {
			System.out.println("could not convert to json object "+filePath);
			e.printStackTrace();
		}
	}
}
