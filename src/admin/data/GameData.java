package admin.data;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;


import admin.ComparatorFactory;
import admin.Utils;
import admin.json.JSONArray;
import admin.json.JSONObject;
import admin.json.JSONUtils;
import admin.json.JSONValue;
import admin.json.parser.ParseException;

//import data.Contestant;
import data.Person;

public class GameData extends data.GameData {

	private ArrayList<Contestant> allContestants = new ArrayList<Contestant>(); // lits of
	// all/remaining
	// contestants
	
	public GameData(int numContestants) {
		super(numContestants);
		
		allContestants = new ArrayList<Contestant>(numContestants);
	}
	
	@Override
	public Contestant[] getActiveContestants() {
		//ArrayList<ContestantAdmin> active = new ArrayList<ContestantAdmin>(allContestants.size());
		
		Contestant[] active = new Contestant[allContestants.size()];
		
		int newSize = 0;
		for (int i = 0; i < allContestants.size(); i++) {
			Contestant c = allContestants.get(i);
			if (!c.isCastOff()) {
				active[newSize++] = c;
			}
		}
		
		return active;
	}
	
	@Override
	public Contestant[] getAllContestants() {
		return allContestants.toArray(new Contestant[0]);
	}

	@Override
	public Contestant getContestant(String first, String last) {
		Contestant j; 
		// loop through array
		for(int i = 0; i <= numContestants; i++){
			j = allContestants.get(i); // get Contestant object for comparison 
			if(first.equals(j.getFirstName()) && last.equals(j.getLastName())) { // ensure names match
				return j; // return info on player
			}
		}
		// otherwise return message saying contestant is no longer/is not in the game
		return null;
	}

	@Override
	public Contestant getContestant(String id) {
		Contestant j; 
		// loop through array
		for(int i = 0; i <= numContestants; i++){
			j = allContestants.get(i); // get Contestant object for comparison 
			if(j.getID().equals(id)) { // ensure names match
				return j; // return info on player
			}
		}
		// otherwise return message saying contestant is no longer/is not in the game
		return null;
	}
	
	// TODO: Should Add contestant check if there are already the max number?

	@Override
	public void addContestant(Contestant c) {
		if (c.getID() == null || 
				c.getID().equals("")) {
			System.out.println("Contestant must have valid ID");
			return;
		}
		
		if (!isIDValid(c.getID())) {
			ArrayList<Person> a = new ArrayList<Person>(15);
			for (Contestant t: getAllContestants())
				a.add((Person)t);
			
			// TODO: Exception or something?
			//c.setID(Utils.generateID(c, a));
			//System.out.println("Invalid contestant ID specified, generating.");
			System.out.println("Contestant must have valid ID");
			return;
		}
		
		allContestants.add(c);
		Collections.sort(allContestants, 
				ComparatorFactory.getComparator(ComparatorFactory.CONTNT_ID));
	}

	@Override
	public void removeContestant(Contestant target) {
		// is the contestant there?
		int i = Collections.binarySearch(allContestants, (Contestant)target,
				ComparatorFactory.getComparator(ComparatorFactory.CONTNT_ID));
		
		if (i < 0) {
			// i < 0 implies not found.
			return;
		}
		
		allContestants.remove(i);
		Collections.sort(allContestants, ComparatorFactory.getComparator(ComparatorFactory.CONTNT_ID));
	}

	@Override
	protected int getContestantIndexID(String id) {
		Contestant t = new Contestant();
		t.setID(id);

		return Collections.binarySearch(allContestants, t,
				ComparatorFactory.getComparator(ComparatorFactory.CONTNT_ID));
	}
	
	/**
	 * intGameData reads in a data file and builds a GameData object out
	 * of it, returning it to the user.
	 * 
	 * @param inputFile   file to be read in
	 * @return GameData object made out of file or null if season not created
	 * 
	 */
	public static GameData initGameData() {
		try {
			JSONUtils.readSeasonFile();
		} catch (FileNotFoundException e) {
			return null; 
		}
		currentGame = new GameData(JSONUtils.getContestants());
		currentGame.setTribeNames(JSONUtils.getTribe1(), JSONUtils.getTribe2());
		try {
			JSONUtils.readContestantFile();
			JSONUtils.readPlayerFile();
		} catch (FileNotFoundException e) { 
		}
		return (GameData)currentGame;
	}

	
	
	
	@Override
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		
		obj.put(KEY_NUM_CONTEST, new Integer(numContestants));
		JSONArray cons = new JSONArray();
		for (Contestant c: allContestants) {
			cons.add(c.toJSONObject());
		}
		
		JSONArray ts = new JSONArray();
		// TODO: only two tribes?
		ts.add(tribeNames[0]);
		ts.add(tribeNames[1]);
		
		
		obj.put(KEY_CONTESTANTS, cons);
		obj.put(KEY_TRIBES, ts);
		obj.put(KEY_WEEKS_REMAIN, new Integer(weeksRem));
		obj.put(KEY_WEEKS_PASSED, new Integer(weeksPassed));
		
		return obj;
	}
	
	@Override
	public void fromJSONString(String json) throws ParseException {
		JSONObject o = (JSONObject)JSONValue.parse(json);
		
		fromJSONObject(o);
	}

	@Override
	public void fromJSONObject(JSONObject obj) {
		numContestants = ((Number)obj.get(KEY_NUM_CONTEST)).intValue();
		
		
		allContestants = new ArrayList<Contestant>(numContestants);
		// load the contestant array.
		JSONArray cons = (JSONArray)obj.get(KEY_CONTESTANTS);
		for (Object o: cons) {
			Contestant c = new Contestant();
			c.fromJSONObject((JSONObject)o);
			allContestants.add(c);
		}
		
		// tribes
		JSONArray ts = (JSONArray)obj.get(KEY_TRIBES);
		tribeNames = new String[] { (String)ts.get(0), (String)ts.get(1) };
		
		// week info:
		weeksRem = ((Number)obj.get(KEY_WEEKS_REMAIN)).intValue();
		weeksPassed = ((Number)obj.get(KEY_WEEKS_PASSED)).intValue();
	}
	
	// TODO: Implement:
	@Override
	public String toString() {
		return "GameData<...>";
	}
	

	public static void main(String[] args) {
		GameData g = new GameData(6);
		
		String[] tribes = new String[] {"banana", "apple"};
		
		g.setTribeNames(tribes[0], tribes[1]);
		
		Contestant c1 = new Contestant("asd2", "Al", "Sd", tribes[1]);
		Contestant c2 = new Contestant("as", "John", "Silver", tribes[0]);
		
		g.addContestant(c1);
		g.addContestant(c2);
		
		System.out.println(g.toJSONString());
		
		GameData g2 = new GameData(6);
		try {
			g2.fromJSONString(g.toJSONString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
