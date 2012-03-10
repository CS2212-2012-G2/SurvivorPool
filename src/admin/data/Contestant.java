package admin.data;

import data.InvalidFieldException;
import admin.json.JSONObject;
import admin.json.JSONValue;
import admin.json.parser.ParseException;

public class Contestant extends data.Contestant {

	/**
	 * Constructor method for type contestant sets player info
	 * @param _id ID tag
	 * @param first
	 *            first name
	 * @param last
	 *            last name
	 * @param _tribe
	 *            contestant's tribe
	 */
	public Contestant(String _id, String first, String last, String _tribe) throws InvalidFieldException {
		super(_id, first, last, _tribe);
	}
	
	public Contestant() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		
		obj.put(KEY_FIRST_NAME, getFirstName());
		obj.put(KEY_LAST_NAME, getLastName());
		obj.put(KEY_ID, getID());
		obj.put(KEY_PICTURE, getPicture());
		obj.put(KEY_TRIBE, getTribe());
		obj.put(KEY_DATE, new Integer(getCastDate()));
		
		return obj;
	}
	
	@Override
	public String toJSONString() {
		return toJSONObject().toJSONString();
	}
	
	@Override
	public void fromJSONString(String json) throws ParseException {
		JSONObject o = (JSONObject)JSONValue.parse(json);
		
		fromJSONObject(o);
	}
	
	@Override
	public void fromJSONObject(JSONObject o) {
		try {
			setID((String)o.remove(KEY_ID));
			setFirstName((String)o.remove(KEY_FIRST_NAME));
			setLastName((String)o.remove(KEY_LAST_NAME));
			setTribe((String)o.remove(KEY_TRIBE));
			setPicture((String)o.remove(KEY_PICTURE));
			setCastDate(((Number)o.remove(KEY_DATE)).intValue());
		} catch (InvalidFieldException e) {
			System.out.println("Warning: InvalidFieldException in fromJSONObject");
			System.out.println(e.getMessage());
		}
	}

	/// Driver for Contestant JSON 
	public static void main(String[] args) throws InvalidFieldException {
		Contestant c = new admin.data.Contestant("ad", "Jon", "silver", "booby");
		
		System.out.println(c.toJSONString());
		
		try {
			Contestant p = new admin.data.Contestant();
			p.fromJSONString(c.toJSONString());
			System.out.println(p);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
