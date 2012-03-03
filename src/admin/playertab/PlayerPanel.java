package admin.playertab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import sun.awt.HorizBagLayout;
import sun.awt.VerticalBagLayout;

import data.Contestant;
import data.GameData;


import admin.Main;
import admin.playertab.PlayerTableModel.SortColumnAdapter;


public class PlayerPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton imgDisplay;
	private String imgPath;

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
	
	private JTextField tfContID;
	private JLabel labelID;
	
	private static Contestant INACTIVE_CONT = new Contestant();
	private Contestant activeCon = INACTIVE_CONT;

	private JTable table;
	protected PlayerTableModel tableModel; 
	private JTableHeader header;
	
	private static String DEFAULT_PICTURE = "res/test/defaultpic.png";
	
	
	public PlayerPanel(){
		
		//////////////////////////////
		// Top Panel:
		//////////////////////////////
		// TODO: Better Test picture
		imgDisplay = new JButton();
		updateContPicture(DEFAULT_PICTURE); //apparently images have to be .png and alphanumeric
		
		
		// Edit fields:
		labelName = new JLabel("Name:");
		tfFirstName = new JTextField();
		tfLastName = new JTextField();
		
		labelCastOff = new JLabel("Cast off:");
		// TODO: FIx the init of this.. :>
		labelCastStatus = new JLabel("-");
		
		labelTribe = new JLabel("Tribe:");
		cbTribe = new JComboBox<String>(GameData.getCurrentGame().getTribeNames());
		
		labelID = new JLabel("ID:");
		tfContID = new JTextField();
		
		// holds all the fields
		paneEditFields = new EditPlayerFieldsPanel(labelName, tfFirstName, 
					tfLastName, labelID, tfContID, labelCastOff, 
					labelCastStatus, labelTribe, cbTribe);
		
		// buttons:
		bCastOff = new JButton("Cast Off");
		bSavePlayer = new JButton("Save");
		
		//////////////////////////////
		// Bottom Panel
		//////////////////////////////
		tableModel = new PlayerTableModel(GameData.getCurrentGame().getAllContestants());
		table = new JTable(tableModel);
		header = table.getTableHeader();
		
		// build the two panes
		setLayout(new VerticalBagLayout(5));
		add(buildTopPanel());
		add(buildTablePanel());
		
		buildActions();
	}
	
	/**
	 * 
	 * @return
	 */
	private JPanel buildTablePanel() {
		JPanel pane = new JPanel();
		
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
	            
				if (table.isRowSelected(row)) {
					label.setBackground(Color.RED);
				} else {
					label.setBackground(UIManager.getColor("Table.background"));
				}
				
				label.setOpaque(true);
				label.setText("" + value);
				
	            return label;
	        }

	    };
	    table.setDefaultRenderer(Object.class, renderer);
	    
	    JScrollPane scroll = new JScrollPane(table);
		
		setLayout(new BorderLayout(5, 5));
		pane.add(scroll, BorderLayout.CENTER);
	    
	    return pane;
	}
	
	private JPanel buildTopPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(10, 10));
		
		// this does not need to be referenced else where, only for layout
		JPanel paneButtons = new JPanel();
		GridLayout bl = new GridLayout(2, 1);
		paneButtons.setLayout(bl);
		
		paneButtons.add(bCastOff);
		paneButtons.add(bSavePlayer);
		
		// add all components on top:
		panel.add(imgDisplay, BorderLayout.LINE_START);
		panel.add(paneEditFields, BorderLayout.CENTER);
		panel.add(paneButtons, BorderLayout.LINE_END);
		
		return panel;
	}
	
	/**
	 * gets the current information with the current contestant, will update 
	 * from the fields associated.
	 * @return Current contestant loaded
	 */
	private Contestant getCurrentContestant() {
		boolean newCont = false;
		Contestant x = null;
		if (activeCon == INACTIVE_CONT) {
			activeCon = new Contestant();
			newCont = true;
		}
		
		activeCon.setFirstName(tfFirstName.getText());
		activeCon.setLastName(tfLastName.getText());
		activeCon.setTribe((String)cbTribe.getSelectedItem());
		activeCon.setPicture(imgPath);
		
		String id = tfContID.getText();
		if (newCont) {
			if (GameData.getCurrentGame().isIDValid(id)) {
				activeCon.setID(id);
			} else {
				id = GameData.getCurrentGame().generateContestantID(activeCon);
				activeCon.setID(id);
			}
		}
			
		x = activeCon;
		activeCon = INACTIVE_CONT;
		
		return x;
	}
	
	/**
	 * Updates the image displayed to have the path associated, helper method
	 * <br>
	 * <b>Note:</b> Pictures must be PNG format.
	 * @param path Path to new image.
	 */
	private void updateContPicture(String path) {
		//apparently images have to be .png and alphanumeric
		try {
			Image img = ImageIO.read(new File(path));
			
			// scale the image!
			img = img.getScaledInstance(imgDisplay.getWidth()-2, 
					imgDisplay.getHeight()-2, Image.SCALE_FAST);
			
			// NO IO errors occured if getting here:
			ImageIcon imgD = new ImageIcon(img);
			imgDisplay.setIcon(imgD);
			imgPath = path;
		} catch (IOException e) {
			System.out.println("Exception loading image for contestant " +
					"picture [" + path + "]");
			imgDisplay.setIcon(null);
			imgDisplay.setText("Could not load: " + path);
		}
		
	}
	
	private void setActiveContestant(Contestant c) {
		if (c == INACTIVE_CONT) {
			tfFirstName.setText("");
			tfLastName.setText("");
			labelCastStatus.setText("-");
			cbTribe.setSelectedItem(0);
			
			updateContPicture(DEFAULT_PICTURE);
			return;
		}
		
		activeCon = c;
		
		tfFirstName.setText(c.getFirstName());
		tfLastName.setText(c.getLastName());
		
		if (c.isCastOff()) {
			labelCastStatus.setText("Week: " + c.getCastDate());
		} else {
			labelCastStatus.setText("Active");
		}
		
		cbTribe.setSelectedItem(c.getTribe());
		
		updateContPicture(c.getPicture());
	}
	
	private void buildActions() {
		bSavePlayer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!Main.checkString(tfFirstName.getText().trim(), GameData.REGEX_FIRST_NAME) ||
						!Main.checkString(tfLastName.getText().trim(), GameData.REGEX_LAST_NAME)){
					JOptionPane.showMessageDialog(null,"Invalid name!(dialog box not permanent)");
					return;
				}
				
				if(!Main.checkString(tfContID.getText(), GameData.REGEX_CONTEST_ID)){
					JOptionPane.showMessageDialog(null, 
							"Invalid ID! (Generating new ID)");
					//return;
				}
				
				// check if the contestant is active
				Contestant con = getCurrentContestant();
				
				tableModel.updateContestant(con);
				
				System.out.println("We here");
				
			}
			
		});
		
		imgDisplay.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int ret = fc.showOpenDialog(null);
				if(ret==JFileChooser.APPROVE_OPTION){
					//File f = fc.getSelectedFile();
					updateContPicture(fc.getSelectedFile().getAbsolutePath());
				}	
			}
			
		});
		
		bCastOff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// can't cast off someone already off.
				if (activeCon.isCastOff())
					return;
				
				activeCon.castOff();
				
				labelCastStatus.setText("Week " + activeCon.getCastDate());
				
				System.out.println("Casting off: " + activeCon.getID());
			}
		});
		
		table.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
		        
		    }
		});
	}
	
}
