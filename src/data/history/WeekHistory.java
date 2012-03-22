package data.history;

import java.util.ArrayList;

import data.Contestant;
import data.GameData;
import data.User;

public class WeekHistory {

	private int weekNum;
	private ArrayList<Contestant> contestants;
	
	/**
	 * Stores all contestants that were chosen as picks in the current week
	 * @param week
	 */
	public WeekHistory(int week){
		weekNum=week;
		generateContestants();
	}
	
	public int getWeek() {
		return weekNum;
	}
	
	public void setWeek(int weekNum) {
		this.weekNum = weekNum;
	}
	
	public ArrayList<Contestant> getContestants() {
		return contestants;
	}
	
	public void setContestants(ArrayList<Contestant> contestants) {
		this.contestants = contestants;
	}
	
	private void generateContestants(){
		ArrayList<User> users = (ArrayList<User>) GameData.getCurrentGame().getAllUsers();
		
		for(User u:users){
			contestants.add(u.getWeeklyPick());
			contestants.add(u.getUltimatePick());
		}
	}
}
