package admin;

import java.util.HashMap;
import java.util.Observable;

import json.simple.JSONObject;
import json.simple.parser.ParseException;

public class Settings extends Observable {

	private static Settings currentSettings = null;
	
	private HashMap<Object, String> settingsMap;
	
	private static final String THEME = "theme";
	
	public enum Field {
		THEME, 
		DIALOG_PLAYER
	}
	
	public Settings() {
		settingsMap = new HashMap<Object, String>();
		settingsMap.put(Field.THEME, "snow");
		
		currentSettings = this;
	}
	
	/**
	 * returns the settings value stored at a specific key
	 * @param key
	 * @return String: setting stored at this key
	 */
	public String getSetting(Field key){
		return settingsMap.get(key);
	}
	
	/**
	 * set the settings at a particular key
	 * @param key
	 * @param newValue: new setting at this key
	 */
	public void setSetting(Field key, String newValue){
		settingsMap.put(key, newValue);
		
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Convert Settings to a JSON object
	 * 
	 * @return a JSONObject with all the relevant data
	 * @throws JSONException
	 */
	public JSONObject toJSONObject() throws ParseException {
		JSONObject obj = new JSONObject();

		obj.put(THEME, getSetting(Field.THEME));
		
		return obj;
	}
	
	/**
	 * Update Settings with values from JSONObject
	 * 
	 * @param obj: JSON object containing settings
	 * @throws ParseException
	 */
	public void fromJSONObject(JSONObject obj) throws ParseException {
		setSetting(Field.THEME, (String)obj.get(THEME));
		
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Returns the currently stored Settings.
	 * 
	 * @return Current settings, null if none present.
	 */
	public static Settings getCurrentGame() {
		return Settings.currentSettings;
	}
}
