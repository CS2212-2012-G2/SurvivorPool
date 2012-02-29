package admin;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.Contestant;

public class EditPlayerFieldsPanel extends JPanel {
	private ImageIcon imgDisplay;
	
	
	private JLabel labelName;
	// TODO: Refactor to something more obvious?
	private JLabel labelCastOff;
	private JLabel labelCastStatus;
	private JLabel labelTribe;
	
	private JTextField tfFirstName;
	private JTextField tfLastName;
	// XXX: Can someone explain this?
	private JComboBox<String> cbTribe;
	
	private JPanel paneField;
	private GridBagLayout gbFields;
	private GridBagConstraints gbFieldsConst;
	
	// TODO: Reorder the parameters
	public EditPlayerFieldsPanel(String[] tribes) {
		/*gridBag = new GridBagLayout();
		gbConst = new GridBagConstraints();
		setLayout(gridBag);*/
		
		paneField = new JPanel();
		paneField.setSize(500, 500);
		gbFields = new GridBagLayout();
		gbFieldsConst = new GridBagConstraints();
		paneField.setLayout(gbFields);
		
		/// Edit fields:
		labelName = new JLabel("Name:");
		tfFirstName = new JTextField();
		tfFirstName.setSize(200, 50);
		tfLastName = new JTextField();
		tfLastName.setSize(200, 50);
		
		labelCastOff = new JLabel("Date Cast of:");
		// TODO: FIx the init of this.. :>
		labelCastStatus = new JLabel("ASDFasdfasdfasdfasdf");
		
		labelTribe = new JLabel("Tribe:");
		cbTribe = new JComboBox<String>(tribes);
		
		setupGridBag(gbFields, gbFieldsConst);
		
		add(paneField);
	}
	
	private void setupGridBag(GridBagLayout gbl, GridBagConstraints gbc) {
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weighty = 0.5;
		gbc.weightx = 0.5;
		
		// first row: [Name Label] [First Name] [Last Name]
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		paneField.add(labelName, gbc);
		
		gbc.gridx = GridBagConstraints.RELATIVE;
		gbc.anchor = GridBagConstraints.CENTER;
		paneField.add(tfFirstName, gbc);
		
		gbc.anchor = GridBagConstraints.LINE_END;
		paneField.add(tfLastName, gbc);
		
		// second row: [Date Label] [Active Label]
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		paneField.add(labelCastOff, gbc);
		
		gbc.gridx = 1;
		gbc.gridwidth = 2; // finish the row
		gbc.anchor = GridBagConstraints.LINE_END;
		paneField.add(labelCastStatus, gbc);
		gbc.gridwidth = 1;
		
		// last row: [Combo Label] [Combo Box]
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.LINE_START;
		paneField.add(labelTribe, gbc);
		
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		paneField.add(cbTribe, gbc);
	}
	
	// TODO: Implement
	/**
	 * Sets the editing information to the information stored in the contestant
	 * @param c 	The contestant to edit
	 */
	public void setEditPane(Contestant c) {
		return;
	}
	
	public static void main(String args[]) {
		JFrame f = new JFrame();
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		EditPlayerFieldsPanel main = new EditPlayerFieldsPanel(new String[] {"SEXY", "MORE SEXY"});
		f.add(main);
		
		f.pack();
		f.setSize(f.getPreferredSize());
		
		f.setVisible(true);
	}
}
