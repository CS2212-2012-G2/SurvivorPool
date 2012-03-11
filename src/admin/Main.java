package admin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;

import admin.contestanttab.ContestantPanel;
import admin.data.GameData;
import admin.playertab.PlayerPanel;

public class Main extends JFrame{

	static Main m;
	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu mnuFile = new JMenu("File");
	private JMenu mnuTheme = new JMenu("Theme");
	
	private JMenuItem mnuItemReset;
	private JMenuItem mnuItemExit;
	private JRadioButtonMenuItem mnuItemTheme1;
	private JRadioButtonMenuItem mnuItemTheme2;
	private JRadioButtonMenuItem mnuItemTheme3;
	
	private JLabel statusBar = new JLabel();

	ActionListener al = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource() == mnuItemExit) {
				System.exit(0);
			}else if (ae.getSource() == mnuItemReset){
				resetSeason();
			}else if (ae.getSource() == mnuItemTheme1) {
				changeTheme(ae.getActionCommand());
			}else if (ae.getSource() == mnuItemTheme3) {
				changeTheme(ae.getActionCommand());
			}else if (ae.getSource() == mnuItemTheme2) {
				changeTheme(ae.getActionCommand());
			}

		}
		
	};
	
	public Main(){
		
		GameData g = GameData.initGameData();
		if(g!=null)
			initGUI();
		else
			initSeasonCreateGUI();
		
		applyTheme();
		this.setSize(640, 480);
		this.setVisible(true);
		this.setTitle("Survivor Pool Admin");
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we) {
				if(GameData.getCurrentGame()!=null)
					GameData.getCurrentGame().writeData();
			    System.exit(0);
			}
		});
	}
	
	private void initSeasonCreateGUI(){
		this.add(new SeasonCreatePanel());
	}
	
	private void initGUI(){
		JTabbedPane tabPane = new JTabbedPane();
		
		JLabel lblGeneral = new JLabel("General");
		JLabel lblContestants = new JLabel("Contestants");
		JLabel lblPlayers = new JLabel("Players");
		
		Dimension d = new Dimension(150,20);
		
		lblGeneral.setPreferredSize(d);
		lblContestants.setPreferredSize(d);
		lblPlayers.setPreferredSize(d);
		
		tabPane.addTab(lblGeneral.getText(),new GeneralPanel());
		tabPane.addTab(lblContestants.getText(),new ContestantPanel());
		tabPane.addTab(lblPlayers.getText(), new PlayerPanel());
		
		tabPane.setTabComponentAt(0, lblGeneral);
		tabPane.setTabComponentAt(1, lblContestants);
		tabPane.setTabComponentAt(2, lblPlayers);
		//tabPane.setBackground(Color.cyan);//tab background color,not the panel
		
		
		mnuItemReset = new JMenuItem("Reset");
		mnuItemExit = new JMenuItem("Exit");
		String[] themeName = Utils.getThemes();
		mnuItemTheme1 = new JRadioButtonMenuItem(themeName[0]);
		mnuItemTheme3 = new JRadioButtonMenuItem(themeName[1]);
		mnuItemTheme2 = new JRadioButtonMenuItem(themeName[2]);
		
		ButtonGroup g = new ButtonGroup();
		mnuTheme.add(mnuItemTheme1);
		g.add(mnuItemTheme1);
		mnuTheme.add(mnuItemTheme3);
		g.add(mnuItemTheme3);
		mnuTheme.add(mnuItemTheme2);
		g.add(mnuItemTheme2);
		
		mnuFile.add(mnuItemReset);
		mnuFile.add(mnuItemExit);
		
		menuBar.add(mnuFile);
		menuBar.add(mnuTheme);
		
		statusBar = new JLabel("General Panel");
		
		mnuItemReset.addActionListener(al);
		mnuItemExit.addActionListener(al);
		mnuItemTheme1.addActionListener(al);
		mnuItemTheme3.addActionListener(al);
		mnuItemTheme2.addActionListener(al);
		
		this.setLayout(new BorderLayout());
		
		this.setJMenuBar(menuBar);
		this.add(tabPane);
		this.add(statusBar, BorderLayout.SOUTH);
	}
	
	/**
	 * Apply the theme to current components.
	 */
	private void applyTheme(){
		Utils.style(this);
	}
	
	/**
	 * Change the current theme.
	 * @param name The theme name
	 */
	private void changeTheme(String name){
		Utils.changeTheme(name);
		applyTheme();
	}
	
	public static void seasonCreated(){
		GameData.initGameData();
		m.getContentPane().removeAll();
		m.initGUI();
		m.applyTheme();
	}
	
	private void resetSeason() {
		//TODO: Using json, need to remove the settings.dat file and reset season.
		System.out.println("need to implement");
	}
	
	//Status bar message getter
	public String getStatusBarMessage() {
		return statusBar.getText();
	}
	
	//Status bar message setter
	public void setStatusBarMessage(String newMessage) {
		statusBar.setText(newMessage);
	}
	
	public static void main(String[] args) {
		m = new Main();
	}

}
