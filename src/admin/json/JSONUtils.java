package admin.json;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import admin.json.parser.JSONParser;
import admin.json.parser.ParseException;


/**
 * Use this class to get the keys, to write to a file, and getting values.
 * This differs from justing using the JSON classes directly as we keep
 * references to the object centralized. We can take care of any additional
 * code that is needed to read/write to files rather.
 */
public class JSONUtils{
	//TODO:NEED TO MAKE JSON UTILS USE SERIALIZATION!
	//we could combine all of this into one file.
	public static JSONObject jsonSeason = new JSONObject();
	public static JSONObject jsonContestants = new JSONObject();
	public static JSONObject jsonPlayers = new JSONObject();
	

	private static String seasonFile = "res/data/Settings.dat";
	private static String contestantFile = "res/data/Contestant.dat";
	private static String playerFile = "res/data/Player.dat";
	
	//TODO:Need to store keys in a constant rather than hardcoded
	final static String sCont = "Num contestant";
	final static String sTribe1 = "Tribe 1";
	final static String sTribe2 = "Tribe 2";
	
	/*------------------------------Updating Values--------------------*/
	public static void changeTribe1(String name){
		jsonSeason.put(sTribe1, name);
	}
	
	public static void changeTribe2(String name){
		jsonSeason.put(sTribe2, name);
	}

	//the reason why this is String num and not int num is 
	//because a spinner returns a string, not an integer
	public static void changeNumContestant(String num){
		jsonSeason.put(sCont,num);
	} 
	
	/*----------------------------GETTING VALUES-----------------------*/
	public static int getContestants(){
		return Integer.parseInt(jsonSeason.get(sCont).toString());
	}
	
	public static String getTribe1(){
		return getString(sTribe1);
	}
	
	public static String getTribe2(){
		return getString(sTribe2);
	}
	
	private static String getString(String key){
		return (String) jsonSeason.get(key);

	}
	
	
	/*----------------------------READING FROM FILE---------------------*/
	
	/**
	 * Read values from Season file for use
	 * @throws FileNotFoundException 
	 */
	public static void readSeasonFile() throws FileNotFoundException{
		jsonSeason=readFile(seasonFile);
	}
	
	/**
	 * Read values from Contestant file for use
	 * @throws FileNotFoundException 
	 */
	public static void readContestantFile() throws FileNotFoundException{
		jsonContestants=readFile(contestantFile);
	}
	
	/**
	 * Read values from Player file for use
	 * @throws FileNotFoundException 
	 */
	public static void readPlayerFile() throws FileNotFoundException{
		jsonPlayers=readFile(playerFile);
	}
	
	/**
	 * Equivalent to calling all the read functions
	 * @throws FileNotFoundException 
	 */
	public static void readAllFiles() throws FileNotFoundException{
		readSeasonFile();
		readContestantFile();
		readPlayerFile();
	}
	
	private static JSONObject readFile(String path) throws FileNotFoundException{
		File f = new File(path);
		if(!f.exists())
			throw new FileNotFoundException();
		try{
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader(path));
			return (JSONObject) obj;
			
		}catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}			
		return null;
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
			fileWrite.write(json.toJSONString());
			fileWrite.close();
			
		} catch (IOException e) {
			System.out.println("JSONObject: writeJson: could not write to file");
			e.printStackTrace();
		}
	}

}
