package data;

import java.util.*;

import data.bonus.BonusQuestion;



import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * AdminPanel class represents the administrator of the game, and will have the
 * ability to add users and contestants (until the game begins), as well as
 * handling the bonus questions.
 * 
 * @author Graem Littleton, Justin McDonald, Jonathan Demelo, Kevin Brightwell,
 *         Ramesh Raj
 * 
 */
public class AdminPanel {

	private GameData game; // main GameData item to be carried over to all users
	private ArrayList players; // list of players
	private ArrayList bonusQuestions; // list of possible bonus questions

	/**
	 * Constructor for the AdminPanel class. Fetches a new GameData object and
	 * prepares the ArrayLists that will be put to use.
	 */

	public AdminPanel() {
	}

	// ----------------- ACCESSOR METHODS ----------------- //

	/**
	 * getBonusQuestion returns a bonus question to be answered by a User.
	 * 
	 * @return Bonus Questions
	 */
	public BonusQuestion getBonusQuestion() {
		throw new NotImplementedException();
	}

	/**
	 * getUser returns a User object based off of an entered unique ID.
	 * 
	 * @param uniqueId
	 *            target user's unique id
	 * @return target user
	 */

	public User getUser(String uniqueId) {
		throw new NotImplementedException();
	}

	// ----------------- MUTATOR METHODS ----------------- //

	/**
	 * addContestant adds a contestant to the array stored in GameData. Cannot
	 * be used if the game has begun.
	 * 
	 * @param cont
	 *            contestant to be added
	 */

	public void addContestant(Contestant cont) {
	}

	/**
	 * addBonusQuestion adds a bonus question the list of bonus questions.
	 * Question cannot be more than 200 characters in length.
	 * 
	 * @param bonus
	 *            bonus question to be added
	 */

	public void addBonusQuestion(String bonus) {
		throw new NotImplementedException();
	}

	/**
	 * addUser adds a user to the array list storing the complete list of people
	 * playing Survivor Pool.
	 * 
	 * @param user
	 *            user to be added
	 */
	public void addUser(User user) {
		throw new NotImplementedException();
	}

	// I'm actually not sure why we have this method here
	public void setGame() {
		throw new NotImplementedException();
	}

	/**
	 * removeBonusQuestion removes a specified bonus question from the array
	 * list.
	 * 
	 * @param target
	 *            desired bonus question
	 */
	public void removeBonusQuestion(BonusQuestion target) {
		throw new NotImplementedException();
	}

}
