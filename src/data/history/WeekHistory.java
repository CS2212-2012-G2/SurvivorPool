package data.history;

import java.util.ArrayList;

import json.simple.JSONArray;
import json.simple.JSONObject;
import json.simple.parser.ParseException;
import data.Contestant;
import data.GameData;
import data.InvalidFieldException;
import data.User;

public class WeekHistory {

	private int weekNum;
	private ArrayList<Contestant> contestants;
	
	private final static String KEY_WEEK = "week";
	private final static String KEY_CONTESTANTS = "contestants";
	/**
	 * Stores all contestants that were chosen as picks in the current week
	 * @param week
	 */
	public WeekHistory(int week){
		weekNum=week;
		generateContestants();
	}
	
	/**
	 * Get the week this object represents
	 * @return
	 */
	public int getWeek() {
		return weekNum;
	}
	
	/**
	 * Change the week this object represents
	 * @param weekNum
	 */
	public void setWeek(int weekNum) {
		this.weekNum = weekNum;
	}
	
	/**
	 * Get all contestants that were chosen by users during the week
	 * @return
	 */
	public ArrayList<Contestant> getContestants() {
		return contestants;
	}
	
	/**
	 * Set all the contestants that were chosen this week
	 * @param contestants
	 */
	public void setContestants(ArrayList<Contestant> contestants) {
		this.contestants = contestants;
	}
	
	/**
	 * Add an individual contestant to chosen list
	 * @param c
	 */
	public void addContestant(Contestant c){
		contestants.add(c);
	}
	
	/**
	 * Adds all the contestants that were chosen to list
	 */
	private void generateContestants(){
		ArrayList<User> users = (ArrayList<User>) GameData.getCurrentGame().getAllUsers();
		
		for(User u:users){
			Contestant c = u.getWeeklyPick();
			if(!contestants.contains(c))
				contestants.add(u.getWeeklyPick()); //this includes the final pick during last week
			
			c = u.getUltimatePick();
			if(!contestants.contains(c))
				contestants.add(u.getUltimatePick());
		}
	}
	
	public JSONObject toJSONObject() throws ParseException {
		JSONObject o = new JSONObject();
		o.put(KEY_WEEK, weekNum);
		JSONArray ar = new JSONArray();
		for(Contestant c:contestants){
			ar.add(c);
		}
		o.put(KEY_CONTESTANTS, ar);
		return o;
	}
	
	public void fromJSONObject(JSONObject obj) throws ParseException {
		setWeek(((Number) obj.get(KEY_WEEK)).intValue());
		JSONArray ar = (JSONArray) obj.get(KEY_CONTESTANTS);
		for(int i =0;i<ar.size();i++){
			Contestant c = new Contestant();
			c.fromJSONObject((JSONObject)ar.get(i));
			addContestant(c);
		}
	}
}
