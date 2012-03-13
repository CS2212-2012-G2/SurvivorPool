package admin.playertab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import sun.awt.VerticalBagLayout;

import admin.AdminUtils;
import admin.MainFrame;
import admin.StatusPanel;
import admin.data.GameData;

import data.Contestant;
import data.User;

// TODO: REWRITE AND REBUILD. 

public class PlayerPanel extends JPanel implements ChangeListener,
		MouseListener {

	// input fields:
	private JLabel labelName;
	private JTextField tfFirstName;
	private JTextField tfLastName;

	private JLabel labelID;
	private JTextField tfID;
	private JButton btnGenID;

	private JLabel labelWeekly;
	private JComboBox<Contestant> cbWeeklyPick;
	private JLabel labelUltimate;
	private JComboBox<Contestant> cbUltPick;

	// etc
	private JLabel labelPts;
	private JButton btnSave;

	// / Table fields
	private PlayerTableModel tableModel;
	private JTable table;
	private JTableHeader header;

	// Bottom buttons
	private JButton btnAddNew;
	private JButton btnDelete;
	private PlayerFieldsPanel playerFields;

	public PlayerPanel() {
		super();

		setLayout(new BorderLayout(5, 5));

		// ////////////////////////////
		// Top Panel:
		// ////////////////////////////
		labelName = new JLabel("Name:");
		tfFirstName = new JTextField();
		tfLastName = new JTextField();

		labelID = new JLabel("User ID:");
		tfID = new JTextField();
		btnGenID = new JButton("Generate ID");

		labelWeekly = new JLabel("Weekly Pick:");
		cbWeeklyPick = new JComboBox<Contestant>();

		labelUltimate = new JLabel("Ultimate Pick:");
		cbUltPick = new JComboBox<Contestant>();

		playerFields = new PlayerFieldsPanel(labelName, tfFirstName,
				tfLastName, labelID, tfID, btnGenID, labelWeekly, cbWeeklyPick,
				labelUltimate, cbUltPick);
		// add the mouse listener to all components.
		for (Component c : playerFields.getComponents()) {
			c.addMouseListener(this);
		}

		// right side!
		labelPts = new JLabel("Points: 0");
		btnSave = new JButton("Save");

		// ////////////////////////////
		// Mid
		// ////////////////////////////
		List<User> users = 
				AdminUtils.uncastListToCast(
						GameData.getCurrentGame().getAllUsers(), 
						new User()
					);
		tableModel = new PlayerTableModel(users);
		table = new JTable(tableModel);
		header = table.getTableHeader();

		// ////////////////////////////
		// Bottom
		// ////////////////////////////
		btnAddNew = new JButton("Add");
		btnDelete = new JButton("Delete");

		// build the two panes
		// setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setLayout(new BorderLayout(5, 5));
		buildTopPanel();
		buildTablePanel();
		buildBottomPanel();
	}

	/**
	 * Builds the top panel including all the editable information
	 */
	private void buildTopPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(10, 10));

		// this does not need to be referenced else where, only for layout
		JPanel rightPane = new JPanel();
		BoxLayout b = new BoxLayout(rightPane, BoxLayout.Y_AXIS);
		rightPane.setLayout(b);
		rightPane.add(labelPts);
		rightPane.add(Box.createVerticalGlue());
		rightPane.add(btnSave);

		// add all components on top:
		panel.add(playerFields, BorderLayout.CENTER);
		panel.add(rightPane, BorderLayout.LINE_END);

		add(panel, BorderLayout.PAGE_START);

		// add the mouse listener to all components.
		for (Component c : panel.getComponents()) {
			c.addMouseListener(this);
		}

		for (Component c : rightPane.getComponents())
			c.addMouseListener(this);
	}

	/**
	 * Builds the panel containing the JTable
	 */
	private void buildTablePanel() {
		JPanel panel = new JPanel();

		// settings:
		header.setReorderingAllowed(false); // no moving.
		table.setColumnSelectionAllowed(true);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		header.addMouseListener(tableModel.new SortColumnAdapter());

		TableCellRenderer renderer = new TableCellRenderer() {

			JLabel label = new JLabel();

			@Override
			public JComponent getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {

				Color c = null;
				if (table.isRowSelected(row)) {
					c = AdminUtils.getThemeTableHighlight();
				} else {
					c = UIManager.getColor("Table.background");
				}
				label.setBackground(c);

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

	private void buildBottomPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		panel.add(btnAddNew);
		panel.add(btnDelete);

		add(panel, BorderLayout.PAGE_END);
		// add the mouse listener to all components.
		for (Component c : panel.getComponents()) {
			c.addMouseListener(this);
		}
	}

	public void seasonStarted(){
		tfID.setEnabled(false);
		tfFirstName.setEnabled(false);
		tfLastName.setEnabled(false);
		btnGenID.setEnabled(false);
		btnAddNew.setEnabled(false);
		btnDelete.setEnabled(false);
	}
	
	/**
	 * Currently used to check if a tab is changed, and if its changed to the
	 * PlayerPanel, it will modify ComboBoxes.
	 */
	@Override
	public void stateChanged(ChangeEvent e) {

		Object obj = e.getSource();
		if (!(obj instanceof JTabbedPane))
			return;

		JTabbedPane tab = (JTabbedPane) obj;

		if (tab.getSelectedIndex() != 2)
			return;

		GameData g = (GameData) GameData.getCurrentGame();

		if (g != null) {
			List<Contestant> cons = AdminUtils.uncastListToCast(
					g.getActiveContestants(), 
					new Contestant()
				);
			//cons = AdminUtils.noNullList(cons);

			cbWeeklyPick.removeAllItems();
			cbUltPick.removeAllItems();

			for (Contestant c : cons) {
				cbWeeklyPick.addItem(c);
				cbUltPick.addItem(c);
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Component c = e.getComponent();
		MainFrame mf = MainFrame.getRunningFrame();
		if (c == labelName || c == tfFirstName || c == tfLastName) {
			mf.setStatusMsg("First and Last name must be alphabetic");
		} else if (c == labelID || c == tfID) {
			mf.setStatusMsg("ID must be 2-7 chars long and may end with numbers");
		} else if (c == btnGenID) {
			mf.setStatusMsg("Click to auto-generate ID from first and last name");
		} else if (c == labelWeekly || c == cbWeeklyPick) {
			mf.setStatusMsg("Select Weekly pick");
		} else if (c == labelUltimate || c == cbUltPick) {
			mf.setStatusMsg("Select Ultimate Winner");
		} else if (c == btnAddNew) {
			mf.setStatusMsg("Add a new User to system");
		} else if (c == btnDelete) {
			mf.setStatusMsg("Remove selected User from system");
		} else if (c == table || c == header) {
			mf.setStatusMsg("Click Heading to sort by column");
		}
		// System.out.println("MouseEntered: " + c.toString());

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// Currently unused stubs.
		return;
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// Currently unused stubs.
		return;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// Currently unused stubs.
		return;
	}
}
