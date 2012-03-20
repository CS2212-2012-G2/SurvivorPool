/**
 * 
 */
package admin;



import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import data.Contestant;
import data.GameData;
import data.InvalidFieldException;
import data.InvalidFieldException.Field;
import data.Person;
import data.User;

/**
 * @author kevin
 *
 */
public abstract class PersonTableModel<P> extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	
	protected String[] columnNames;
	protected List<P> data;
	
	protected int sortColumn;
	protected List<P> globalData;
	
	/**
	 * Creates the table model which controls the table's actions and data.
	 * @param _globaldata The global data stored in GameData, this is done to
	 * 			maintain data persistance with the two, while allowing order 
	 * 			manipulation.
	 */
	public PersonTableModel(List<P> _globaldata) {
		columnNames = new String[] {
				"ID", "Last Name", "First Name", "Tribe", "Date Cast"
		};
		
		globalData = _globaldata;
		data = new ArrayList<P>(globalData);
		
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
	public abstract Object getValueAt(int row, int col); 
	
	@Override
    public boolean isCellEditable(int row, int col) { 
		// Always uneditable
		return false;
	}
    
	@Override
	public abstract void setValueAt(Object value, int row, int col);
	
	/**
	 * Checks if the table has an empty row.
	 * @return
	 */
	public boolean hasEmptyRow() {
        if (data.size() == 0) 
        	return false;
        
        Person p = (Person)data.get(data.size() - 1);
        
        if (p.getFirstName().trim().equals("") &&
        		p.getLastName().trim().equals("") &&
        		p.getID().equals(""))
        	return true;
        else
        	return false;
    }

	/**
	 * Gets a person based on the row passed, this is used to read clicks.
	 * @param row The row to gather from
	 * @return Data contained in the Row in the Generic specified form
	 */
    public P getByRow(int row) {
    	return (row > -1 ? data.get(row) : null);
    }
    
    /**
     * Gets the row number of a User's ID
     * @param u The user to find
     * @return Row number, -1 if not found
     */
    public int getRowByPerson(P p) {
    	if (p == null) 
    		return -1;
    	
    	String id = ((Person)p).getID();
    	
    	for (int i = 0; i < data.size(); i++) {
    		Person t = (Person)data.get(i);
    		if (t.getID().equals(id)) {
    			return i;
    		}
    	}
    	
    	return -1;
    }
	
	/**
	 * Sorts the table by the column specified, will update the table.
	 * @param col -1 for stored value, else the column passed. Default
	 * to no sorting otherwise.
	 */
	protected abstract void sortTableBy(int col);
	
	/**
	 * Sorts the table using sortTableBy with the current sorted column.
	 */
	protected void sortTable() {
		sortTableBy(-1);
	}
	
	/**
	 * Adds a person, resorts the table. Updates the stored game data.
	 * @param c 
	 * @throws InvalidFieldException Thrown if ID already in use.
	 */
	protected void addPerson(P p) throws InvalidFieldException {
		GameData g = GameData.getCurrentGame();
		
		if (p instanceof Contestant)
			g.addContestant((Contestant)p);
		else
			g.addUser((User)p);
		
		data.add(p);
		sortTable();
	}
	
	/**
	 * Removes a person from the table and GameData.
	 * @param p Person to remove.
	 */
	public void removePerson(P p) {
		data.remove(p);
		sortTable();
		
		GameData g = GameData.getCurrentGame();
		
		if (p instanceof Contestant)
			g.removeContestant((Contestant)p);
		else if (p instanceof User) 
			g.removeUser((User)p);
	}
	
	/**
	 * Updates the stored person with the same ID to hold the currently
	 * stored information. Overwrites everything in the current position, also
	 * will resort the data table.
	 * <br>
	 * If the ID is not present, it WILL create a new entry.
	 * @param c New contestant data.
	 * @throws InvalidFieldException Thrown if attempting to add a contestant which has an ID already present
	 */
	public void updatePerson(P p) throws InvalidFieldException {
		// is the ID in use in the game data?
		GameData g = GameData.getCurrentGame();
		
		Person person = (Person)p;
		
		List<Person> list;
		boolean idUsed;
		InvalidFieldException ie;
		
		if (person instanceof Contestant) {
			list = Utils.castListElem(g.getAllContestants(), 
					(Person)(new User()));
			idUsed = g.isContestantIDInUse(person.getID());
			ie = new InvalidFieldException(Field.CONT_ID_DUP, 
					"Invalid ID (in use)");
		} else {
			list = Utils.castListElem(g.getAllUsers(), 
					(Person)(new User()));
			idUsed = g.isUserIDInUse(person.getID());
			ie = new InvalidFieldException(Field.USER_ID_DUP, 
					"Invalid ID (in use)");
		}
		
		boolean inGame = list.contains(person);
		
		if (!inGame && !idUsed ) {
			addPerson(p);
		} else if (!inGame && idUsed) {
			throw ie;
		}
		else if (inGame && idUsed) {
			// we know the contestant is in the game, AND the ID is in use
			// try to find if its in the game otherwise, if it is, then we 
			// have a problem, otherwise, no problem. 
			// JESUS CHRIST THIS LOGIC SUCKED.
			for (Person t: list) {
				if (t.getID().equals(person.getID()) && t != person) {
					throw ie;
				}
			}
		}
		
		sortTable();
	}
	
	/**
	 * 
	 * TODO:
	 * @author kevin
	 *
	 */
	public class SortColumnAdapter extends MouseAdapter {
		
		public void mouseClicked(MouseEvent e) {
	        JTable table = ((JTableHeader)e.getSource()).getTable();
	        TableColumnModel colModel = table.getColumnModel();
	        
	        // get the person referenced
			@SuppressWarnings("unchecked")
			PersonTableModel<P> model = (PersonTableModel<P>)table.getModel();
	        P p = model.getByRow(table.getSelectedRow());

	        // The index of the column whose header was clicked
	        int vIndex = colModel.getColumnIndexAtX(e.getX());

	        // Return if not clicked on any column header
	        if (vIndex == -1) {
	            return;
	        }
	        
	        int mIndex = table.convertColumnIndexToModel(vIndex);
	        
	        // we have the column index, sort the data
	        sortTableBy(mIndex);
	        
	        // reset the selection to that row
	        int r = model.getRowByPerson(p);
	        table.setRowSelectionInterval(r, r);
	    }
	}
}
