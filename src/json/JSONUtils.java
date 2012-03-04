package json;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import admin.Main;

/**
 * Use this class to get the keys, to write to a file, and getting values.
 * This differs from justing using the JSON classes directly as we keep
 * references to the object centralized. We can take care of any additional
 * code that is needed to read/write to files rather.
 */
public class JSONUtils extends JSONObject{

	//we could combine all of this into one file.
	public static JSONObject jsonSeason = new JSONObject();
	public static JSONObject jsonContestants = new JSONObject();
	public static JSONObject jsonPlayers = new JSONObject();
	
	private static String seasonFile = "res/data/Settings.dat";
	private static String contestantFile = "res/data/Contestant.dat";
	private static String playerFile = "res/data/Player.dat";
	
	public static void writeSeason(){
		writeJSON(seasonFile,jsonSeason);
	}
	
	public static void writeContestant(){
		writeJSON(contestantFile,jsonSeason);
	}
	
	public static void writePlayer(){
		writeJSON(playerFile,jsonSeason);
	}
	
	private static void writeJSON(String filePath, JSONObject json){
		try {
			FileWriter fileWrite = new FileWriter(filePath, false);
			BufferedWriter buffWrite = new BufferedWriter(fileWrite);
			
			String s = json.toString(4);
			buffWrite.write(s);
			
			buffWrite.close();
			fileWrite.close();
			
		} catch (IOException e) {
			System.out.println("JSONObject: writeJson: could not write to file");
			e.printStackTrace();
		} catch (JSONException e) {
			System.out.println("JSONObject: writeJson: Jsonexception");
			e.printStackTrace();
		}
	}
}
