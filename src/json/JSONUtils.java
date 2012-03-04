package json;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Use this class to get the keys, to write to a file, and getting values.
 * This differs from justing using the JSON classes directly as we keep
 * references to the object centralized. We can take care of any additional
 * code that is needed to read/write to files rather.
 */
public class JSONUtils{

	//we could combine all of this into one file.
	public static JSONObject jsonSeason = null;
	public static JSONObject jsonContestants = null;
	public static JSONObject jsonPlayers = null;
	
	private static String seasonFile = "res/data/Settings.dat";
	private static String contestantFile = "res/data/Contestant.dat";
	private static String playerFile = "res/data/Player.dat";
	
	//TODO:Need to store keys in a constant rather than hardcoded
	
	/*------------------------------Updating Values--------------------*/
	public static void changeTribe1(String name){
		try {
			jsonSeason.put("Tribe 1", name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static void changeTribe2(String name){
		try {
			jsonSeason.put("Tribe 2", name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	//the reason why this is String num and not int num is 
	//because a spinner returns a string, not an integer
	public static void changeNumContestant(String num){
		try {
			jsonSeason.put("Num Contestants",num);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	} 
	
	/*----------------------------GETTING VALUES-----------------------*/
	public static int getContestants(){
		if(jsonSeason.has("Num Contestants")){
			try {
				return jsonSeason.getInt("Num Contestants");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	public static String getTribe1(){
		return getString("Tribe 1");
	}
	
	public static String getTribe2(){
		return getString("Tribe 2");
	}
	
	private static String getString(String key){
		if(jsonSeason.has(key)){
			try {
				return jsonSeason.getString(key);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	/*----------------------------READING FROM FILE---------------------*/
	
	/**
	 * Read values from Season file for use
	 */
	public static void readSeasonFile(){
		readFile(seasonFile);
	}
	
	/**
	 * Read values from Contestant file for use
	 */
	public static void readContestantFile(){
		readFile(contestantFile);
	}
	
	/**
	 * Read values from Player file for use
	 */
	public static void readPlayerFile(){
		readFile(playerFile);
	}
	
	/**
	 * Equivalent to calling all the read functions
	 */
	public static void readAllFiles(){
		readSeasonFile();
		readContestantFile();
		readPlayerFile();
	}
	
	private static void readFile(String path){
		try {
			jsonSeason = new JSONObject(path);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	/*------------------------------WRITING TO FILE----------------------*/
	/**
	 * Write season information to file
	 */
	public static void writeSeason(){
		writeJSON(seasonFile,jsonSeason);
	}
	
	/**
	 * Write contestants' information to file
	 */
	public static void writeContestant(){
		writeJSON(contestantFile,jsonSeason);
	}
	
	/**
	 * Write players' information to file
	 */
	public static void writePlayer(){
		writeJSON(playerFile,jsonSeason);
	}
	
	/**
	 * Writes to all files.
	 */
	public static void writeAll(){
		writeSeason();
		writeContestant();
		writePlayer();
	}
	
	/**
	 * Writes to file using a json object
	 * @param filePath The file path to write to
	 * @param json A json object that has the keys and teh values
	 */
	private static void writeJSON(String filePath, JSONObject json){
		try {
			FileWriter fileWrite = new FileWriter(filePath, false);
			BufferedWriter buffWrite = new BufferedWriter(fileWrite);
			
			String s = json.toString(4);//makes it pretty so we can debug if needed
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
