package data.history;

import java.util.ArrayList;

import json.simple.JSONArray;
import json.simple.JSONObject;
import json.simple.parser.ParseException;
import data.Contestant;
import data.GameData;

//TODO: does this file need to be moved somewhere more appropriate?
public class History {
	
	WeekHistory[] weekList;
	
	/**
	 * History of events that happened in a week
	 * @param numWeeks the total number of weeks in game
	 */
	public History(int numWeeks){
		weekList = new WeekHistory[numWeeks];
	}
	
	/**
	 * Add a new week to the history
	 * @param w the week number you want to store
	 */
	public void setWeek(int weekNum){
		weekList[weekNum] = new WeekHistory(weekNum);
	}
	
	public void setWeek(int w, WeekHistory weekH) {
		weekList[w] = weekH;
	}
	
	public WeekHistory getWeek(int weekNum){
		return weekList[weekNum];
	}
	
	/**
	 * Checks if casting off a contestant for a given week will invalidate the season
	 * Does not actually modify the week.
	 * @param weekNum The week that is being modified
	 * @param c The contestant which is being casted off
	 * @return true if week can be modified
	 */
	public boolean canCastoff(int weekNum,Contestant c){
		for(int i =weekNum;i<=GameData.getCurrentGame().getCurrentWeek();i++){
			WeekHistory w = weekList[i];
			ArrayList<Contestant> chosenConts = w.getContestants();
			for(Contestant cont:chosenConts){
				if(cont.getID().equals(c.getID()))
					return false;
			}
		}
		
		return true;
	}
	
	private static final String WEEK_NUM = "week_cnt",
			WEEK_LIST = "week_list";
	
	public JSONObject toJSONObject() throws ParseException {
		JSONObject obj = new JSONObject();
		obj.put(WEEK_NUM, weekList.length);
		
		JSONArray weeks = new JSONArray();
		for (WeekHistory w : weekList) {
			if (w != null)
				weeks.add(w.toJSONObject());
		}
		
		obj.put(WEEK_LIST, weeks);
		
		return obj;
	}
	
	public void fromJSONObject(JSONObject obj) throws ParseException {
		weekList = new WeekHistory[((Number)obj.get(WEEK_NUM)).intValue()];
		
		JSONArray weekObjs = (JSONArray)obj.get(WEEK_LIST);
		for (int i = 0; i < weekList.length && i < weekObjs.size(); i++) {
			WeekHistory w = new WeekHistory(i);
			w.fromJSONObject((JSONObject)weekObjs.get(i));
			setWeek(i, w);
		}
	}

	
}
