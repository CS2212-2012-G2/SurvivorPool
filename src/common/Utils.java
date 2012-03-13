package common;

import data.me.regexp.RE;


public class Utils {
	public static String TRIBE_PATTERN = "^[a-zA-Z\\s]{1,30}$";
	
	
	/**
	 * Checks if string matches pattern.
	 * @param val The string to check for validity
	 * @param pattern A regex pattern that has all possible valid values
	 * @return true if string matches pattern
	 */
	public static boolean checkString(String val,String pattern){
		if(val==null)
			return false;
		if(val.length()==0)
			return false;
		RE r = new RE(pattern);
		return r.match(val);                
	}
	
}
