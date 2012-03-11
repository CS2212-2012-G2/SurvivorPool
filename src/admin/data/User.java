package admin.data;

import data.me.json.*;
import data.Contestant;

public class User extends data.User {

	private Contestant winPick, weeklyPick; // users pick of the winner and
											// their weekly pick
	
	public User(String first, String last, String id) {
		super(first, last, id);

	}

	public User() {
		super();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public JSONObject toJSONObject() throws JSONException {
		JSONObject obj = new JSONObject();
		
		obj.put(KEY_FIRST_NAME, getFirstName());
		obj.put(KEY_LAST_NAME, getLastName());
		obj.put(KEY_ID, getID());
		obj.put(KEY_POINTS, new Integer(getPoints()));
		
		Contestant c = getWeeklyPick();
		if (c != null)
			obj.put(KEY_WEEKLY_PICK_ID, c.getID());
		else 
			obj.put(KEY_WEEKLY_PICK_ID, null);
		
		c = getWinPick();
		if (c != null) 
			obj.put(KEY_ULT_PICK_ID, c.getID());
		else
			obj.put(KEY_ULT_PICK_ID, null);
		
		obj.put(KEY_WIN_PICK_POINTS, new Integer(getWinPoints()));
		
		return obj;
	}

	@Override
	public String toJSONString() throws JSONException {
		return toJSONObject().toString();
	}

	@Override
	public void fromJSONString(String json) throws JSONException {
		JSONObject o = new JSONObject(json);
		
		GameData g = (GameData)GameData.getCurrentGame();
		
		setID((String)o.remove(KEY_ID));
		setFirstName((String)o.remove(KEY_FIRST_NAME));
		setLastName((String)o.remove(KEY_LAST_NAME));
		setPoints((Integer)o.remove(KEY_POINTS));
		setWeeklyPick(g.getContestant((String)o.remove(KEY_WEEKLY_PICK_ID)));
		setWinPickNoSetPts(g.getContestant((String)o.remove(KEY_ULT_PICK_ID)));
		setWinPoints((Integer)o.remove(KEY_WIN_PICK_POINTS));
	}
	
	public static void main(String[] args) {
		User u = new User("bob", "builder", "bbuilde");
		
		try {
			System.out.println(u.toJSONString());
			User p  = new User();
			
			p.fromJSONString(u.toJSONString());
			System.out.println(p);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public Contestant getWeeklyPick() {
		return weeklyPick;
	}

	@Override
	public Contestant getWinPick()  {
		return winPick;
	}

	@Override
	public void setWeeklyPick(Contestant pick)  {
		weeklyPick = pick;
	}

	@Override
	public void setWinPick(Contestant winner)  {
		winPick = winner;
		winPoints = 2 * GameData.getCurrentGame().weeksLeft();
	}

	@Override
	public void setWinPickNoSetPts(Contestant winner)  {
		winPick = winner;
	}

}
