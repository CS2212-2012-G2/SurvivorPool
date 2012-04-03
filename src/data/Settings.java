package data;

/**
 * Class that will be used to remember stylistic choices of the user.  Includes theme, window size,
 * and the positioning of the window on the Admin's screen when loading.
 * 
 * @author Kevin Brightwell
 */

import java.io.FileNotFoundException;
import java.util.HashMap;

import json.simple.JSONObject;
import json.simple.parser.ParseException;

public class Settings extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	private static Settings currentSettings = null;
	
	/**
	 * Keys to represent stylistic choices (Theme, screen size) when written
	 * into a JSON object.
	 */
	public static final String THEME = "theme",
			HISTORY = "history",
			SCREEN_LOC_X = "screen_locX",
			SCREEN_LOC_Y = "screen_locY",
			SCREEN_SIZE_X = "screen_sizeX",
			SCREEN_SIZE_Y = "screen_sizeY";
	/**
	 * Constructor for when no settings are loaded.
	 */
	public Settings() {
		this(false);
	}
	
	/**
	 * Constructor
	 * 		initSettingsData if settings were loaded.		
	 * 
	 * @param loadSettings
	 */
	public Settings(boolean loadSettings) {
		super();
		
		if (loadSettings) {
			initSettingsData();
		}
		
		currentSettings = this;
	}
	
	// -------------------- ACCESSORS ------------------ //
	
	/**
	 * Returns the currently stored Settings.
	 * 
	 * @return Current settings, null if none present.
	 */
	public static Settings getCurrentSettings() {
		return Settings.currentSettings;
	}
	
	/**
	 * used to initialize settings from persistance data
	 * 
	 * @return Settings
	 */
	public static Settings initSettingsData(){
		JSONObject json;
		try {
			json = JSONUtils.readFile(JSONUtils.pathSettings);
			
			currentSettings = new Settings(false);
			
			currentSettings.fromJSONObject(json);
		} catch (FileNotFoundException e) {
			return new Settings(false);
		}
		
		return currentSettings;
	}
	

	// ----------------- JSON ----------------- //
	
	/**
	 * Convert Settings to a JSON object
	 * 
	 * @return a JSONObject with all the relevant data
	 * @throws JSONException
	 */
	public JSONObject toJSONObject() {
		return new JSONObject(this);
	}
	
	/**
	 * Update Settings with values from JSONObject
	 * 
	 * @param obj: JSON object containing settings
	 * @throws ParseException
	 */
	public void fromJSONObject(JSONObject obj) {
		clear();
		
		for (String key: obj.keySet()) {
			put(key, obj.get(key));
		}
	}
	
	/**
	 * Write all DATA into file
	 */
	public void writeData() {
		JSONUtils.writeJSON(JSONUtils.pathSettings, toJSONObject());
	}
}
