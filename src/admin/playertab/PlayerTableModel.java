package admin.playertab;

import java.util.Vector;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import data.Contestant;
import data.User;

public class PlayerTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private String[] columnNames;
	private Vector data;
	private boolean frozen = false;
	
	public static final int INDEX_ID = 0;
	public static final int INDEX_LASTNAME = 1;
	public static final int INDEX_FIRSTNAME = 2;
	public static final int INDEX_TRIBE = 3;
	public static final int INDEX_DATECAST = 4;
	
	
	public PlayerTableModel(String[] _columnNames) {
		columnNames = _columnNames;
		data = new Vector<User>();
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
        	return new Integer(player.getID());
        
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
        data.add(new Contestant(-1));
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

}
