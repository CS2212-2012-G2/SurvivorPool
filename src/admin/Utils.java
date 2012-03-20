package admin;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import data.Contestant;
import data.Person;
import data.User;
import data.me.regexp.RE;

public class Utils {

	// A clean way to handle themes with minimal code.
	enum THEMES {
		// ThemeName(Foreground Color,Background Color);
		Western(new Color(255, 255, 255), new Color(79, 38, 100), Color.WHITE), Snow(
				new Color(0, 0, 255), new Color(255, 255, 255), Color.BLUE), BandW(
				new Color(255, 255, 255), new Color(0, 0, 0), Color.GRAY);
		Color fore, back, table;

		THEMES(Color f, Color b, Color t) {
			fore = f;
			back = b;
			table = t;
		}

		public Color getBackground() {
			return back;
		}

		public Color getForeground() {
			return fore;
		}

		public Color getTableHighlight() {
			return table;
		}
	};

	public enum CompType {
		// Contestants:
		CONTNT_FIRST_NAME, CONTNT_LAST_NAME, CONTNT_ID, CONTNT_TRIBE, CONTNT_DATE,
		// Users:
		USER_FIRST_NAME, USER_LAST_NAME, USER_ID, USER_POINTS, USER_ULT_PICK, USER_WEEKLY_PICK
	}

	private static THEMES theme = THEMES.Snow;

	/**
	 * The background colour of current theme
	 * 
	 * @return Color get the background colour of current theme
	 */
	public static Color getThemeBG() {
		return theme.getBackground();
	}

	/**
	 * The foreground colour of current theme
	 * 
	 * @return Color get the foreground colour of current theme
	 */
	public static Color getThemeFG() {
		return theme.getForeground();
	}

	/**
	 * The table highlight colour.
	 * 
	 * @return Color of the table to highlight.
	 */
	public static Color getThemeTableHighlight() {
		return theme.getTableHighlight();
	}

	/**
	 * Change the current theme
	 * 
	 * @see getThemes() to get all themes
	 * @param name
	 *            The theme's name to change to
	 */
	public static void changeTheme(String name) {
		THEMES[] t = THEMES.values();
		for (int i = 0; i < t.length; i++) {
			if (t[i].name().equalsIgnoreCase(name))
				theme = t[i];
		}
	}

	/**
	 * Gets a string array of all themes possible
	 * 
	 * @return String array with the themes names
	 */
	public static String[] getThemes() {
		THEMES[] t = THEMES.values();
		String[] themeString = new String[t.length];
		for (int i = 0; i < t.length; i++) {
			themeString[i] = t[i].name();
		}
		return themeString;
	}

	/**
	 * modified from http://today.java.net/pub/a/today/2003/10/14/swingcss.html
	 * Changes theme of the current component.If a component is a
	 * container(Panel,frame,etc.) The background and foreground of all the
	 * components within the container.
	 * 
	 * @param comp
	 *            The component to apply the theme
	 */
	//
	public static void style(Component comp) {

		comp.setForeground(theme.getForeground());
		comp.setBackground(theme.getBackground());
		if (!(comp instanceof Container)) {
			return;
		}

		Component[] comps = ((Container) comp).getComponents();
		for (int i = 0; i < comps.length; i++) {
			style(comps[i]);
		}
	}

	/**
	 * Uses the stored first and last name, and the currently running Game to
	 * generate a unique user ID.
	 * 
	 * @param activeCon
	 *            person to use data from.
	 * @param a
	 *            Data to check through
	 */
	public static String generateID(Person activeCon, List<Person> a) {
		if (activeCon.getLastName() == null || activeCon.getFirstName() == null) {
			System.out.println("generateContestantID: first or last name null");
			return new String();
		}

		ArrayList<String> ids = new ArrayList<String>(a.size());
		for (Person p : a)
			if (p.getID() != null)
				ids.add(p.getID());

		String newID;
		String lastName = activeCon.getLastName().toLowerCase()
				.replaceAll("\\s+", "");
		String firstName = activeCon.getFirstName().toLowerCase();
		int lastSub = Math.min(6, lastName.length());
		int num = 0;
		boolean valid = false;

		do {
			// take the first letter of first name
			// take substring of lastName length 6 or full name
			newID = firstName.charAt(0) + lastName.substring(0, lastSub);
			if (num != 0) {
				newID += Integer.toString(num);
			}
			num++;

			valid = true;
			for (String id : ids)
				if (id.equalsIgnoreCase(newID)) {
					valid = false;
					break;
				}
		} while (!valid); // check if the ID is present

		return newID;
	}

	/**
	 * Removes all 'null' items from a list.
	 * 
	 * @param l
	 * @return
	 */
	public static <T> List<T> noNullList(List<T> l) {
		List<T> list = new ArrayList<T>(l);

		int i = 0;
		while ((i = list.indexOf(null)) != -1)
			list.remove(i);

		return list;
	}

