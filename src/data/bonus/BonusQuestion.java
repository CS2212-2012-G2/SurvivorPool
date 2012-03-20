package data.bonus;

import java.util.List;

import data.me.json.JSONArray;
import data.me.json.JSONException;
import data.me.json.JSONObject;

/**
 * BonusQuestion is the class that will deal with the numerous bonus questions
 * that users can answer at their leisure during the competition.
 * 
 * @author Graem Littleton, Kevin Brightwell, Jonathan Demelo, Ramesh Raj,
 *         Justin McDonald
 * 
 */

public class BonusQuestion {
	public static enum BONUS_TYPE {
		MULTI, SHORT;
	};

	protected BONUS_TYPE bonusType;

	protected String prompt;
	protected boolean active;
	protected String answer;
	protected String[] choices; // TODO: can we somehow implement short answer
								// and mc together without this?
	protected int week;

	public static final String FILE_PATH = "res/data/bonus.dat";
	protected static final String KEY_TYPE = "type";
	protected static final String KEY_PROMPT = "prompt";
	protected static final String KEY_ANSWER = "answer";
	protected static final String KEY_ACTIVE = "active";
	protected static final String KEY_CHOICES = "mc_choices";// TODO:need a
																// better name
																// for type
	protected static final String KEY_WEEK = "week";

	/**
	 * Default constructor for Bonus Question
	 * 
	 * @param prompt
	 *            The question(required)
	 * @param answer
	 *            The answer(required)
	 * @param choices
	 *            The possible choices(null from short answer, and actual values
	 *            for MC)
	 */
	public BonusQuestion(String prompt, String answer, String[] choices,
			boolean active, int week) {
		this.prompt = prompt;
		this.answer = answer;
		this.choices = choices;
		bonusType = choices == null ? BONUS_TYPE.SHORT : BONUS_TYPE.MULTI;
		this.active = active;
		this.week = week;
		Bonus.addNewQuestion(this);
		// TODO: do we need to check if answer is in choices?
	}

	/**
	 * Only used for fromJsonObject
	 */
	public BonusQuestion() {
	}

	/**
	 * Get the type of question.
	 * 
	 * @return
	 */
	public BONUS_TYPE getBonusType() {
		return bonusType;
	}

	/**
	 * Set the bonus type
	 * 
	 * @param bonusType
	 */
	public void setBonusType(BONUS_TYPE bonusType) {
		this.bonusType = bonusType;
	}

	/**
	 * Get the question prompt
	 * 
	 * @return String of prompt
	 */
	public String getPrompt() {
		return prompt;
	}

	/**
	 * Set the question prompt
	 * 
	 * @param prompt
	 */
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	/**
	 * Returns if the question is still active
	 * 
	 * @return
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Set if question is active
	 * 
	 * @param active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Get the answer to the question
	 * 
	 * @return
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * Set answer to question
	 * 
	 * @param answer
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * Get possible choices or null if question is short answre
	 * 
	 * @return
	 */
	public String[] getChoices() {
		return choices;
	}

	/**
	 * Set choices. CHANGES TYPE TO MULTIPLE CHOICE!
	 * 
	 * @param choices
	 */
	public void setChoices(String[] choices) {
		this.choices = choices;
		bonusType = BONUS_TYPE.MULTI;
	}

	/**
	 * Get the week this question was asked
	 * 
	 * @return int
	 */
	public int getWeek() {
		return week;
	}

	/**
	 * Set the week this question was asked
	 * 
	 * @param week
	 */
	public void setWeek(int week) {
		this.week = week;
	}

	/**
	 * Converts Contestant object to a json object
	 * 
	 * @return a JSON object containing all the data needed
	 * @throws JSONException
	 */
	public JSONObject toJSONObject() throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put(KEY_PROMPT, prompt);
		obj.put(KEY_ANSWER, answer);

		if (choices != null) {
			JSONArray sChoice = new JSONArray();
			for (String c : choices) {
				if (c != null)
					sChoice.put(c);
			}
			obj.put(KEY_CHOICES, sChoice);
		} else {
			obj.put(KEY_CHOICES, null);
		}

		obj.put(KEY_ACTIVE, active);
		obj.put(KEY_WEEK, week);
		return obj;
	}

	public void fromJSONObject(JSONObject o) throws JSONException {

		setPrompt(o.getString(KEY_PROMPT));
		setAnswer(o.getString(KEY_ANSWER));
		setActive(o.getBoolean(KEY_ACTIVE));

		JSONArray jChoices = o.getJSONArray(KEY_CHOICES);
		if (jChoices == null) {
			setChoices(null);
		} else {
			String[] choice = new String[jChoices.length()];
			for (int i = 0; i < jChoices.length(); i++) {
				choice[i] = jChoices.getString(i);
			}
			setChoices(choice);
		}
		setWeek(o.getInt(KEY_WEEK));

	}

	public static void main(String[] args) throws JSONException {
		/*	************to json test*********************/
		BonusQuestion b = new BonusQuestion("question week 3", "answer", null, true, 3);
		String shortActive = b.toJSONObject().toString();
		System.out.println(shortActive);

		b = new BonusQuestion("question week 2", "answer", null, false, 2);
		String shortNotActive = b.toJSONObject().toString();
		System.out.println(shortNotActive);

		String[] choices = { "one", "two", "three", "answer" };
		b = new BonusQuestion("question week 4", "answer", choices, true, 4);
		String mcActive = b.toJSONObject().toString();
		System.out.println(mcActive);

		b = new BonusQuestion("question week 1", "answer", choices, false, 1);
		String mcNotActive = b.toJSONObject().toString();
		System.out.println(mcNotActive);
		
		System.out.println(Bonus.toJSONObject().toString());
		
		/* **from json test**********************/
		System.out.println("\n\n\nGenerating fromJSONObject");
		JSONObject o = new JSONObject(shortActive);
		BonusQuestion bq = new BonusQuestion();
		bq.fromJSONObject(o);
		System.out.println(bq.toJSONObject().toString());

		o = new JSONObject(shortNotActive);
		bq = new BonusQuestion();
		bq.fromJSONObject(o);
		System.out.println(bq.toJSONObject().toString());

		o = new JSONObject(mcActive);
		bq = new BonusQuestion();
		bq.fromJSONObject(o);
		System.out.println(bq.toJSONObject().toString());

		o = new JSONObject(mcNotActive);
		bq = new BonusQuestion();
		bq.fromJSONObject(o);
		System.out.println(bq.toJSONObject().toString());
		System.out.println(Bonus.toJSONObject().toString());
		
		/* ****************TESTING SORT*************************/
		System.out.println("\n\nTesting sort**************************");
		List<BonusQuestion> list = Bonus.getAllQuestions();
		for(BonusQuestion quest: list){
			System.out.println(quest.getWeek());
		}
		
		BonusQuestion byWeek = Bonus.getQuestionByWeek(2);
		System.out.println(byWeek.getPrompt());
	}
}
