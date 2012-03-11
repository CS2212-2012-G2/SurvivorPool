package admin.data;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import admin.AdminUtils;
import admin.ComparatorFactory;
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
		
		allList = Arrays.asList(allContestants);
	}
	
	private void updateSortAllContestants(int compFactID) {
		allList = Arrays.asList(allContestants);
		List<Contestant> t = AdminUtils.noNullList(allList);
		Collections.sort(t, ComparatorFactory.getComparator(compFactID));
		// t holds the sorted array, replace all the values with their
		// new index. When the entry is null, it means we are done.
		for (int i = 0; i < numContestants && allContestants[i] != null; i++) {
			allContestants[i] = t.get(i);
		}

		allList = Arrays.asList(allContestants);
	}

	// extends the method in super class to sort it.
	@Override
	public void addContestant(Contestant c) {
		super.addContestant(c);
		
		updateSortAllContestants(ComparatorFactory.CONTNT_ID);
	}

	// overridden to use binary search for speeeeed.
	@Override
	public void removeContestant(Contestant target) {
		// is the contestant there?
		int i = AdminUtils.BinSearchSafe(allList, (Contestant)target,
				ComparatorFactory.getComparator(ComparatorFactory.CONTNT_ID));
		
		if (i < 0) {
			// i < 0 implies not found.
			return;
		}
		
		allList.remove(i);
		updateSortAllContestants(ComparatorFactory.CONTNT_ID);
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
	
		return AdminUtils.BinSearchSafe(allList, t,
				ComparatorFactory.getComparator(ComparatorFactory.CONTNT_ID));
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			GameData.getCurrentGame().fromJSONObject(json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return (GameData)currentGame;
	}


	@Override
	public void fromJSONObject(JSONObject obj) throws JSONException {
		super.fromJSONObject(obj);
		allList = Arrays.asList(allContestants);
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
	// TODO: DOC
	@Override
	public void writeData(){
		
		try {
			JSONUtils.writeJSON(JSONUtils.seasonFile, this.toJSONObject());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
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
			System.out.println(g.toJSONString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		GameData g2 = new GameData(6);
		
			try {
				g2.fromJSONString(g.toJSONString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}
	
	
}
