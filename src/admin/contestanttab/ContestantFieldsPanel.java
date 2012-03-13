package admin.contestanttab;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import admin.MainFrame;
import admin.StatusPanel;

import data.Contestant;

public class ContestantFieldsPanel extends JPanel {
	
	// Store external references:
	private JLabel labelName;
	// TODO: Refactor to something more obvious?
	private JLabel labelCastOff;
	private JLabel labelCastStatus;
	private JLabel labelTribe;
	
	private JLabel labelID;
	private JTextField tfContID;
	
	private JTextField tfFirstName;
	private JTextField tfLastName;
	private JComboBox<String> cbTribe;
	
	// store internal
	private GridBagLayout gbFields;
	private GridBagConstraints gbFieldsConst;
	
	
	public ContestantFieldsPanel(JLabel _labelName, JTextField _tfFirstName, 
			JTextField _tfLastName, JLabel _labelID, 
			JTextField _tfContID, JLabel _labelCastOff, 
			JLabel _labelCastStatus, JLabel _labelTribe, JComboBox<String> _cbTribe) {
		super();
		
		// passed in
		labelName = _labelName;
		labelCastOff = _labelCastOff;
		labelCastStatus = _labelCastStatus;
		labelTribe = _labelTribe;
		
		tfFirstName = _tfFirstName;
		tfLastName = _tfLastName;
		cbTribe = _cbTribe;
		
		labelID = _labelID;
		tfContID = _tfContID;
		
		gbFields = new GridBagLayout();
		gbFieldsConst = new GridBagConstraints();
		
		setLayout(gbFields);
		setupGridBag(gbFields, gbFieldsConst);
	}
	
	private void setupGridBag(GridBagLayout gbl, GridBagConstraints gbc) {
		//gbc.insets = new Insets(5, 5, 5, 5);
		
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
		
		// row: [ID Label] [ID TextEdit]
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.weightx = 0.25;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 5, 10);
		add(labelID, gbc);
		
		gbc.weightx = 0.5;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridx = GridBagConstraints.RELATIVE;
		gbc.insets = new Insets(0, 0, 5, 0);
		add(tfContID, gbc);
		
		
		// row: [Date Label] [Active Label]
		gbc.gridx = 0;
		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 5, 10);
		add(labelCastOff, gbc);
		
		gbc.gridx = GridBagConstraints.RELATIVE;
		gbc.gridwidth = GridBagConstraints.REMAINDER; // finish the row
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.insets = new Insets(0,0,5,0);
		add(labelCastStatus, gbc);
		
		
		// row: [Combo Label] [Combo Box]
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 5, 10);
		add(labelTribe, gbc);
		
		gbc.gridx = GridBagConstraints.RELATIVE;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(0, 0, 5, 0);
		add(cbTribe, gbc);
	}
	
	// TODO: Implement (Move from ContestantPanel?)
	/**
	 * Sets the editing information to the information stored in the contestant
	 * @param c 	The contestant to edit
	 */
	public void setEditPane(Contestant c) {
		return;
	}
}
