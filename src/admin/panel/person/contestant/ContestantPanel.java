package admin.panel.person.contestant;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.EnumSet;
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
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

import admin.FileDrop;
import admin.MainFrame;
import admin.Utils;
import admin.panel.person.PersonPanel;
import data.Contestant;
import data.GameData;
import data.GameData.UpdateTag;
import data.InvalidFieldException;

public class ContestantPanel extends PersonPanel<Contestant> implements MouseListener, Observer {

	private static final long serialVersionUID = 1L;

	// container for top stuff
	private JButton btnCastOff;
	
	private JButton imgDisplay;
	
	private JLabel labelName;
	// TODO: Refactor to something more obvious?
	private JLabel labelCastOff;
	private JTextField tfCastDate;
	private JLabel labelTribe;

	private JTextField tfFirstName;
	private JTextField tfLastName;
	private JComboBox<String> cbTribe;

	private JTextField tfContID;
	private JLabel labelID;
	
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
	protected static final String TOOL_WINNER = "Click to choose winner";
	
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
		tfCastDate = new JTextField("-");
		btnCastOff = new JButton("Cast Off");

		labelTribe = new JLabel("Tribe:");
		cbTribe = new JComboBox<String>(GameData.getCurrentGame()
				.getTribeNames());

		labelID = new JLabel("ID:");
		tfContID = new JTextField(2);

		// holds all the fields
		personFields = new ContestantFieldsPanel(imgDisplay, labelName, 
				tfFirstName, tfLastName, labelID, tfContID, labelCastOff, 
				tfCastDate, btnCastOff, labelTribe, cbTribe);
		// add the mouse listener to all components.
		for (Component c : ((JPanel)personFields).getComponents()) {
			if (c instanceof JPanel) {
				for (Component d: ((JPanel)c).getComponents())
					d.addMouseListener(this);
			}
			c.addMouseListener(this);
		}

		// check to stop casting off before start
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
		
		if (GameData.getCurrentGame().isFinalWeek()){
			btnCastOff.setToolTipText(TOOL_WINNER);
		} else {
			btnCastOff.setToolTipText(TOOL_CASTOFF);
		}
		
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
	@Override
	protected void setPanelPerson(Contestant c, boolean newContestant) {
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
		super.buildActions();
		
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
						savePerson();
					} catch (InvalidFieldException ex) {
						setExceptionError(ex);
						return;
					}
				}

				setPanelPerson(null, true);
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
				
				GameData g = GameData.getCurrentGame();

				if (s.equals("Cast Off") || s.equals("Select Winner")) {
					// check if someone is already cast off
					if (g.doesElimExist() == true) {
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
					
					if (g.isFinalWeek()) {
						tfCastDate.setText("Winner");
					} else {
						g.castOff(c);
						tfCastDate.setText("" + c.getCastDate());
					}
				} else {
					g.undoCastOff(c);
					tfCastDate.setText("Active");
					if (g.isFinalWeek())
						btnCastOff.setText("Select Winner");
					else
						btnCastOff.setText("Cast Off");
				}

				update(GameData.getCurrentGame(), UpdateTag.CONTESTANT_CAST_OFF);
			}
		});
		
		imgDisplay.addActionListener(imgButtonListener);
		
		new FileDrop( this, new FileDrop.Listener(){   
			public void filesDropped( java.io.File[] files ){   
				updateContPicture(files[0].getAbsolutePath());
			}
		});

		List<JTextField> tfArr = Arrays.asList(tfContID, tfFirstName,
				tfLastName);
		for (JTextField tf : tfArr) {
			tf.addFocusListener(editAdapt);
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
	public void mousePressed(MouseEvent e) {
		Component c = e.getComponent();
		if (c == tfContID || c == tfFirstName || c == tfLastName
				|| c == cbTribe || c == table || c == btnCastOff) {
			setFieldsChanged(true);
		}
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

		@SuppressWarnings("unchecked")
		EnumSet<UpdateTag> update = (EnumSet<GameData.UpdateTag>)arg;
		
		if (update == null || update.contains(UpdateTag.SET_TRIBE_NAMES) ||
				update.contains(UpdateTag.CONTESTANT_CAST_OFF)) {
			// tribe combobox
			String[] newTribes = g.getTribeNames();
			cbTribe.removeAllItems();
			for (String s : newTribes) {
				cbTribe.addItem(s);
			}

			// updates the data in the table
			tableModel.fireTableDataChanged();
		}
		
		//if (update == null) return;
		
		if (update == null || update.contains(UpdateTag.START_SEASON) ||  
				update.contains(UpdateTag.ADVANCE_WEEK) || 
				update.contains(UpdateTag.END_GAME)) {
			// depends on season started:
			boolean sStart = g.isSeasonStarted();
			
			// change text to "select winner" once its the final week
			if (g.isFinalWeek())
				btnCastOff.setText("Select Winner");
			
			btnAddNew.setEnabled(!sStart);
			
			if (g.isSeasonEnded()) {
				btnCastOff.setEnabled(false);
				btnSave.setEnabled(false);
			} else {
				btnCastOff.setEnabled(sStart);
			}
			
			btnDelete.setEnabled(!sStart);
			tfLastName.setEnabled(!sStart);
			tfFirstName.setEnabled(!sStart);
			tfContID.setEnabled(!sStart);
			
			tfCastDate.setEditable(false);
			
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
}
