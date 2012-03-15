package admin.contestanttab;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import data.InvalidFieldException;

import admin.GameDataDependant;
import admin.Utils;
import admin.FileDrop;
import admin.MainFrame;
import data.GameData;
import data.Contestant;
import data.Person;

public class ContestantPanel extends JPanel implements MouseListener, GameDataDependant {

	private static final long serialVersionUID = 1L;
	private JButton imgDisplay;
	private String imgPath;

	private ContestantFieldsPanel paneEditFields;
	// container for top stuff
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
	private ContestantTableModel tableModel; 
	private JTableHeader header;
	
	private JButton bAddNew;
	private JButton bDelete;
	
	private static String DEFAULT_PICTURE = "res/test/defaultpic.png";
	private static int IMAGE_MAX_DIM = 75;
	
	
	public ContestantPanel(){
		super();
		
		//////////////////////////////
		// Top Panel:
		//////////////////////////////
		// TODO: Better Test picture
		imgDisplay = new JButton();
		updateContPicture(DEFAULT_PICTURE); //apparently images have to be .png and alphanumeric
		
		
		// Edit fields:
		labelName = new JLabel("Name:");
		tfFirstName = new JTextField(20);
		tfLastName = new JTextField(20);
		
		labelCastOff = new JLabel("Cast off:");
		// TODO: FIx the init of this.. :>
		labelCastStatus = new JLabel("-");
		
		labelTribe = new JLabel("Tribe:");
		cbTribe = new JComboBox<String>(GameData.getCurrentGame().getTribeNames());
		
		labelID = new JLabel("ID:");
		tfContID = new JTextField(2);
		
		// holds all the fields
		paneEditFields = new ContestantFieldsPanel(labelName, tfFirstName, 
					tfLastName, labelID, tfContID, labelCastOff, 
					labelCastStatus, labelTribe, cbTribe);
		// add the mouse listener to all components.
		for (Component c: paneEditFields.getComponents()) {
			c.addMouseListener(this);
		}
		
		
		// buttons:
		bCastOff = new JButton("Cast Off");
		bSavePlayer = new JButton("Save");
		
		//////////////////////////////
		// Mid
		//////////////////////////////
		List<Contestant> cons = GameData.getCurrentGame().getAllContestants();
		tableModel = new ContestantTableModel(cons);
		table = new JTable(tableModel);
		header = table.getTableHeader();
		
		//////////////////////////////
		// Bottom
		//////////////////////////////
		bAddNew = new JButton("Add");
		bDelete = new JButton("Delete");
		
		// build the two panes
		//setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setLayout(new BorderLayout(5, 5));
		buildTopPanel();
		buildTablePanel();
		buildBottomPanel();
		
		buildActions();
		
	}
	
