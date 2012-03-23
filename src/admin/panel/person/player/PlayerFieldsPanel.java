package admin.panel.person.player;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import data.Contestant;

public class PlayerFieldsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// Store external references:
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

	// store internal
	private GridBagLayout gbFields;
	private GridBagConstraints gbFieldsConst;

	public PlayerFieldsPanel(JLabel _labelName, JTextField _tfFirstName,
			JTextField _tfLastName, JLabel _labelID, JTextField _tfID,
			JButton _btnGenID, JLabel _labelWeekly,
			JComboBox<Contestant> _cbWeeklyPick, JLabel _labelUltimate,
			JComboBox<Contestant> _cbUltPick) {
		super();
		
		/// name
		labelName = _labelName;
		tfFirstName = _tfFirstName;
		tfLastName = _tfLastName;
		
		labelName.setToolTipText(PlayerPanel.TOOL_NAME);
		tfFirstName.setToolTipText(PlayerPanel.TOOL_NAME);
		tfLastName.setToolTipText(PlayerPanel.TOOL_NAME);
		/// end name
		
		/// ID
		labelID = _labelID;
		tfID = _tfID;
		btnGenID = _btnGenID;
		
		labelID.setToolTipText(PlayerPanel.TOOL_IDTXT);
		tfID.setToolTipText(PlayerPanel.TOOL_IDTXT);
		btnGenID.setToolTipText(PlayerPanel.TOOL_IDBTN);
		/// end ID
		
		/// contestant picks:
		labelWeekly = _labelWeekly;
		cbWeeklyPick = _cbWeeklyPick;
		
		labelWeekly.setToolTipText(PlayerPanel.TOOL_WEEKLY);
		cbWeeklyPick.setToolTipText(PlayerPanel.TOOL_WEEKLY);
		
		labelUltimate = _labelUltimate;
		cbUltPick = _cbUltPick;
		
		labelUltimate.setToolTipText(PlayerPanel.TOOL_ULT);
		cbUltPick.setToolTipText(PlayerPanel.TOOL_ULT);
		/// end contestant picks
		
		gbFields = new GridBagLayout();
		gbFieldsConst = new GridBagConstraints();

		setLayout(gbFields);
		setupGridBag(gbFields, gbFieldsConst);
	}

	private void setupGridBag(GridBagLayout gbl, GridBagConstraints gbc) {
		// gbc.insets = new Insets(5, 5, 5, 5);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weighty = 0.5;
		gbc.weightx = 0.5;
		gbc.gridy = 0;

		// row: [Name Label] [First Name] [Last Name]
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.weightx = 0.25;
		gbc.insets = new Insets(0, 0, 5, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		add(labelName, gbc);

		gbc.weightx = 0.5;
		gbc.gridx = GridBagConstraints.RELATIVE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 5, 5);
		add(tfFirstName, gbc);

		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.insets = new Insets(0, 0, 5, 0);
		add(tfLastName, gbc);

		// row: [ID Label] [ID TextEdit] [Gen Button]
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.weightx = 0.25;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 5, 10);
		add(labelID, gbc);

		gbc.weightx = 0.5;
		gbc.gridx = GridBagConstraints.RELATIVE;
		gbc.insets = new Insets(0, 0, 5, 5);
		add(tfID, gbc);

		gbc.weightx = 0.25;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(0, 0, 5, 0);
		add(btnGenID, gbc);

		// row: [Combo Label] [Combo Box]
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 5, 10);
		add(labelWeekly, gbc);

		gbc.gridx = GridBagConstraints.RELATIVE;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(0, 0, 5, 0);
		add(cbWeeklyPick, gbc);

		// row: [Combo Label] [Combo Box]
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 5, 10);
		add(labelUltimate, gbc);

		gbc.gridx = GridBagConstraints.RELATIVE;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(0, 0, 5, 0);
		add(cbUltPick, gbc);
	}

	// TODO: Move from PlayerPanel into here?
	/**
	 * Sets the editing information to the information stored in the contestant
	 * 
	 * @param c
	 *            The contestant to edit
	 */
	/*
	 * public void setEditPane(User u) { return; }
	 */
}
