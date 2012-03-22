package data.history;

import java.util.ArrayList;

import json.simple.JSONObject;
import json.simple.parser.ParseException;
import data.Contestant;
import data.GameData;

//TODO: does this file need to be moved somewhere more appropriate?
public class History {
	
	WeekHistory[] week;
	
	/**
	 * History of events that happened in a week
	 * @param numWeeks the total number of weeks in game
	 */
	public History(int numWeeks){
		week = new WeekHistory[numWeeks];
	}
	
	/**
	 * Add a new week to the history
	 * @param w the week number you want to store
	 */
	public void setWeek(int weekNum){
		week[weekNum]=new WeekHistory(weekNum);
	}
	
	public WeekHistory getWeek(int weekNum){
		return week[weekNum];
	}
	
	/**
	 * Checks if casting off a contestant for a given week will invalidate the season
	 * Does not actually modify the week.
	 * @param weekNum The week that is being modified
	 * @param c The contestant which is being casted off
	 * @return true if week can be modified
	 */
	public boolean canCastoff(int weekNum,Contestant c){
		for(int i =weekNum;i<GameData.getCurrentGame().getCurrentWeek();i++){
			WeekHistory w = week[i];
			ArrayList<Contestant> chosenConts = w.getContestants();
			for(Contestant cont:chosenConts){
				if(cont.getID().equals(c.getID()))
					return false;
			}
		}
		
		return true;
	}
	
	public JSONObject toJSONObject() throws ParseException {
		return null;
	}
	
	public void fromJSONObject(JSONObject obj) throws ParseException {}
}
