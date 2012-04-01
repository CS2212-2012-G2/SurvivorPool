package admin;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

import data.Contestant;
import data.Person;
import data.User;

public class Utils {

	// A clean way to handle themes with minimal code.
	enum GUITHEME {
		// ThemeName(Foreground Color,Background Color);
		Western(Color.WHITE, new Color(79, 33, 112), new Color(79, 33, 112)), 
		Snow(new Color(0, 0, 255), new Color(255, 255, 255), Color.BLUE), 
		BandW(new Color(255, 255, 255), new Color(0, 0, 0), Color.GRAY);
		Color fore, back, table;

		GUITHEME(Color f, Color b, Color t) {
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
	
	/**
	 * Generates the default table renderer for tables inside the client.
	 * @return
	 */
	public static TableCellRenderer buildDefaultRenderer() {
		TableCellRenderer renderer = new TableCellRenderer() {

			JLabel label = new JLabel();
	
			@Override
			public JComponent getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
	
				if (table.isRowSelected(row)) {
					label.setBackground(Utils.getThemeTableHighlight());
					label.setForeground(Utils.getThemeBG());
				} else {
					label.setBackground(UIManager.getColor("Table.background"));
					label.setForeground(UIManager.getColor("Table.foreground"));
				}
	
				label.setOpaque(true);
				label.setText("" + value);
	
				return label;
			}
	
		};
		
		return renderer;
	}

	public enum CompType {
		// Person:
		FIRST_NAME, LAST_NAME, ID,
		// Contestants:
		CONTNT_TRIBE, CONTNT_DATE,
		// Users:
		 USER_POINTS, USER_ULT_PICK, USER_WEEKLY_PICK
	}

	private static GUITHEME theme;

	public static GUITHEME getTheme() {
		return theme;
	}
	
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
		GUITHEME[] t = GUITHEME.values();
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
		GUITHEME[] t = GUITHEME.values();
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
	 * @param activeP
	 *            person to use data from.
	 * @param a
	 *            Data to check through
	 */
	public static String generateID(Person activeP, List<Person> a) {
		if (activeP.getLastName() == null || activeP.getFirstName() == null) {
			System.out.println("generateContestantID: first or last name null");
			return new String();
		}

		ArrayList<String> ids = new ArrayList<String>(a.size());
		for (Person p : a)
			if (p.getID() != null)
				ids.add(p.getID());

		String newID;
		String lastName = activeP.getLastName().toLowerCase()
				.replaceAll("\\s+", "");
		String firstName = activeP.getFirstName().toLowerCase();
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
	public static <T extends Person> int BinSearchSafe(List<T> list, T target,
			CompType compType) {

		Comparator<T> comp = (Comparator<T>) getComparator(compType, target.getClass());

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

	public static <T extends Person> Comparator<T> getComparator(CompType t, Class<T> targ) {
		switch (t) {
		case FIRST_NAME:
			return new Comparator<T>() {
				@Override
				public int compare(T o1, T o2) {
					String f1 = o1.getFirstName().toLowerCase();
					String f2 = o2.getFirstName().toLowerCase();
					return (f1.compareTo(f2));
				}
			};

		case LAST_NAME:
			return new Comparator<T>() {
				@Override
				public int compare(T o1, T o2) {
					String l1 = o1.getLastName().toLowerCase();
					String l2 = o2.getLastName().toLowerCase();
					return (l1.compareTo(l2));
				}
			};

		case ID:
			return new Comparator<T>() {
				@Override
				public int compare(T o1, T o2) {
					return (o1.getID().compareTo(o2.getID()));
				}
			};

		case CONTNT_DATE:
			if (targ == Contestant.class)
				return new Comparator<T>() {
					@Override
					public int compare(T c1, T c2) {
						return (((Contestant)c1).getCastDate() - 
								((Contestant)c2).getCastDate());
					}
				};

		case CONTNT_TRIBE:
			if (targ == Contestant.class)
				return new Comparator<T>() {
					@Override
					public int compare(T c1, T c2) {
						String t1 = ((Contestant)c1).getTribe();
						String t2 = ((Contestant)c2).getTribe();
						return t1.compareTo(t2);
					}
				};
				
		case USER_POINTS:
			if (targ == User.class)
				return new Comparator<T>() {
					@Override
					public int compare(T u1, T u2) {
						Integer p1 = ((User)u1).getPoints();
						Integer p2 = ((User)u2).getPoints();
						return (p1.compareTo(p2));
					}
				};

		case USER_ULT_PICK:
			if (targ == User.class)
				return new Comparator<T>() {
					Comparator<Contestant> comp = getComparator(CompType.ID, Contestant.class);
	
					@Override
					public int compare(T u1, T u2) {
						Contestant u1c = ((User)u1).getUltimatePick();
						Contestant u2c = ((User)u2).getUltimatePick();
						return comp.compare(u1c, u2c);
					}	
				};

		case USER_WEEKLY_PICK:
			return new Comparator<T>() {
				Comparator<Contestant> comp = getComparator(CompType.ID, Contestant.class);

				@Override
				public int compare(T u1, T u2) {
					Contestant u1c = ((User)u1).getWeeklyPick();
					Contestant u2c = ((User)u2).getWeeklyPick();
					return comp.compare(u1c, u2c);
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
		return val.matches(pattern);
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
	
	/**
	 * Converts a Number object into an Integer. Takes a cast and method call.
	 * This provides a simple wrapper.
	 * @param n	Number to convert
	 * @return	Integer representation of n
	 */
	public static Integer numToInt(Number n) {
		return (Integer)(n.intValue());
	}
	
	/**
	 * Wrapper for {@link Utils.numToInt(Number)}.
	 * @param o
	 * @return
	 * @see Utils.numToInt(Number)
	 */
	public static Integer numToInt(Object o) {
		if (o instanceof Number) {
			return numToInt((Number)o);
		}
		return null;
	}
}
