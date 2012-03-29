package data;

import java.io.FileNotFoundException;
import java.util.HashMap;

import json.simple.JSONObject;
import json.simple.parser.ParseException;

public class Settings extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	private static Settings currentSettings = null;
	
	public static final String THEME = "theme",
			HISTORY = "history",
			SCREEN_LOC_X = "screen_locX",
			SCREEN_LOC_Y = "screen_locY",
			SCREEN_SIZE_X = "screen_sizeX",
			SCREEN_SIZE_Y = "screen_sizeY";
	
	public Settings() {
		this(false);
	}
	
	public Settings(boolean loadSettings) {
		super();
		
		if (loadSettings) {
			initSettingsData();
		}
		
		currentSettings = this;
	}
	
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
	
	/**
	 * Returns the currently stored Settings.
	 * 
	 * @return Current settings, null if none present.
	 */
	public static Settings getCurrentSettings() {
		return Settings.currentSettings;
	}
}
