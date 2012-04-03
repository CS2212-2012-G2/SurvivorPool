package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import json.simple.JSONObject;
import json.simple.JSONValue;

/**
 * Use this class to get the keys, to write to a file, and getting values. This
 * differs from justing using the JSON classes directly as we keep references to
 * the object centralized. We can take care of any additional code that is
 * needed to read/write to files rather.
 * 
 * @author Ramesh Raj
 */
public class JSONUtils {
	
	public static final String pathGame = "res/data/GameData.dat",
							   pathBonus = "res/data/Bonus.dat",
							   pathSettings = "res/data/Settings.dat",
							   pathUserAnswer = "res/data/UserAnswer";

	public static JSONObject readFile(String path) throws FileNotFoundException {
		File f = new File(path);
		if (!f.exists())
			throw new FileNotFoundException();
		try {
			String jString = fileToString(path);
			JSONObject obj = (JSONObject) JSONValue.parse(jString);
			return obj;

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private static String fileToString(String pathname) throws IOException {

		File file = new File(pathname);
		StringBuilder fileContents = new StringBuilder((int) file.length());
		Scanner scanner = new Scanner(file);
		String lineSeparator = System.getProperty("line.separator");

		try {
			while (scanner.hasNextLine()) {
				fileContents.append(scanner.nextLine() + lineSeparator);
			}
			return fileContents.toString();
		} finally {
			scanner.close();
		}
	}

	/**
	 * Writes to file using a json object
	 * 
	 * @param filePath           The file path to write to
	 * @param json               A json object that has the keys and the values
	 */
	public static void writeJSON(String filePath, JSONObject json) {
		try {
			File f = new File(filePath);

			// if the directory above the file doesn't exist, make it. :)
			File dir = f.getParentFile();
			if (!dir.exists()) {
				dir.mkdirs();
			}

			// delete the file if it exists already to completely overwrite
			if (f.exists()) {
				f.delete();
				f.createNewFile();
			}

			f.setWritable(true);

			FileWriter fileWrite = new FileWriter(f);
			fileWrite.write(json.toString());
			fileWrite.close();

		} catch (IOException e) {
			System.out
					.println("JSONObject: writeJson: could not write to file");
			e.printStackTrace();
		}
	}

	/**
	 * Deletes the season file, returns true if successful.
	 * 
	 * @return True if sucessfully reset season.
	 */
	public static boolean resetSeason() {
		File f = new File(pathGame);
		f.delete();
		File fB = new File(pathBonus);
		fB.delete();
		
		List<User> users=GameData.getCurrentGame().getAllUsers();
		File fUser; //remove all of users previous answers
		for(User u:users){
			fUser = new File(pathUserAnswer+u.getID()+".dat");
			if(fUser.exists())
				fUser.delete();
		}
		
		return (f.exists() && fB.exists());
	}

}
