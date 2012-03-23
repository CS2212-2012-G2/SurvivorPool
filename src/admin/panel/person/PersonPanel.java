package admin.panel.person;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import admin.MainFrame;
import admin.Utils;
import admin.panel.person.contestant.ContestantTableModel;
import admin.panel.person.player.PlayerTableModel;
import data.Contestant;
import data.GameData;
import data.InvalidFieldException;
import data.Person;
import data.User;

public abstract class PersonPanel<P extends Person> extends JPanel implements
		MouseListener, Observer {

	private static final long serialVersionUID = 1L;

	protected JButton btnSave;

	protected PersonFields<P> personFields;

	protected JTable table;
	protected PersonTableModel<P> tableModel;
	protected JTableHeader header;

	protected JButton btnAddNew;
	protected JButton btnDelete;

	// vars:
	protected boolean isNewPerson = false;
	protected boolean fieldsChanged = false;

	private boolean usingContestants;

	/**
	 * THIS VARIABLE IS A REFERENCE MAINTAINED INTERNALLY. DO NOT ADJUST UNLESS
	 * YOU KNOW WHAT YOU ARE DOING.
	 */
	protected P loadedPerson;

	@SuppressWarnings("unchecked")
	public PersonPanel(P type) {
		super();

		usingContestants = (type instanceof Contestant);

		// ////////////////////////////
		// Top Panel:
		// ////////////////////////////
		// left to subclass:
		btnSave = new JButton("Save");

		// buttons:

		// ////////////////////////////
		// Mid (table!)
		// ////////////////////////////
	
		boolean tableValuesPresent;
		table = new JTable();
		
		if (usingContestants) {
			List<Contestant> cons = GameData.getCurrentGame().getAllContestants();
			tableModel = (PersonTableModel<P>) new ContestantTableModel(table, cons);
			tableValuesPresent = cons.size() > 0;
		} else { 
			List<User> users = GameData.getCurrentGame().getAllUsers();
			tableModel = (PersonTableModel<P>) new PlayerTableModel(table, users);
			tableValuesPresent = users.size() > 0;
		}
		
		TableRowSorter<PersonTableModel<P>> sort = new TableRowSorter<PersonTableModel<P>>(
				tableModel);
		tableModel.setComparators(sort);

		table.setModel(tableModel);
		table.setRowSorter(sort);
		sort.toggleSortOrder(PersonTableModel.INDEX_ID);

		header = table.getTableHeader();

		// ////////////////////////////
		// Bottom
		// ////////////////////////////
		btnAddNew = new JButton("New");
		btnDelete = new JButton("Delete");

		GameData.getCurrentGame().addObserver(this);
	}

	protected abstract void setToolTips();

	/**
	 * Builds the top panel including all the editable information
	 */
	protected abstract void buildTopPanel();

	/**
	 * Builds the panel containing the JTable
	 */
	protected void buildTablePanel() {
		JPanel panel = new JPanel();

		// settings:
		header.setReorderingAllowed(false); // no moving.
		table.setColumnSelectionAllowed(true);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// header.addMouseListener(tableModel.new SortColumnAdapter());

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
		table.setDefaultRenderer(Object.class, renderer);

		JScrollPane scroll = new JScrollPane(table);

		panel.setLayout(new BorderLayout(5, 5));
		panel.add(scroll, BorderLayout.CENTER);

		add(panel, BorderLayout.CENTER);

		// add the mouse listener to all components.
		for (Component c : scroll.getComponents()) {
			c.addMouseListener(this);
		}
	}

	/**
	 * Helper method to build the bottom panel of the container
	 */
	protected abstract void buildBottomPanel();

	/**
	 * Gets the information from the edit pane and returns a reference to that
	 * person. This is the same person stored in GameData. 
	 * 
	 * @return Current contestant loaded
	 * @throws InvalidFieldException
	 *             Thrown on any bad fields passed
	 */
	protected P getPerson() throws InvalidFieldException {
		personFields.getFromPane(loadedPerson);
		return loadedPerson;
	}

	/**
	 * Returns an object reference to a new person, dependant on what is needed
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private P newPerson() {
		if (usingContestants) {
			return (P)(new Contestant());
		} else {
			return (P)(new User());
		}
	}

	/**
	 * Sets the panel to the passed Contestant value. If newContestant is true,
	 * then it loads a NEW contestant object, otherwise it uses the reference
	 * passed in.
	 * 
	 * @param p the Person to load information from
	 * @param newContestant
	 */
	protected void setPanelPerson(P p, boolean newPerson) {
		if (getFieldsChanged()) {
			System.out.println("Player panel changing, fields modified.");
			try {
				savePerson();
			} catch (InvalidFieldException e) {
				setExceptionError(e);
				return;
			}
		}

		isNewPerson = newPerson;

		if (isNewPerson) {
			loadedPerson = newPerson();
			btnSave.setText("Add");
		} else {
			if (loadedPerson == p) {
				return; // don't need to set it then..
			}
			
			loadedPerson = p;
			btnSave.setText("Save");
		}

		// let the edit pane handle most
		personFields.setEditPane(p, newPerson);

		// delete button activation
		btnDelete.setEnabled(table.getRowCount() > 0);

		if (newPerson || p == null) {
			// we don't want any rows selected
			ListSelectionModel m = table.getSelectionModel();
			m.clearSelection();

			return;
		}

		tableModel.setRowSelect(p);
	}

	protected void savePerson() throws InvalidFieldException {
		P p = null;

		// when a contestant is added, delete becomes active
		btnDelete.setEnabled(true);
		try {
			p = getPerson();

			tableModel.updatePerson(p);
		} catch (InvalidFieldException e) {
			setExceptionError(e);
			throw e;
		} // end catch block

		// set that its now NOT a new contestant, and no fields have changed.
		isNewPerson = false;
		setFieldsChanged(false);
	}

	/**
	 * Should ALWAYS used when modifying fieldsChanged.
	 * 
	 * @param value
	 *            new value for fieldsChanged field.
	 */
	protected void setFieldsChanged(boolean value) {
		fieldsChanged = value;
		btnSave.setEnabled(value);
	}

	/**
	 * Returns whether fields have changed or not.
	 * 
	 * @return True if changed, false otherwise.
	 */
	protected boolean getFieldsChanged() {
		return fieldsChanged;
	}
	
	/**
	 * Puts the panel together based on abstract methods.
	 */
	protected void assembleAll() {
		setLayout(new BorderLayout(5, 5));
		buildTopPanel();
		buildTablePanel();
		buildBottomPanel();

		buildActions();
		
		setToolTips();

		update(GameData.getCurrentGame(), null);

		setFieldsChanged(false);

		if (tableModel.getRowCount() > 0) {
			tableModel.setRowSelect(0, false);
		} else {
			setPanelPerson(null, true);
		}
	}

	/**
	 * Sets the error infromation based on an exception!
	 * 
	 * @param e
	 *            Exception with the information necessary
	 */
	protected abstract void setExceptionError(InvalidFieldException e);

	protected abstract void buildActions();

	@Override
	public void mouseClicked(MouseEvent e) {
		return;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JComponent c = (JComponent) e.getComponent();
		MainFrame mf = MainFrame.getRunningFrame();

		String txt = c.getToolTipText();
		if (txt != null)
			mf.setStatusMsg(txt);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseEntered(e);
	}

	@Override
	public abstract void mousePressed(MouseEvent e);

	// unused
	@Override
	public void mouseReleased(MouseEvent e) {
		return;
	}

	/**
	 * Refreshes all values associated with GameData reference. <br>
	 * Currently: - Tribe combobox - Table - Sets buttons enabled/disabled as
	 * appropriate.
	 * 
	 * @see GameDataDependant.refreshGameFields
	 */
	@Override
	public abstract void update(Observable obj, Object arg);
}
