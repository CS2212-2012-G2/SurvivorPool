package admin.data;

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
	public Contestant(String _id, String first, String last, String _tribe) {
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
		setID((String)o.remove(KEY_ID));
		setFirstName((String)o.remove(KEY_FIRST_NAME));
		setLastName((String)o.remove(KEY_LAST_NAME));
		setPicture((String)o.remove(KEY_PICTURE));
		setTribe((String)o.remove(KEY_TRIBE));
		setCastDate(((Number)o.remove(KEY_DATE)).intValue());
	}

	/// Driver for Contestant JSON 
	public static void main(String[] args) {
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
