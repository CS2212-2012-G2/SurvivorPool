package client.data;


import java.util.Vector;

import net.rim.device.api.io.FileNotFoundException;
import net.rim.device.api.util.Arrays;

import common.Utils;

import data.Contestant;
import data.InvalidFieldException;
import data.me.json.JSONArray;
import data.me.json.JSONException;
import data.me.json.JSONObject;

public class GameData extends data.GameData {

	private Vector allList; // lits of
	// all/remaining
	// contestants
	
	public GameData(int numContestants) {
		super(numContestants);
		
		allList = arrayToList(allContestants);
	}
	
	private void updateSortAllContestants(int compFactID) {
		allList = arrayToList(allContestants);
		Vector t = noNullList(allList);
		Object[] ar = new Object[t.size()];
		t.copyInto(ar);
		Arrays.sort(ar,ComparatorFactory.getComparator(compFactID));
		
		// t holds the sorted array, replace all the values with their
		// new index. When the entry is null, it means we are done.
		for (int i = 0; i < numContestants && allContestants[i] != null;
			 i++) {
			allContestants[i] = (Contestant) ar[i];
		}

		allList = arrayToList(allContestants);
	}

	/**
	 * Convert an array to a vector
	 * @param ar an array
	 * @return a Vector containing the elements of the array
	 */
	private Vector arrayToList(Object[] ar){
		Vector retList = new Vector();
		for(int i =0;i<ar.length;i++){
			retList.addElement(ar[i]);
		}
		return retList;
	}
	
	/**
	 * Returns a vector with all nulls removed
	 * @param c a vector which may contain nulls
	 * @return a vector which contains all non-null elements from param
	 */
	private Vector noNullList(Vector c){
		Vector retList=new Vector();
		for(int i =0;i<c.size();i++){
			if(c.elementAt(i)!=null)
				retList.addElement(c.elementAt(i));
		}
		return retList;
	}
	
	// extends the method in super class to sort it.
	public void addContestant(Contestant c) {
		super.addContestant(c);
		updateSortAllContestants(ComparatorFactory.CONTNT_ID);
	}

	public void removeContestant(Contestant target) {
		//if contestant was there, sort the array
		if(allList.removeElement(target)){
			updateSortAllContestants(ComparatorFactory.CONTNT_ID);
		}
	}
	
	
	protected int getContestantIndexID(String id) {
		Contestant t = new Contestant();
		try { 
			t.setID(id);
		} catch (InvalidFieldException e) 
		{ 
			System.out.println("getContestantIndexID:\t" + e.getMessage());
			return -1;
		}
		return allList.indexOf(t);
	}
	
	/**
	 * initGameData reads in a data file and builds a GameData object out
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
			currentGame = new GameData(json.getInt(KEY_NUM_CONTEST));
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

	
	
	

	public JSONObject toJSONObject() throws JSONException {
		JSONObject obj = new JSONObject();
		
		obj.put(KEY_NUM_CONTEST, new Integer(numContestants));
		JSONArray cons = new JSONArray();
		for (int i =0;i<allList.size();i++) {
			Contestant c = (Contestant) allList.elementAt(i);
			if (c != null)
				cons.put(c.toJSONObject());
		}
		
		JSONArray ts = new JSONArray();
		// TODO: only two tribes?
		ts.put(tribeNames[0]);
		ts.put(tribeNames[1]);
		
		
		obj.put(KEY_CONTESTANTS, cons);
		obj.put(KEY_TRIBES, ts);
		obj.put(KEY_WEEKS_REMAIN, new Integer(weeksRem));
		obj.put(KEY_WEEKS_PASSED, new Integer(weeksPassed));
		
		return obj;
	}
	
	
	public void fromJSONString(String json) throws JSONException {
		JSONObject o = new JSONObject(json);
		
		fromJSONObject(o);
	}


	public void fromJSONObject(JSONObject obj) throws JSONException {
		numContestants = ((Integer)obj.get(KEY_NUM_CONTEST)).intValue();
				
		// tribes
		JSONArray ts = (JSONArray)obj.get(KEY_TRIBES);
		this.setTribeNames((String)ts.get(0),  (String)ts.get(1) );
		// week info:
		weeksRem = ((Integer)obj.get(KEY_WEEKS_REMAIN)).intValue();
		weeksPassed = ((Integer)obj.get(KEY_WEEKS_PASSED)).intValue();
		
		//Contestants must be loaded last!
		allList = new Vector(numContestants);
		// load the contestant array. 
		JSONArray cons = (JSONArray)obj.get(KEY_CONTESTANTS);
		for (int i =0;i<cons.length();i++) {
			Contestant c = new Contestant();
			c.fromJSONObject(cons.getJSONObject(i));
			addContestant(c);
		}

	}
	
	// TODO: Implement:
	
	public String toString() {
		return super.toString();
	}
	
	/**
	 * Used by SeasonCreate to create a new season.
	 * @param num
	 */
	public static void initSeason(int num){
		currentGame = new GameData(num);
	}
	
	public void writeData() {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		GameData g = new GameData(6);
		
		String[] tribes = new String[] {"banana", "apple"};
		
		g.setTribeNames(tribes[0], tribes[1]);
		
		Contestant c1 = null, c2 = null;
		try {
			c1 = new Contestant("asd2", "Al", "Sd", tribes[1]);
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
