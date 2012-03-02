package admin;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.*;

import admin.playertab.PlayerPanel;

import data.GameData;

public class Main extends JFrame{

	static Main m;
	static GameData gd;
	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu mnuFile = new JMenu("File");
	private JMenu mnuTheme = new JMenu("Theme");
	
	private JMenuItem mnuItemExit;
	private JRadioButtonMenuItem mnuItemSunset;
	private JRadioButtonMenuItem mnuItemJungle;
	private JRadioButtonMenuItem mnuItemUnderwater;

	ActionListener al = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource() == mnuItemExit) {
				System.exit(0);
			} else if (ae.getSource() == mnuItemSunset) {
				System.out.println("Need to implement themes");
			}else if (ae.getSource() == mnuItemJungle) {
				System.out.println("Need to implement themes");
			}else if (ae.getSource() == mnuItemUnderwater) {
				System.out.println("Need to implement themes");
			}

		}
		
	};
	
	public Main(){
		//TODO:get gamedata from initGamedata
		gd = GameData.intGameData("src/data/SeasonSettings");
		//TODO:change to use game data
		if(gd!=null)
			initGUI();
		else
			initSeasonCreateGUI();
			
		this.setSize(640, 480);
		this.setVisible(true);
		this.setTitle("Survivor Pool Admin");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void initSeasonCreateGUI(){
		this.add(new SeasonCreatePanel());
	}
	
	private void initGUI(){
		JTabbedPane tabPane = new JTabbedPane();
		
		//can do this by setting a jlabel as a tab and changing its prefered size
		//<html><body leftmargin=30 topmargin=8 marginwidth=50 marginheight=5>General</body></html>
		
		tabPane.addTab("<html><body><table width='150'>General</table></body></html>",new GeneralPanel());
		tabPane.addTab("Player",new PlayerPanel());
		tabPane.addTab("Users", new UserPanel());
		tabPane.setBackground(Color.cyan);//tab background color,not the panel
		
		
		mnuItemExit = new JMenuItem("Exit");
		mnuItemSunset = new JRadioButtonMenuItem("Sunset");
		mnuItemUnderwater = new JRadioButtonMenuItem("Underwater");
		mnuItemJungle = new JRadioButtonMenuItem("Jungle");
		
		ButtonGroup g = new ButtonGroup();
		mnuTheme.add(mnuItemSunset);
		g.add(mnuItemSunset);
		mnuTheme.add(mnuItemUnderwater);
		g.add(mnuItemUnderwater);
		mnuTheme.add(mnuItemJungle);
		g.add(mnuItemJungle);
		
		mnuFile.add(mnuItemExit);
		
		menuBar.add(mnuFile);
		menuBar.add(mnuTheme);
		
		mnuItemExit.addActionListener(al);
		mnuItemSunset.addActionListener(al);
		mnuItemUnderwater.addActionListener(al);
		mnuItemJungle.addActionListener(al);
		
		this.setJMenuBar(menuBar);
		this.add(tabPane);
	}
	
	public static void seasonCreated(){
		m.getContentPane().removeAll();
		m.initGUI();
	}
	
	public static GameData getGameData(){
		return gd;
	}
	
	/**
	 * Checks if string matches pattern.
	 * @param val The string to check for validity
	 * @param pattern A regex pattern that has all possible valid values
	 * @return true if string matches pattern
	 */
	public static boolean checkString(String val,String pattern){
		if(val==null)
			return false;
		if(val.length()==0)
			return false;
		return Pattern.matches(pattern, val);
	}
	public static void main(String[] args) {
		m = new Main();

	}

}