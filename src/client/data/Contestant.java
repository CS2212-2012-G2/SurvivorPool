package client.data;

import data.InvalidFieldException;
import data.me.json.*;

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
	
	public JSONObject toJSONObject() throws JSONException {
		JSONObject obj = new JSONObject();
		
		obj.put(KEY_FIRST_NAME, getFirstName());
		obj.put(KEY_LAST_NAME, getLastName());
		obj.put(KEY_ID, getID());
		obj.put(KEY_PICTURE, getPicture());
		obj.put(KEY_TRIBE, getTribe());
		obj.put(KEY_DATE, new Integer(getCastDate()));
		System.out.println(obj.get(KEY_TRIBE)+"a");
		return obj;
	}
	
	public String toJSONString() throws JSONException {
		return toJSONObject().toString();
	}
	
	
	public void fromJSONString(String json) throws JSONException{
		JSONObject o = new JSONObject(json);
		
		fromJSONObject(o);
	}
	
	
	public void fromJSONObject(JSONObject o) {
		try {
			setID((String)o.remove(KEY_ID));
			setFirstName((String)o.remove(KEY_FIRST_NAME));
			setLastName((String)o.remove(KEY_LAST_NAME));
			setTribe((String)o.remove(KEY_TRIBE));
			setPicture((String)o.remove(KEY_PICTURE));
			setCastDate(((Integer)o.remove(KEY_DATE)).intValue());
		} catch (InvalidFieldException e) {
			System.out.println("Warning: InvalidFieldException in fromJSONObject");
			System.out.println(e.getMessage());
		}
	}

	/// Driver for Contestant JSON 
	public static void main(String[] args) throws InvalidFieldException {
		Contestant c = new client.data.Contestant("ad", "Jon", "silver", "booby");
		
		try {
			System.out.println(c.toJSONString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			Contestant p = new client.data.Contestant();
			p.fromJSONString(c.toJSONString());
			System.out.println(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
