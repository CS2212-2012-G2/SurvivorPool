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
			e.printStackTrace();
		}
		try {
			GameData.getCurrentGame().fromJSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return (GameData)currentGame;
	}

	public void fromJSONObject(JSONObject obj) throws JSONException {
		super.fromJSONObject(obj);
		allList = arrayToList(allContestants);
		
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
		// TODO figure out a way to ouput data on BB
		
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

	
}
