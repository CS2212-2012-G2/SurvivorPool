package admin.contestanttab;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import admin.PersonTableModel;
import admin.Utils;
import data.Contestant;
import data.GameData;
import data.InvalidFieldException;
import data.InvalidFieldException.Field;

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
	 * 
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
		data = new ArrayList<Contestant>(globalData);

		sortColumn = INDEX_ID;

	}

	@Override
	public Object getValueAt(int row, int col) {
		Contestant player = (Contestant) data.get(row);

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
		Contestant player = (Contestant) data.get(row);

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

	/**
	 * Sorts the table by the column specified, will update the table.
	 * 
	 * @param col
	 *            -1 for stored value, else the column passed. Default to no
	 *            sorting otherwise.
	 */
	protected void sortTableBy(int col) {
		Comparator<Contestant> comp;

		// use the stored column if -1 is passed.
		col = (col == -1 ? sortColumn : col);

		switch (col) {
		case INDEX_ID:
			comp = Utils.getContComparator(Utils.CompType.CONTNT_ID);
			break;

		case INDEX_FIRSTNAME:
			comp = Utils.getContComparator(Utils.CompType.CONTNT_FIRST_NAME);
			break;

		case INDEX_LASTNAME:
			comp = Utils.getContComparator(Utils.CompType.CONTNT_LAST_NAME);
			break;

		case INDEX_TRIBE:
			comp = Utils.getContComparator(Utils.CompType.CONTNT_TRIBE);
			break;

		case INDEX_DATECAST:
			comp = Utils.getContComparator(Utils.CompType.CONTNT_DATE);
			break;

		default:
			return;
		}

		Collections.sort(data, comp);
		fireTableDataChanged();

		sortColumn = col;
	}
}
