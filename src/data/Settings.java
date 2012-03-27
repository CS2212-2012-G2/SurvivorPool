package data;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Observable;

import admin.Utils;

import json.simple.JSONObject;
import json.simple.parser.ParseException;

public class Settings extends Observable {

	private static Settings currentSettings = null;
	
	private HashMap<Object, String> settingsMap;
	
	private static final String THEME = "theme";
	
	public static String pathSettings = "res/data/Settings.dat";
	
	public enum Field {
		THEME
	}
	
	public Settings(String theme) {
		settingsMap = new HashMap<Object, String>();
		settingsMap.put(Field.THEME, theme);
		
		Utils.changeTheme(theme);
		
		writeData();
		
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
		writeData();
		
//		setChanged();
//		notifyObservers();
	}
	
	public static Settings initSettingsData(){
		JSONObject json;
		try {
			json = JSONUtils.readFile(pathSettings);
		} catch (FileNotFoundException e) {
			return currentSettings;
		}
		
		currentSettings = new Settings((String)json.get(THEME));
		
		try {
			Settings.getCurrentSettings().fromJSONObject(json);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return currentSettings;
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
	 * Write settings to data file.
	 */
	public void writeData(){
		
		try{
			JSONUtils.writeJSON(pathSettings, this.toJSONObject());
		} catch (ParseException e){
			e.printStackTrace();
		}
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
