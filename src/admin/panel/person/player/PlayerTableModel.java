package admin.panel.person.player;

import java.util.Comparator;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableRowSorter;

import admin.panel.person.PersonTableModel;
import data.Contestant;
import data.InvalidFieldException;
import data.User;

public class PlayerTableModel extends PersonTableModel<User> {

	private static final long serialVersionUID = 1L;

	// public static final int INDEX_SELECT = 0;
	public static final int INDEX_ID = 0;
	public static final int INDEX_LASTNAME = 1;
	public static final int INDEX_FIRSTNAME = 2;
	public static final int INDEX_POINTS = 3;
	public static final int INDEX_WEEKLY_PICK = 4;
	public static final int INDEX_ULT_PICK = 5;

	/**
	 * Creates the table model which controls the table's actions and data.
	 * @param sorter TODO
	 * @param users
	 *            The global reference to the actual GameData.
	 */
	public PlayerTableModel(JTable table, List<User> users) {
		super(table, users);
		
		columnNames = new String[] { "ID", "Last", "First", "Points", 
				"Weekly Pick", "Ultimate Pick" };

		sortColumn = INDEX_ID;
	}

	@Override
	public Object getValueAt(int row, int col) {
		User user = (User) globalData.get(row);
		
		switch (col) {
		case INDEX_ID:
			return user.getID();

		case INDEX_FIRSTNAME:
			return user.getFirstName();

		case INDEX_LASTNAME:
			return user.getLastName();

		case INDEX_POINTS:
			return user.getPoints();

		case INDEX_WEEKLY_PICK:
			return user.getWeeklyPick().toString();

		case INDEX_ULT_PICK:
			return user.getUltimatePick().toString();

		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		User user = (User) globalData.get(row);

		switch (col) {
		case INDEX_ID:
			// this can't be changed..
			break;

		case INDEX_FIRSTNAME:
			try {
				user.setFirstName((String) value);
			} catch (InvalidFieldException e1) {
			}
			break;

		case INDEX_LASTNAME:
			try {
				user.setLastName((String) value);
			} catch (InvalidFieldException e) {
			}
			break;

		default:
			// can't change here!
			break;
		}

		fireTableCellUpdated(row, col);
	}

	@Override
	protected void setComparators(TableRowSorter<PersonTableModel<User>> sort) {
		Comparator<Integer> intComp = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1-o2;
			}	
		};
		
		Comparator<String> strCompNoCase = new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareToIgnoreCase(o2);
			}
		};
		
		Comparator<String> strCompCase = new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		};
		
		Comparator<Contestant> conComp = new Comparator<Contestant>() {

			@Override
			public int compare(Contestant o1, Contestant o2) {
				return o1.compareTo(o2);
			}
		};
		
		sort.setComparator(INDEX_ID, strCompCase);
		sort.setComparator(INDEX_FIRSTNAME, strCompNoCase);
		sort.setComparator(INDEX_LASTNAME, strCompNoCase);
		sort.setComparator(INDEX_POINTS, intComp);
		sort.setComparator(INDEX_ULT_PICK, conComp);	
		sort.setComparator(INDEX_WEEKLY_PICK, conComp);
	}
}
