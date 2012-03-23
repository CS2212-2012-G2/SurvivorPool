package admin.panel.person.contestant;

import java.util.Comparator;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableRowSorter;

import admin.panel.person.PersonTableModel;
import data.Contestant;
import data.InvalidFieldException;

public class ContestantTableModel extends PersonTableModel<Contestant> {
	private static final long serialVersionUID = 1L;

	// public static final int INDEX_SELECT = 0;
	public static final int INDEX_ID = 0;
	public static final int INDEX_LASTNAME = 1;
	public static final int INDEX_FIRSTNAME = 2;
	public static final int INDEX_TRIBE = 3;
	public static final int INDEX_DATECAST = 4;

	/**
	 * Creates the table model which controls the table's actions and data.
	 * @param sorter TODO
	 * @param _globaldata
	 *            The global data stored in GameData, this is done to maintain
	 *            data persistance with the two, while allowing order
	 *            manipulation.
	 */
	public ContestantTableModel(JTable table, List<Contestant> _globaldata) {
		super(table, _globaldata);
		
		columnNames = new String[] {
				"ID", "Last Name", "First Name", "Tribe", "Date Cast"
		};
		
		globalData = _globaldata;
		//data = new ArrayList<Contestant>(globalData);

		sortColumn = INDEX_ID;
	}

	@Override
	public Object getValueAt(int row, int col) {
		Contestant player = (Contestant) globalData.get(row);

		switch (col) {
		case INDEX_ID:
			return player.getID();

		case INDEX_FIRSTNAME:
			return player.getFirstName();

		case INDEX_LASTNAME:
			return player.getLastName();

		case INDEX_TRIBE:
			return player.getTribe();

		case INDEX_DATECAST:
			if (player.isCastOff())
				return new Integer(player.getCastDate());
			else
				return "Active";

		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		Contestant player = (Contestant) globalData.get(row);

		switch (col) {
		case INDEX_ID:
			// this can't be changed..
			break;

		case INDEX_FIRSTNAME:
			try {
				player.setFirstName((String) value);
			} catch (InvalidFieldException e) {
			}
			break;

		case INDEX_LASTNAME:
			try {
				player.setLastName((String) value);
			} catch (InvalidFieldException e) {
			}
			break;

		case INDEX_TRIBE:
			try {
				player.setTribe((String) value);
			} catch (InvalidFieldException e) {
			}
			break;

		case INDEX_DATECAST:
			// can't change here!
			break;
		}

		fireTableCellUpdated(row, col);
	}

	@Override
	protected void setComparators(TableRowSorter<PersonTableModel<Contestant>> sort) {
		Comparator<String> strCompNoCase = new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareToIgnoreCase(o2);
			}
		};
		
		Comparator<Object> activeComp = new Comparator<Object>() {

			@Override
			public int compare(Object o1, Object o2) {
				if (o1 instanceof String && o2 instanceof String) {
					return 0;
				}
				
				if (o1 instanceof Integer && o2 instanceof String) {
					return -1;
				}
				
				if (o1 instanceof String && o2 instanceof Integer) {
					return +1;
				}
				
				// both are ints:
				return (Integer)o1 - (Integer)o2;
			}
			
		};
		
		sort.setComparator(INDEX_ID, strCompNoCase);
		sort.setComparator(INDEX_FIRSTNAME, strCompNoCase);
		sort.setComparator(INDEX_LASTNAME, strCompNoCase);
		sort.setComparator(INDEX_DATECAST, activeComp);
		sort.setComparator(INDEX_TRIBE, strCompNoCase);	
	}
}
