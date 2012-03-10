package admin.contestanttab;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import data.GameData;
import data.InvalidFieldException;

import admin.ComparatorFactory;
import admin.Utils;

import admin.data.*;

public class ContestantTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private String[] columnNames;
	private List<Contestant> data;
	private boolean frozen = false;
	
	//public static final int INDEX_SELECT = 0;
	public static final int INDEX_ID = 0;
	public static final int INDEX_LASTNAME = 1;
	public static final int INDEX_FIRSTNAME = 2;
	public static final int INDEX_TRIBE = 3;
	public static final int INDEX_DATECAST = 4;
	
	private int sortColumn = INDEX_ID;
	private List<Contestant> globalData;
	
	/**
	 * Creates the table model which controls the table's actions and data.
	 * @param _globaldata The global data stored in GameData, this is done to
	 * 			maintain data persistance with the two, while allowing order 
	 * 			manipulation.
	 */
	public ContestantTableModel(List<Contestant> _globaldata) {
		columnNames = new String[] {
				"ID", "Last Name", "First Name", "Tribe", "Date Cast"
		};
		globalData = _globaldata;
		data = new ArrayList<Contestant>(globalData.size());
	}
	
	@Override
	public String getColumnName(int col) {
        return columnNames[col].toString();
    }
	
	@Override
    public int getRowCount() {
		return data.size(); 
	}
	
	@Override
    public int getColumnCount() {
		return columnNames.length; 
	}
    
	@Override
	public Object getValueAt(int row, int col) {
        Contestant player = (Contestant)data.get(row);
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
    public boolean isCellEditable(int row, int col) { 
		switch (col) {   
        // conditionally editable:
        case INDEX_FIRSTNAME:
        case INDEX_LASTNAME:	
        	return (!frozen);

        // always editable:
        case INDEX_TRIBE:
        	return true;
        	
        case INDEX_ID:
        case INDEX_DATECAST:	
        default:
        	// this can't be changed..
        	return false;
        }
	}
    
	@Override
	public void setValueAt(Object value, int row, int col) {
		Contestant player = (Contestant)data.get(row);
        
        switch (col) {
        case INDEX_ID:
        	// this can't be changed..
        	break;
        
        case INDEX_FIRSTNAME:
        	if (!frozen)
        		try { 
        			player.setFirstName((String)value); 
        		} catch (InvalidFieldException e) { }
        	break;
        
        case INDEX_LASTNAME:
        	if (!frozen)
        		try {
        			player.setLastName((String)value);
        		} catch (InvalidFieldException e) { }
        	break;
        
        case INDEX_TRIBE:
        	try {
        		player.setTribe((String)value);
        	} catch (InvalidFieldException e) { }
        	break;
        	
        case INDEX_DATECAST:
        	// can't change here!
        	break;
        }
        
        fireTableCellUpdated(row, col);
    }
	
	public boolean hasEmptyRow() {
        if (data.size() == 0) 
        	return false;
        
        Contestant player = (Contestant)data.get(data.size() - 1);
        
        if (player.getFirstName().trim().equals("") &&
        		player.getLastName().trim().equals("") &&
        		player.getTribe().trim().equals("") &&
        		player.getCastDate() == -1)
        	return true;
        else
        	return false;
    }

	/**
	 * Gets a Contestant based on the row passed, this is used to read clicks.
	 * @param row The row to gather from
	 * @return Data contained in the Row in a Contestant form
	 */
    public Contestant getByRow(int row) {
    	return data.get(row);
    }

	/**
	 * Called when a season is started, some data can be changed after, others
	 * can't.
	 */
	public void freezeData() {
		frozen  = true;
	}
	
	/**
	 * Used to check if data is Frozen, only some fields are editable if frozen
	 * @return wether the model's data is frozen or not. 
	 */
	public boolean isFrozen() {
		return frozen;
	}
	
	/**
	 * Sorts the table by the column specified, will update the table.
	 * @param col -1 for stored value, else the column passed. Default
	 * to no sorting otherwise.
	 */
	protected void sortTableBy(int col) {
		Comparator<Contestant> comp;
		
		// use the stored column if -1 is passed.
		col = (col == -1 ? sortColumn : col);
		
		switch (col) {
        case INDEX_ID:
        	comp = ComparatorFactory.getComparator(ComparatorFactory.CONTNT_ID);
        	break;
        
        case INDEX_FIRSTNAME:
        	comp = ComparatorFactory.getComparator(ComparatorFactory.CONTNT_FIRST_NAME);
        	break;
        
        case INDEX_LASTNAME:
        	comp = ComparatorFactory.getComparator(ComparatorFactory.CONTNT_LAST_NAME);
        	break;
        
        case INDEX_TRIBE:
        	comp = ComparatorFactory.getComparator(ComparatorFactory.CONTNT_TRIBE);
        	break;
        	
        case INDEX_DATECAST:
        	comp = ComparatorFactory.getComparator(ComparatorFactory.CONTNT_DATE);
        	break;
        	
        default:
        	return;
        }
		
		Collections.sort(data, comp);
		fireTableDataChanged();
		
		sortColumn = col;
	}
	
	/**
	 * Sorts the table using sortTableBy with the current sorted column.
	 */
	protected void sortTable() {
		sortTableBy(-1);
	}
	
	/**
	 * Adds a contestant, resorts the table. Updates the stored game data.
	 * @param c 
	 */
	private void addContestant(Contestant c) {
		data.add(c);
		sortTable();
		
		GameData.getCurrentGame().addContestant(c);
	}
	
	private void removeContestant(Contestant c) {
		data.remove(c);
		sortTable();
		
		GameData.getCurrentGame().removeContestant(c);
	}
	
	/**
	 * Updates the stored contestant with the same ID to hold the currently
	 * stored information. Overwrites everything in the current position, also
	 * will resort the data table.
	 * <br>
	 * If the ID is not present, it WILL create a new entry (no sort).
	 * @param c New contestant data.
	 */
	public void updateContestant(Contestant c) {
		int index = Utils.BinSearchSafe(globalData, c,
				ComparatorFactory.getComparator(ComparatorFactory.CONTNT_ID));
		
		if (index >= 0) {
			try { 
				globalData.get(index).update(c); 
			} catch (InvalidFieldException e) { }
		} else {
			addContestant(c);
		}
		
		sortTable();
	}
	
	protected class SortColumnAdapter extends MouseAdapter {
		
		public void mouseClicked(MouseEvent e) {
	        JTable table = ((JTableHeader)e.getSource()).getTable();
	        TableColumnModel colModel = table.getColumnModel();

	        // The index of the column whose header was clicked
	        int vIndex = colModel.getColumnIndexAtX(e.getX());

	        // Return if not clicked on any column header
	        if (vIndex == -1) {
	            return;
	        }
	        
	        int mIndex = table.convertColumnIndexToModel(vIndex);
	        
	        // we have the column index, sort the data
	        sortTableBy(mIndex);
	    }
	}
}