	/**
	 * Builds the top panel including all the editable information
	 */
	private void buildTopPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(10, 10));
		
		// this does not need to be referenced else where, only for layout
		JPanel paneButtons = new JPanel();
		GridLayout bl = new GridLayout(2, 1);
		paneButtons.setLayout(bl);
		
		/* check to stop casting off before start */
		// TODO: Doesn't change back when started
		if (!GameData.getCurrentGame().isSeasonStarted())
			bCastOff.setEnabled(false);
		
		paneButtons.add(bCastOff);
		
		paneButtons.add(bSavePlayer);
		
		// add all components on top:
		panel.add(imgDisplay, BorderLayout.LINE_START);
		panel.add(paneEditFields, BorderLayout.CENTER);
		panel.add(paneButtons, BorderLayout.LINE_END);
		
		add(panel, BorderLayout.PAGE_START);
		
		// add the mouse listener to all components.
		for (Component c: panel.getComponents()) {
			c.addMouseListener(this);
		}
		
		for (Component c: paneButtons.getComponents())
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
 		for (Component c: scroll.getComponents()) {
 			c.addMouseListener(this);
 		}
	}
	
	private void buildBottomPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		panel.add(bAddNew);
		panel.add(bDelete);
		
		add(panel, BorderLayout.PAGE_END);
		// add the mouse listener to all components.
		for (Component c: panel.getComponents()) {
			c.addMouseListener(this);
		}
	}
	
	/**
	 * Updates the image displayed to have the path associated, helper method
	 * <br>
	 * <b>Note:</b> Pictures must be PNG format.
	 * @param path Path to new image.
	 */
	//apparently images have to be .png and alphanumeric
	private void updateContPicture(String path) {
		// don't update if its already correct!
		if (imgPath == path) {
			return;
		}
		
		try {
			Image img = ImageIO.read(new File(path));
			if(img==null)
				throw new IOException();
			
			// TODO: Make this scale more approriately using Image's resolution/aspect ratio
			// scale the image!
			if (img.getWidth(null) > IMAGE_MAX_DIM ||
					img.getHeight(null) > IMAGE_MAX_DIM) {
				img = img.getScaledInstance(Math.min(IMAGE_MAX_DIM, img.getWidth(null)),
						Math.min(IMAGE_MAX_DIM, img.getHeight(null)), 
						Image.SCALE_SMOOTH);
			}
			
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
	
	/**
	 * gets the current information with the current contestant, will update 
	 * from the fields associated.
	 * @return Current contestant loaded
	 */
	private Contestant getCurrentContestant() {
		Contestant tempCon = activeCon;
		activeCon = new Contestant();
		
		try {
			String id = tfContID.getText();
			if (GameData.getCurrentGame().isIDValid(id)) {
				activeCon.setID(id);
			} else {
				throw new InvalidFieldException("Invalid ID by double occurance.");
			}
			
			activeCon.setFirstName(tfFirstName.getText().trim());
			activeCon.setLastName(tfLastName.getText().trim());
			activeCon.setTribe((String)cbTribe.getSelectedItem());
			activeCon.setPicture(imgPath);
		} catch (InvalidFieldException ife) {
			System.out.println("Invalid field in " + 
					"ContestantPanel.getCurrentContestant:");	
			System.out.println("\t" + ife.getMessage());
			
			activeCon = tempCon;
			return null;
		}
			
		tempCon = activeCon;
		activeCon = INACTIVE_CONT;
		
		return tempCon;
	}
	
	private void setActiveContestant(Contestant c) {
		activeCon = c;
		
		tfFirstName.setText(c.getFirstName());
		tfLastName.setText(c.getLastName());
		
		if (c.isCastOff()) {
			labelCastStatus.setText("Week: " + c.getCastDate());
		} else {
			labelCastStatus.setText("Active");
		}
		
		cbTribe.setSelectedItem(c.getTribe());
		
		tfContID.setText(c.getID());
		
		updateContPicture(c.getPicture());
	}
	
	private void buildActions() {
		bSavePlayer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame frame = MainFrame.getRunningFrame();
				if(!Utils.checkString(tfFirstName.getText().trim(), Person.REGEX_FIRST_NAME)){
					frame.setStatusErrorMsg("Invalid first name. 1-20 alphabetic characters",tfFirstName);
				}else if(!Utils.checkString(tfLastName.getText().trim(), Person.REGEX_LAST_NAME)){
					frame.setStatusErrorMsg("Invalid last name. 1-20 alphabetic characters",tfLastName);
				}else if(!Utils.checkString(tfContID.getText(), Person.REGEX_CONTEST_ID)){
					frame.setStatusErrorMsg("Invalid contestant id. 2 alphanumeric characters",tfContID);
				}else{	
					// check if the contestant is active
					Contestant con = getCurrentContestant();
					if (con != null) {
						// null if something went wrong.
						tableModel.updateContestant(con);
					}
					System.out.println("We here");
				}
				
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
			public void actionPerformed(ActionEvent e) {
				String s = ((JButton) e.getSource()).getText();
				if(s.equals("Cast Off")){
					// can't cast off someone already off.
				if (activeCon.isCastOff())
					return;
			
				activeCon.toCastOff();
				labelCastStatus.setText("Week " + activeCon.getCastDate());
				}
				else{
				activeCon.undoCast();
				labelCastStatus.setText("Active");
				bCastOff.setText("Cast Off");
				}	
			}
		});
		
		bDelete.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
    			int num = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " +
    					   activeCon.getFirstName() + " " + activeCon.getLastName() + "?",
    					  "Delete Contestant", JOptionPane.YES_OPTION, JOptionPane.NO_OPTION);
    			if(num == JOptionPane.YES_OPTION)
				tableModel.removeContestant(activeCon);
    			else return;
			}
		});
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

			public void valueChanged(ListSelectionEvent arg0) {
				 int row = table.getSelectedRow();
				 Contestant c = tableModel.getByRow(row);
			     
				 if (c != null){
					 setActiveContestant(c);
					 if(activeCon.isToBeCast() == true)
						 bCastOff.setText("Undo Cast");
					 else bCastOff.setText("Cast Off");
					 
				 }
			}
		});
		
		new FileDrop( this, new FileDrop.Listener(){   
			public void filesDropped( java.io.File[] files ){   
				updateContPicture(files[0].getAbsolutePath());
			}  
		});
	}

	public void seasonStarted(){
		bAddNew.setEnabled(false);
		bDelete.setEnabled(false);
		tfLastName.setEnabled(false);
		tfFirstName.setEnabled(false);
		tfContID.setEnabled(false);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		return;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Component c = e.getComponent();
		MainFrame mf = MainFrame.getRunningFrame();
		
		if (c == labelName || c == tfFirstName || c == tfLastName) {
			mf.setStatusMsg("First and Last name must be alphabetic");
		} else if (c == labelID || c == tfContID) {
			mf.setStatusMsg("ID must be two characters long and alpha-numeric");
		} else if (c == labelTribe || c == cbTribe) {
			mf.setStatusMsg("Select a tribe");
		} else if (c == imgDisplay) {
			mf.setStatusMsg("Click to select image");
		} else if (c == table) {
			mf.setStatusMsg("Click row to edit contestant");
		} else if (c == bAddNew) {
			mf.setStatusMsg("Click to add new contestant");
		} else if (c == bSavePlayer) {
			mf.setStatusMsg("Click to save contestant data");
		}
		//System.out.println("MouseEntered: " + c.toString());
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseEntered(e);
	}

	// unused
	@Override
	public void mousePressed(MouseEvent e) {
		return;
	}

	// unused
	@Override
	public void mouseReleased(MouseEvent e) {
		return;
	}

	/**
	 * Refreshes all values associated with GameData reference.
	 * <br> Currently:
	 * - Tribe combobox
	 * - Table
	 * @see GameDataDependant.refreshGameFields
	 */
	@Override
	public void refreshGameFields() {
		GameData g = GameData.getCurrentGame();
		
		// tribe combobox
		String[] newTribes = g.getTribeNames();
		cbTribe.removeAllItems();
		for (String s: newTribes) {
			cbTribe.addItem(s);
		}
		
		tableModel.fireTableDataChanged();
	}

}
