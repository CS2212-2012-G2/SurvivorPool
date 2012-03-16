package admin.contestanttab;

import data.Contestant;
import data.GameData;
import data.InvalidFieldException;


/*
 * Test Case: Admin User casting off after season started
 */
public class TestDriver_CastOffContestant{
	
	//Constant values for testing purposes
	private static final Integer NUM_TEST_CONTESTANTS = 6;
	private static final Integer TEST_BET_AMOUNT = 5;
	private static final String TEST_TRIBE_ONE = "Alive";
	private static final String TEST_TRIBE_TWO = "Dead";
	
	public static void main(String[] args) throws InvalidFieldException {
		
		//Instantiate a new GameData object with 6 test contestants.
		GameData g = new GameData(NUM_TEST_CONTESTANTS);
		
		//Instantiate valid tribe names for the game.
		g.setTribeNames(TEST_TRIBE_ONE, TEST_TRIBE_TWO);
		
		//Strings to be used to compose test Contestants, so that a season may be started.
		String[] testID = {"MJ", "BP", "JS", "AJ", "AE", "JB"};
		String[] testFirstName = {"Michael", "Brad", "Jessica", "Angelina", "Albert", "Al"};
		String[] testLastName = {"Jackson", "Pitt", "Simpson", "Jolie", "Einstein", "Capone"};
		String[] testTribe = {TEST_TRIBE_TWO, TEST_TRIBE_ONE, TEST_TRIBE_ONE, TEST_TRIBE_ONE, TEST_TRIBE_TWO, TEST_TRIBE_TWO};
		
		//Instantiate and add the 6 test contestants to the game.
		for (int i = 0; i < 6; i++){
			Contestant c = new Contestant(testID[i], testFirstName[i], testLastName[i], testTribe[i]);
			g.addContestant(c);
		}
		
		//Start the season, so that contestants may be cast off, and the weeks can be advanced.
		g.startSeason(TEST_BET_AMOUNT);
		
		
		// TEST 1: CAST OFF THE (TEST) CONTESTANT "JESSICA SIMPSON"
		g.getAllContestants().get(2).toCastOff();	//set the next contestant to be cast off
		g.advanceWeek();							//advance the week, committing the cast off
		
		// CONFIRMATION:
		if (g.getAllContestants().get(2).getCastDate() == -1)
			System.out.println("Test 1: FAILED\n\tJessica Simpson was not cast off.\n");
		else System.out.println("Test 1: PASSED\n\tJessica Simpson was cast off during Week " + g.getAllContestants().get(2).getCastDate() + "\n");
		
		
		// TEST 2: CAST OFF THE (TEST) CONTESTANT "MICHAEL JACKSON"
		g.getAllContestants().get(0).toCastOff();	//set the next contestant to be cast off
		g.advanceWeek();							//advance the week, committing the cast off
		
		// CONFIRMATION:
		if (g.getAllContestants().get(0).getCastDate() == -1)
			System.out.println("Test 2: FAILED\n\tMichael Jackson was not cast off.\n");
		else System.out.println("Test 2: PASSED\n\tMichael Jackson was cast off during Week " + g.getAllContestants().get(0).getCastDate() + "\n");
		
	}
}
