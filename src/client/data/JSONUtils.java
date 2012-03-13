package client.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import net.rim.device.api.io.FileNotFoundException;
import net.rim.device.api.io.IOUtilities;

import data.me.json.JSONException;
import data.me.json.JSONObject;


/**
 * Use this class to get the keys, to write to a file, and getting values.
 * This differs from justing using the JSON classes directly as we keep
 * references to the object centralized. We can take care of any additional
 * code that is needed to read/write to files rather.
 */
public class JSONUtils{
	
	public static String seasonFile = "res/data/Settings.dat";
	
	public static JSONObject readFile(String path) throws FileNotFoundException{
		try {
			
			Class classs = Class.forName("client.SplashScreen");
			InputStream is = classs.getResourceAsStream(seasonFile);
			String str = new String(IOUtilities.streamToBytes(is), "UTF-8");
			JSONObject obj = new JSONObject(str);
			return obj;

		} catch (Exception ex) {
			throw new FileNotFoundException();
		}
	}
	
	
	/**
	 * Writes to file using a json object
	 * @param filePath The file path to write to
	 * @param json A json object that has the keys and teh values
	 */
	public static void writeJSON(String filePath, JSONObject json){
		/*try {
			FileWriter fileWrite = new FileWriter(filePath, false);
			fileWrite.write(json.toString());
			fileWrite.close();
			
		} catch (IOException e) {
			System.out.println("JSONObject: writeJson: could not write to file");
			e.printStackTrace();
		}*/
	}

}
