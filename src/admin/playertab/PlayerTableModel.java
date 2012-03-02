package admin.playertab;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import data.Contestant;

public class PlayerTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private String[] columnNames;
	private List<Contestant> data;
	private boolean frozen = false;
	
	public static final int INDEX_SELECT = 0;
	public static final int INDEX_ID = 1;
	public static final int INDEX_LASTNAME = 2;
	public static final int INDEX_FIRSTNAME = 3;
	public static final int INDEX_TRIBE = 4;
	public static final int INDEX_DATECAST = 5;
	
	private int sortColumn = INDEX_LASTNAME;
	
	/**
	 * Creates the table model which controls the table's actions and data.
	 * @param _columnNames The column names
	 * @param _data	The data to work on/with. This could be an empty list, but
	 * 			NOT null.
	 */
	public PlayerTableModel(List<Contestant> _data) {
		columnNames = new String[] {
				"Select", "ID", "Last Name", "First Name", "Tribe", "Date Cast"
		};
		data = _data;
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
        		return new Integer(-1);
        
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
        		player.setFirstName((String)value);
        	break;
        
        case INDEX_LASTNAME:
        	if (!frozen)
        		player.setLastName((String)value);
        	break;
        
        case INDEX_TRIBE:
        	player.setTribe((String)value);
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

    public void addEmptyRow() {
    	// TOOD: Make this create a new contestant ID
        data.add(new Contestant());
        fireTableRowsInserted(
           data.size() - 1,
           data.size() - 1);
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
	 * @return
	 */
	public boolean isFrozen() {
		return frozen;
	}
	
	/**
	 * Sorts the table by the column specified, will update the table.
	 * @param col -1 for stored value, else the column passed. Default
	 * to no sorting otherwise.
	 */
	private void sortTableBy(int col) {
		Comparator<Contestant> comp;
		
		// use the stored column if -1 is passed.
		col = (col == -1 ? sortColumn : col);
		
		switch (col) {
        case INDEX_ID:
        	comp = new Contestant.ComparatorID();
        	break;
        
        case INDEX_FIRSTNAME:
        	comp = new Contestant.ComparatorFirstName();
        	break;
        
        case INDEX_LASTNAME:
        	comp = new Contestant.ComparatorFirstName();
        	break;
        
        case INDEX_TRIBE:
        	comp = new Contestant.ComparatorLastName();
        	break;
        	
        case INDEX_DATECAST:
        	comp = new Contestant.ComparatorDate();
        	break;
        	
        default:
        	return;
        }
		
		Collections.sort(data, comp);
		fireTableDataChanged();
		
		sortColumn = col;
	}
	
	/**
	 * Sorts the table using {@link PlayerTableModel.sortTableBy} with 
	 * the current sorted column.
	 */
	private void sortTable() {
		sortTableBy(-1);
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
		boolean found = false;
		
		// update the contestant if they are present
		/*for (Contestant dataCon: data)
			if (dataCon.getID().equals(c.getID())) {
				int i = data.indexOf(dataCon);
				data.set(i, c);
				found = true;
				// force the table to update.
				break;
			}*/
		if (!found)
			data.add(c);
		
		sortTable();
	}

}
