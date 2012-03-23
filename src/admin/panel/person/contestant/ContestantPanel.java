package admin.panel.person.contestant;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;

import admin.FileDrop;
import admin.MainFrame;
import admin.Utils;
import admin.panel.person.PersonPanel;
import data.Contestant;
import data.GameData;
import data.InvalidFieldException;

public class ContestantPanel extends PersonPanel<Contestant> implements MouseListener, Observer {

	private static final long serialVersionUID = 1L;

	private ContestantFieldsPanel paneEditFields;
	// container for top stuff
	private JButton btnCastOff;
	
	private JButton imgDisplay;
	private String imgPath;
	
	private JLabel labelName;
	// TODO: Refactor to something more obvious?
	private JLabel labelCastOff;
	private JLabel labelCastStatus;
	private JLabel labelTribe;

	private JTextField tfFirstName;
	private JTextField tfLastName;
	private JComboBox<String> cbTribe;

	private JTextField tfContID;
	private JLabel labelID;
	
	// vars:
	private boolean isNewContestant = false;
	private boolean fieldsChanged = false;
	
	// static constants:
	private static final String CAST_OFF_TEXT = "Cast Off";
	private static final String UNDO_CAST_TEXT = "Undo Cast Off";
	
	// tool tip texts:
	protected static final String TOOL_NAME = "First and Last name must be alphabetic";
	protected static final String TOOL_ID = "ID must be two characters long and " +
			"alpha-numeric";
	protected static final String TOOL_TRIBE = "Select a tribe";
	protected static final String TOOL_CASTOFF = "Click to cast off contestant.";
	protected static final String TOOL_SAVE = "Click to save contestant data";
	protected static final String TOOL_IMAGE = "Click to select image";
	protected static final String TOOL_ADDNEW = "Click to add new contestant";
	protected static final String TOOL_DELETE = "Click to remove currently selected " +
			"Contestant";
	
	/**
	 * THIS VARIABLE IS A REFERENCE MAINTAINED INTERNALLY. DO NOT ADJUST UNLESS
	 * YOU KNOW WHAT YOU ARE DOING.
	 */
	private Contestant loadedContestant;

	public ContestantPanel() {
		super(new Contestant());

		// ////////////////////////////
		// Top Panel:
		// ////////////////////////////
		// TODO: Better Test picture
		imgDisplay = new JButton();

		// Edit fields:
		labelName = new JLabel("Name:");
		tfFirstName = new JTextField(20);
		tfLastName = new JTextField(20);

		labelCastOff = new JLabel("Cast off:");
		// TODO: FIx the init of this.. :>
		labelCastStatus = new JLabel("-");

		labelTribe = new JLabel("Tribe:");
		cbTribe = new JComboBox<String>(GameData.getCurrentGame()
				.getTribeNames());

		labelID = new JLabel("ID:");
		tfContID = new JTextField(2);

		// holds all the fields
		personFields = new ContestantFieldsPanel(imgDisplay, labelName, 
				tfFirstName, tfLastName, labelID, tfContID, labelCastOff, 
				labelCastStatus, labelTribe, cbTribe);
		// add the mouse listener to all components.
		for (Component c : ((JPanel)personFields).getComponents()) {
			if (c instanceof JPanel) {
				for (Component d: ((JPanel)c).getComponents())
					d.addMouseListener(this);
			}
			c.addMouseListener(this);
		}

		// buttons:
		btnCastOff = new JButton("Cast Off");
		/* check to stop casting off before start */
		if (!GameData.getCurrentGame().isSeasonStarted()) {
			btnCastOff.setEnabled(false);
		}
		
		//////////////////////////////
		// Mid (table!)
		//////////////////////////////
		// handled by super call

		// ////////////////////////////
		// Bottom
		//////////////////////////////
		
		// build the two panes
		// setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		assembleAll();
	}

