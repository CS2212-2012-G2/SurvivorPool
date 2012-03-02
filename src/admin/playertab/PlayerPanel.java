package admin.playertab;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import admin.Main;


public class PlayerPanel extends JPanel {

	private JLabel imgDisplay;

	private EditPlayerFieldsPanel paneEditFields;
	// container for top stuff
	private JPanel paneTop;
	
	private JButton bCastOff;
	private JButton bSavePlayer;
	
	private JLabel labelName;
	// TODO: Refactor to something more obvious?
	private JLabel labelCastOff;
	private JLabel labelCastStatus;
	private JLabel labelTribe;
	
	private JTextField tfFirstName;
	private JTextField tfLastName;
	private JComboBox<String> cbTribe;
	
	private PlayerTablePanel paneTable;
	
	
	public PlayerPanel(){
		paneTop = new JPanel();
		paneTop.setLayout(new BoxLayout(paneTop, BoxLayout.X_AXIS));
		
		// TODO: Resize?
		String path = "res/test/defaultpic.png"; //apparently images have to be .png and alphanumeric
		ImageIcon imgD = new ImageIcon(path);
		imgDisplay = new JLabel("a");
		imgDisplay.setIcon(imgD);
		
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
		cbTribe = new JComboBox<String>(Main.getGameData().getTribeNames());
		
		// holds all the fields
		paneEditFields = new EditPlayerFieldsPanel(labelName, labelCastOff, 
				labelCastStatus, labelTribe, tfFirstName, tfLastName, 
				cbTribe);
		
		// buttons:
		bCastOff = new JButton("Cast Off");
		bSavePlayer = new JButton("Save");
		
		bSavePlayer.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String pattern = "[A-Za-z]{1,20}";
				if(!Main.checkString(tfFirstName.getText(), pattern)||
						!Main.checkString(tfLastName.getText(), pattern)){
					JOptionPane.showMessageDialog(null,"Invalid name!(dialog box not permanent)");
				}
			}
			
		});
		
		// this does not need to be referenced else where, only for layout
		JPanel paneButtons = new JPanel();
		GridLayout bl = new GridLayout(2, 1);
		paneButtons.setLayout(bl);
		
		paneButtons.add(bCastOff);
		paneButtons.add(bSavePlayer);
		
		// add all components on top:
		paneTop.add(imgDisplay);
		paneTop.add(paneEditFields);
		paneTop.add(paneButtons);
		
		// bottom panel
		paneTable = new PlayerTablePanel();
		
		
		add(paneTop);
		add(paneTable);
		
	}
}