	/**
	 * Creates a new list of the objects cast as the type passed as target.
	 * WARNING: BESURE YOU KNOW THIS WILL WORK.
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T, V> List<V> castListElem(List<T> list, V target) {
		List<V> result = new ArrayList<V>(list.size());
		for (T elem : list) {
			result.add((V) elem);
		}
		return result;
	}

	/**
	 * removes null entries before performing binary search on a list.
	 * 
	 * @param list
	 * @param target
	 * @param comp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> int BinSearchSafe(List<T> list, T target,
			CompType compType) {

		Comparator<T> comp = null;
		if (target instanceof Contestant)
			comp = (Comparator<T>) getContComparator(compType);
		else if (target instanceof User)
			comp = (Comparator<T>) getUserComparator(compType);

		return Collections.binarySearch(list, target, comp);
	}

	/**
	 * Traverses a list of Persons for an ID.
	 * 
	 * @param list
	 * @param searchID
	 * @return Index if found, index <0 if not found.
	 */
	public static <T> int BinIDSearchSafe(List<T> list, String searchID) {
		List<String> idList = new ArrayList<String>(list.size());
		for (T elem : list) {
			if (elem instanceof Person) {
				idList.add(((Person) elem).getID());
			}
		}

		return Collections.binarySearch(idList, searchID);
	}

	public static Comparator<Contestant> getContComparator(CompType t) {
		switch (t) {
		case CONTNT_FIRST_NAME:
			return new Comparator<Contestant>() {
				@Override
				public int compare(Contestant c1, Contestant c2) {
					String f1 = c1.getFirstName().toLowerCase();
					String f2 = c2.getFirstName().toLowerCase();
					return (f1.compareTo(f2));
				}
			};

		case CONTNT_LAST_NAME:
			return new Comparator<Contestant>() {
				@Override
				public int compare(Contestant c1, Contestant c2) {
					String l1 = c1.getLastName().toLowerCase();
					String l2 = c2.getLastName().toLowerCase();
					return (l1.compareTo(l2));
				}
			};

		case CONTNT_ID:
			return new Comparator<Contestant>() {
				@Override
				public int compare(Contestant c1, Contestant c2) {
					return (c1.getID().compareTo(c2.getID()));
				}
			};

		case CONTNT_DATE:
			return new Comparator<Contestant>() {
				@Override
				public int compare(Contestant c1, Contestant c2) {
					return (c1.getCastDate() - c2.getCastDate());
				}
			};

		case CONTNT_TRIBE:
			return new Comparator<Contestant>() {
				@Override
				public int compare(Contestant c1, Contestant c2) {
					return (c1.getTribe().compareTo(c2.getTribe()));
				}
			};

		default:
			return null;
		}
	}

	public static Comparator<User> getUserComparator(CompType t) {
		switch (t) {
		case USER_FIRST_NAME:
			return new Comparator<User>() {
				@Override
				public int compare(User u1, User u2) {
					String f1 = u1.getFirstName().toLowerCase();
					String f2 = u2.getFirstName().toLowerCase();
					return (f1.compareTo(f2));
				}
			};

		case USER_LAST_NAME:
			return new Comparator<User>() {
				@Override
				public int compare(User u1, User u2) {
					String l1 = u1.getLastName().toLowerCase();
					String l2 = u2.getLastName().toLowerCase();
					return (l1.compareTo(l2));
				}
			};

		case USER_ID:
			return new Comparator<User>() {
				@Override
				public int compare(User u1, User u2) {
					return (u1.getID().compareTo(u2.getID()));
				}
			};

		case USER_POINTS:
			return new Comparator<User>() {
				@Override
				public int compare(User u1, User u2) {
					return (u1.getPoints() - u2.getPoints());
				}
			};

		case USER_ULT_PICK:
			return new Comparator<User>() {
				Comparator<Contestant> comp = getContComparator(CompType.CONTNT_ID);

				@Override
				public int compare(User u1, User u2) {
					return comp.compare(u1.getUltimatePick(),
							u2.getUltimatePick());
				}
			};

		case USER_WEEKLY_PICK:
			return new Comparator<User>() {
				Comparator<Contestant> comp = getContComparator(CompType.CONTNT_ID);

				@Override
				public int compare(User u1, User u2) {
					return comp.compare(u1.getWeeklyPick(), u2.getWeeklyPick());
				}
			};

		default:
			return null;
		}
	}

	/**
	 * Checks if string matches pattern.
	 * 
	 * @param val
	 *            The string to check for validity
	 * @param pattern
	 *            A regex pattern that has all possible valid values
	 * @return true if string matches pattern
	 */
	public static boolean checkString(String val, String pattern) {
		if (val == null)
			return false;
		if (val.length() == 0)
			return false;
		RE r = new RE(pattern);
		return r.match(val);
	}

	/**
	 * Capitalizes a string. Does not remove existing capitals.
	 * 
	 * @param s
	 * @return
	 */
	public static String strCapitalize(String s) {
		String result = "";

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if ((i + 1 < s.length()) && (c == ' ' || c == '-' || c == '_')) {
				result += c;
				result += Character.toUpperCase(s.charAt(++i));
			} else if (i == 0) {
				result += Character.toUpperCase(c);
			} else {
				result += c;
			}
		}

		return result;
	}
}
