package admin.playertab;

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

import admin.PersonTableModel;
import admin.Utils;
import data.GameData;
import data.InvalidFieldException;
import data.InvalidFieldException.Field;
import data.User;


public class PlayerTableModel extends PersonTableModel<User> {

	private static final long serialVersionUID = 1L;
	
	//public static final int INDEX_SELECT = 0;
	public static final int INDEX_ID = 0;
	public static final int INDEX_LASTNAME = 1;
	public static final int INDEX_FIRSTNAME = 2;
	public static final int INDEX_POINTS	= 3;
	public static final int INDEX_WEEKLY_PICK = 4;
	public static final int INDEX_ULT_PICK = 5;
	
	/**
	 * Creates the table model which controls the table's actions and data.
	 * @param users The global reference to the actual GameData.
	 */
	public PlayerTableModel(JTable table, List<User> users) {
		super(table, users);
		
		columnNames = new String[] { "ID", "Last", "First", "Points", 
				"Weekly Pick", "Ultimate Pick" };
		
		sortColumn = INDEX_ID;
	}
    
	@Override
	public Object getValueAt(int row, int col) {
        User user = (User)data.get(row);
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
		User user = (User)data.get(row);
        
        switch (col) {
        case INDEX_ID:
        	// this can't be changed..
        	break;
        
        case INDEX_FIRSTNAME:
        	try {
				user.setFirstName((String)value);
			} catch (InvalidFieldException e1) {}
        	break;
        
        case INDEX_LASTNAME:
        	try {
				user.setLastName((String)value);
			} catch (InvalidFieldException e) { }
        	break;
        
        default:
        	// can't change here!
        	break;
        }
        
        fireTableCellUpdated(row, col);
    }
	
    /**
	 * Sorts the table by the column specified, will update the table.
	 * @param col -1 for stored value, else the column passed. Default
	 * to no sorting otherwise.
	 */
	protected void sortTableBy(int col) {
		Comparator<User> comp;
		
		// use the stored column if -1 is passed.
		col = (col == -1 ? sortColumn : col);
		
		switch (col) {
        case INDEX_ID:
        	comp = Utils.getUserComparator(Utils.CompType.USER_ID);
        	break;
        
        case INDEX_FIRSTNAME:
        	comp = Utils.getUserComparator(Utils.CompType.USER_FIRST_NAME);
        	break;
        
        case INDEX_LASTNAME:
        	comp = Utils.getUserComparator(Utils.CompType.USER_LAST_NAME);
        	break;
        
        case INDEX_POINTS:
        	comp = Utils.getUserComparator(Utils.CompType.USER_POINTS);
        	break;
        	
        case INDEX_ULT_PICK:
        	comp = Utils.getUserComparator(Utils.CompType.USER_ULT_PICK);
        	break;
        	
        case INDEX_WEEKLY_PICK:
        	comp = Utils.getUserComparator(Utils.CompType.USER_WEEKLY_PICK);
        	break;

        // others aren't valid to sort by (too ambiguous)
        default:
        	return;
        }
		
		Collections.sort(data, comp);
		fireTableDataChanged();
		
		sortColumn = col;
	}
}
