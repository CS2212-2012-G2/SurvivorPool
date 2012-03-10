package admin.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import admin.json.JSONObject;
import admin.json.parser.JSONParser;
import admin.json.parser.ParseException;


/**
 * Use this class to get the keys, to write to a file, and getting values.
 * This differs from justing using the JSON classes directly as we keep
 * references to the object centralized. We can take care of any additional
 * code that is needed to read/write to files rather.
 */
public class JSONUtils{
	
	//TODO:only one file?
	public static String seasonFile = "res/data/Settings.dat";
	
	public static JSONObject readFile(String path) throws FileNotFoundException{
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
	
	
	/**
	 * Writes to file using a json object
	 * @param filePath The file path to write to
	 * @param json A json object that has the keys and teh values
	 */
	public static void writeJSON(String filePath, JSONObject json){
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