	/**
	 * Builds the top panel including all the editable information
	 */
	@Override
	protected void buildTopPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(10, 10));

		// this does not need to be referenced else where, only for layout
		JPanel paneButtons = new JPanel();
		GridLayout bl = new GridLayout(2, 1);
		paneButtons.setLayout(bl);

		paneButtons.add(btnCastOff);
		paneButtons.add(btnSave);
		
		// add all components on top:
		panel.add((JPanel)personFields, BorderLayout.CENTER);
		panel.add(paneButtons, BorderLayout.LINE_END);

		add(panel, BorderLayout.PAGE_START);

		// add the mouse listener to all components.
		for (Component c : panel.getComponents()) {
			c.addMouseListener(this);
		}

		for (Component c : paneButtons.getComponents())
			c.addMouseListener(this);
	}

	/**
	 * Builds the panel containing the JTable
	 */
	@Override
	protected void buildTablePanel() {
		JPanel panel = new JPanel();

		// settings:
		header.setReorderingAllowed(false); // no moving.
		table.setColumnSelectionAllowed(true);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//header.addMouseListener(tableModel.new SortColumnAdapter());

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
	@Override
	protected void buildBottomPanel() {
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
	
	/**
	 * Sets the tool tips for all the components.
	 */
	@Override
	protected void setToolTips() {
		labelName.setToolTipText(ContestantPanel.TOOL_NAME);
		tfFirstName.setToolTipText(ContestantPanel.TOOL_NAME);
		tfLastName.setToolTipText(ContestantPanel.TOOL_NAME);
		
		labelID.setToolTipText(ContestantPanel.TOOL_ID);
		tfContID.setToolTipText(ContestantPanel.TOOL_ID);
		
		labelTribe.setToolTipText(ContestantPanel.TOOL_TRIBE);
		cbTribe.setToolTipText(ContestantPanel.TOOL_TRIBE);
		
		btnCastOff.setToolTipText(TOOL_CASTOFF);
		btnSave.setToolTipText(TOOL_SAVE);
		imgDisplay.setToolTipText(TOOL_IMAGE);
		
		btnAddNew.setToolTipText(TOOL_ADDNEW);
		btnDelete.setToolTipText(TOOL_DELETE);
	}

	/**
	 * Wrapper that allows the super class to do most of the work. Small 
	 * adjustments for contestants vs. players.
	 * 
	 * @param c
	 * @param newContestant
	 */
	private void setPanelContestant(Contestant c, boolean newContestant) {
		super.setPanelPerson(c, newContestant);

		btnCastOff.setEnabled(GameData.getCurrentGame().isSeasonStarted());
			
		if (newContestant || c == null) {
			// we don't want any rows selected
			ListSelectionModel m = table.getSelectionModel();
			m.clearSelection();

			return;
		}
		
		tableModel.setRowSelect(c);
	}

	private void saveContestant() throws InvalidFieldException {
		Contestant con = null;
		
		// when a contestant is added, delete becomes active
		btnDelete.setEnabled(true);
		try {
			con = getPerson();

			tableModel.updatePerson(con);
		} catch (InvalidFieldException e) {
			setExceptionError(e);
			throw e;
		} // end catch block

		// set that its now NOT a new contestant, and no fields have changed.
		isNewContestant = false;
		setFieldsChanged(false);
	}

	/**
	 * Sets the error infromation based on an exception!
	 * 
	 * @param e
	 *            Exception with the information necessary
	 */
	@Override
	protected void setExceptionError(InvalidFieldException e) {
		if (e.isHandled())
			return;

		MainFrame mf = MainFrame.getRunningFrame();

		switch (e.getField()) {
		case CONT_ID:
			mf.setStatusErrorMsg("Invalid ID (must be 2 alphanumeric "
					+ "characters long)", tfContID);
			break;
		case CONT_ID_DUP:
			mf.setStatusErrorMsg("Invalid ID (in use)", tfContID);
			break;
		case CONT_FIRST:
			mf.setStatusErrorMsg("Invalid First Name (must be alphabetic"
					+ ", 1-20 characters)", tfFirstName);
			break;
		case CONT_LAST:
			mf.setStatusErrorMsg("Invalid Last Name (must be alphabetic"
					+ ", 1-20 characters)", tfLastName);
			break;
		case CONT_TRIBE: // how you did this is beyond me..
			mf.setStatusErrorMsg("Invalid Tribe selection", cbTribe);
			break;
		default:
			mf.setStatusErrorMsg("Unknown problem with fields");
		}

		e.handle();
	}

	/**
	 * Used to store the listener event so we can remove it later.
	 */
	private ActionListener imgButtonListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			int ret = fc.showOpenDialog(null);
			if (ret == JFileChooser.APPROVE_OPTION) {
				// File f = fc.getSelectedFile();
				updateContPicture(fc.getSelectedFile().getAbsolutePath());
			}
		}
	};
	
	/**
	 * 
	 */
	@Override
	protected void buildActions() {
		btnAddNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GameData g = GameData.getCurrentGame();
				
				// check if too many contestants
				if (g.getAllContestants().size() == g.getInitialContestants()) {
					JOptionPane.showMessageDialog(
									null,
									"There are already the maximum "
											+ "number of contestants in the " +
											"game.  To add another you must " +
											"delete an existing contestant.");
					return;
				}
				
				if (getFieldsChanged()) {
					try {
						saveContestant();
					} catch (InvalidFieldException ex) {
						setExceptionError(ex);
						return;
					}
				}

				isNewContestant = true;
				setPanelContestant(null, true);
			}
		});
		
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {		
				if (!getFieldsChanged()) 
					return;
				
				try { 
					saveContestant(); 
					
					Contestant c = getPerson(); // this wont cause exception
					
					tableModel.setRowSelect(c);
				} catch (InvalidFieldException ex) {
					setExceptionError(ex);
					return;
				}				
			}

		});

		btnCastOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = ((JButton) e.getSource()).getText();

				Contestant c = null;
				try {
					c = getPerson();
				} catch (InvalidFieldException ie) {
					// FIXME: Intelligently respond on the exception.
					// In theory, it shouldn't happen, but we shouldn't cast
					// someone who isn't fully in the game.. :/
					return;
				}

				if (s.equals("Cast Off")) {
					// check if someone is already cast off
					if (GameData.getCurrentGame().doesElimExist() == true) {
						JOptionPane.showMessageDialog(
										null,
										"You can't cast off more than one " +
										"person per week. If you accidently" +
										" casted the wrong person, you can " +
										"undo the incorrect cast first, and " +
										"then cast off the correct one.");
						return;
					}
					
					// can't cast off someone already off.
					if (c.isCastOff()) {
						JOptionPane.showMessageDialog(null,
								"This person is already out of the game.");
						return;
					}

					c.toCastOff();
					labelCastStatus.setText("Week " + c.getCastDate());
				} else {
					c.undoCast();
					labelCastStatus.setText("Active");
					btnCastOff.setText("Cast Off");
				}

				update(GameData.getCurrentGame(), null);
			}
		});
		
		btnDelete.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// ask the admin for input on whether to delete or not
				int response = JOptionPane.showConfirmDialog(null,
						"Would you like to delete currently selected "
								+ "Contestant?", "Delete Contestant?",
						JOptionPane.YES_NO_OPTION);

				if (response == JOptionPane.YES_OPTION) {
					// user said they want to delete contestant
					
					Contestant c = null;
					try {
						c = getPerson();
					} catch (InvalidFieldException ex) {
						if (ex.getField() == InvalidFieldException.Field.CONT_ID) {
							MainFrame.getRunningFrame().setStatusErrorMsg(
									"Can not delete Contestant"
											+ " (invalid ID)", tfContID);
							return;
						}
						System.out.println("Delete contestant, exception");
					}
					
					if (c == null) {
						System.out.println("We goofed really badly.");
						throw new NullPointerException("Could not get " +
								"contestant from game data.");
					}
					
					int row = tableModel.getRowByPerson(c);
					boolean selRow = (table.getRowCount() > 1);

					// remove the contestant from the game
					tableModel.removePerson(c);
					
					if (selRow && (c != null)) {
						row %= table.getRowCount();
						tableModel.setRowSelect(row, false);
					} else {
						btnAddNew.doClick();
					}
				}
			}
		});
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent le) {
				 int row = table.getSelectedRow();
				 if (row < 0) return;
				// oldRow = row;
				 
				 row = table.getRowSorter().convertRowIndexToModel(row);
				 Contestant c = tableModel.getByRow(row);
			     
				 if (c != null){
					 if (getFieldsChanged())
						 btnSave.doClick();
					 
					 setPanelContestant(c, false); 
				 }
			}
		});
		
		imgDisplay.addActionListener(imgButtonListener);
		
		new FileDrop( this, new FileDrop.Listener(){   
			public void filesDropped( java.io.File[] files ){   
				updateContPicture(files[0].getAbsolutePath());
			}
		});

		FocusAdapter fa = new FocusAdapter() {
			JTextField src;

			public void focusGained(FocusEvent evt) {
				src = (JTextField) evt.getComponent();

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						src.selectAll();
					}
				});
			}
		};

		List<JTextField> tfArr = Arrays.asList(tfContID, tfFirstName,
				tfLastName);
		for (JTextField tf : tfArr) {
			tf.addFocusListener(fa);
		}
	}

	/**
	 * Convienience wrapper. 
	 * @param absolutePath
	 */
	protected void updateContPicture(String absolutePath) {
		((ContestantFieldsPanel)personFields).updateContPicture(absolutePath);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		return;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JComponent c = (JComponent)e.getComponent();
		MainFrame mf = MainFrame.getRunningFrame();
		
		String txt = c.getToolTipText();
		if (txt != null)
			mf.setStatusMsg(txt);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseEntered(e);
	}

	// unused
	@Override
	public void mousePressed(MouseEvent e) {
		Component c = e.getComponent();
		if (c == tfContID || c == tfFirstName || c == tfLastName
				|| c == cbTribe || c == table || c == btnCastOff) {
			setFieldsChanged(true);
		}
	}

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
	public void update(Observable obj, Object arg) {
		GameData g = (GameData) obj;

		// tribe combobox
		String[] newTribes = g.getTribeNames();
		cbTribe.removeAllItems();
		for (String s : newTribes) {
			cbTribe.addItem(s);
		}

		// updates the data in the table
		tableModel.fireTableDataChanged();

		// depends on season started:
		boolean sStart = g.isSeasonStarted();
		
		btnAddNew.setEnabled(!sStart);
		btnCastOff.setEnabled(sStart);
		btnDelete.setEnabled(!sStart);
		tfLastName.setEnabled(!sStart);
		tfFirstName.setEnabled(!sStart);
		tfContID.setEnabled(!sStart);

		List<ActionListener> acts = Arrays.asList(imgDisplay
				.getActionListeners());
		boolean actPresent = acts.contains(imgButtonListener);
		if (actPresent && sStart) {
			imgDisplay.removeActionListener(imgButtonListener);
		} else if (!actPresent && !sStart) {
			imgDisplay.addActionListener(imgButtonListener);
		}
	}
}
