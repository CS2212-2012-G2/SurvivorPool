package admin.data;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import admin.AdminUtils;
import data.Contestant;
import data.InvalidFieldException;
import data.me.json.JSONArray;
import data.me.json.JSONException;
import data.me.json.JSONObject;

public class GameData extends data.GameData {

	private List<Contestant> allList; // lits of
	// all/remaining
	// contestants
	
	public GameData(int numContestants) {
		super(numContestants);
		
		allList = AdminUtils.uncastListToCast(allContestants, 
				new Contestant());
	}
	
	private void updateSortAllContestants(AdminUtils.CompType compFactID) {
		allList = AdminUtils.uncastListToCast(allContestants, 
				new Contestant());
		
		Collections.sort(allList, AdminUtils.getContComparator(compFactID));
	}

	// extends the method in super class to sort it.
	@Override
	public void addContestant(Contestant c) {
		super.addContestant(c);
		
		updateSortAllContestants(AdminUtils.CompType.CONTNT_ID);
	}

	// overridden to use binary search for speeeeed.
	@Override
	public void removeContestant(Contestant target) {
		// is the contestant there?
		int i = AdminUtils.BinSearchSafe(allList, (Contestant)target, AdminUtils.CompType.CONTNT_ID);
		
		if (i < 0) {
			// i < 0 implies not found.
			return;
		}
		
		allList.remove(i);
		updateSortAllContestants(AdminUtils.CompType.CONTNT_ID);
	}
	
	@Override
	protected int getContestantIndexID(String id) {
		Contestant t = new Contestant();
		try { 
			t.setID(id);
		} catch (InvalidFieldException e) 
		{ 
			System.out.println("getContestantIndexID:\t" + e.getMessage());
			return -1;
		}
	
		return AdminUtils.BinSearchSafe(allList, t, AdminUtils.CompType.CONTNT_ID);
	}
	
	/**
	 * intGameData reads in a data file and builds a GameData object out
	 * of it, returning it to the user.
	 * 
	 * @param inputFile   file to be read in
	 * @return GameData object made out of file or null if season not created
	 * 
	 */
	public static GameData initGameData() {
		JSONObject json;
		try {
			json = JSONUtils.readFile(JSONUtils.seasonFile);
		} catch (FileNotFoundException e) {
			return (GameData) currentGame; 
		}
		
		try {
			currentGame = new GameData(((Number)json.get(KEY_NUM_CONTEST)).intValue());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			GameData.getCurrentGame().fromJSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return (GameData)currentGame;
	}


	@Override
	public void fromJSONObject(JSONObject obj) throws JSONException {
		super.fromJSONObject(obj);
		allList = AdminUtils.uncastListToCast(allContestants, 
				new Contestant());
	}
	
	/**
	 * Used by SeasonCreate to create a new season.
	 * @param num
	 */
	public static void initSeason(int num){
		currentGame = new GameData(num);
	}
	
	public void endCurrentGame() {
		super.endCurrentGame();
		JSONUtils.resetSeason();
	}
	/**
	 * Write all DATA into file
	 */
	@Override
	public void writeData(){
		
		try {
			JSONUtils.writeJSON(JSONUtils.seasonFile, this.toJSONObject());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		GameData g = new GameData(6);
		
		String[] tribes = new String[] {"banana", "apple"};
		
		g.setTribeNames(tribes[0], tribes[1]);
		
		Contestant c1 = null, c2 = null;
		try {
			c1 = new Contestant("a2", "Al", "Sd", tribes[1]);
			c2 = new Contestant("as", "John", "Silver", tribes[0]);
		} catch (InvalidFieldException e) {
			// wont happen.
		}
		
		g.addContestant(c1);
		g.addContestant(c2);
		
		try {
			System.out.println(g.toJSONObject().toString());
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		GameData g2 = new GameData(6);
		
			try {
				g2.fromJSONObject(g.toJSONObject());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		
		
	}
	
	/**
	 * Returns an instance of the current Game.
	 * @return instance of current game.
	 */
	public static GameData getCurrentGame() {
		GameData g = (GameData)data.GameData.getCurrentGame();
		return g;
	}
	
	
}
