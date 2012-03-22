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
 * @author Ramesh
 * 
 */
public class Bonus extends Observable {

	static List<BonusQuestion> questions = new ArrayList<BonusQuestion>();

	private static final String KEY_QUESTIONS = "questions";
	public static final String filePath = "res/data/bonus.dat";
	static Comparator<BonusQuestion> comp =new Comparator<BonusQuestion>(){

		public int compare(BonusQuestion o1, BonusQuestion o2) {
			return o1.getWeek()-o2.getWeek();
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
	 * @return a possible null BonusQuestion
	 */
	public static BonusQuestion getQuestionByWeek(int week) {
		int min = 0;
		int max = questions.size();
		while(min<=max){
			int middle = (min+max)/2;
			BonusQuestion b = questions.get(middle);
			if(b.getWeek()==week)
				return b; 
			else if(b.getWeek()>week)
				max=middle-1;
			else
				min=middle+1;
		}
		
		return null;
	}
	
	/**
	 * Get the question that was asked on a certain week
	 * 
	 * @param week
	 * @return a possible null BonusQuestion
	 */
	public static BonusQuestion getQuestionByWeekAndNumber(int week, int number) {
		for (int i = 0; i < questions.size(); i++) {
			BonusQuestion b = questions.get(i);
			System.out.println("Week " + b.getWeek());
			System.out.println("Week Searched " + week);
			System.out.println("Number " + b.getNumber());
			System.out.println("Number Searched " + number);
			if (b.getWeek() == week && b.getNumber() == number){
				System.out.println("pass");
				return b;				
			}
		}
		return null;
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
			fromJSONObject(JSONUtils.readFile(filePath));
		} catch (FileNotFoundException e) {
			System.out.println("could not read "+filePath);
		} catch (ParseException	e) {
			System.out.println("could not convert to json object "+filePath);
			e.printStackTrace();
		}
	}
	
	public static void writeData() {
		try {
			JSONUtils.writeJSON(filePath, toJSONObject());
		} catch (ParseException p) {
			p.printStackTrace();
		}
	}

}