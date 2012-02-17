package UWOSurvivorAdmin;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ContestantMenuScreen {
	private JFrame frame;
	private JLabel textBanner; // labels with the panels
	private Font font; // fonts to style the labels
	private JButton buttonCreate, buttonModify, buttonDelete, buttonList; // buttons
	private JPanel panel;
	private String skin;
	
	public ContestantMenuScreen(String Tempskin) {
		this.skin = Tempskin;
		
		frame = new JFrame("Main Menu");
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(5, 115, 5, 115));
		panel.setLayout(new GridLayout(5, 1, 5, 5));
		
		/* banner */
		textBanner = new JLabel("CONTESTANT MENU", SwingConstants.CENTER); // large title banner
		font = new Font("Verdana", Font.BOLD, 32);
		textBanner.setOpaque(false);
		textBanner.setFont(font);
		panel.add(textBanner);

		/* list contestants button */
		buttonList = new JButton("List Contestants"); // the continue button
		buttonList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new ListContestantScreen(skin);
			}
		});
		panel.add(buttonList);
		
		/* create Contestant button */
		buttonCreate = new JButton("Create Contestant"); // the continue button
		buttonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new CreateContestantScreen(skin);
			}
		});
		panel.add(buttonCreate);

		/* modify Contestant button */
		buttonModify = new JButton("Modify Contestant"); // the continue button
		buttonModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new ModifyContestantScreen(skin);
			}
		});
		panel.add(buttonModify);

		/* delete Contestant button */
		buttonDelete = new JButton("Delete Contestant"); // the continue button
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				/* push to the next frame */
				new DeleteContestantScreen(skin);
			}
		});
		panel.add(buttonDelete);
		
		/* menu bar */
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		JMenu menuTheme = new JMenu("Theme");
		/* new game menu button */
		JMenuItem menuItemMain = new JMenuItem("Main Menu");
		menuItemMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new MainMenuScreen(skin);
			}
		});
		/* exit menu button */
		JMenuItem menuItemMainExit = new JMenuItem("Exit");
		menuItemMainExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		/* theme selection buttons */
		JMenuItem menuItemMainSunset = new JMenuItem("Sunset");
		menuItemMainSunset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				skin = "Sunset";
				updateTheme();
			}
		});

		JMenuItem menuItemMainUnderwater = new JMenuItem("Underwater");
		menuItemMainUnderwater.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				skin = "Underwater";
				updateTheme();
			}
		});

		JMenuItem menuItemMainJungle = new JMenuItem("Jungle");
		menuItemMainJungle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				skin = "Jungle";
				updateTheme();
			}
		});
		
		updateTheme();
		
		JMenu menuBack = new JMenu("Back");
		JMenuItem menuItemMainBack = new JMenuItem("Go Back");
		menuItemMainBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new MainMenuScreen(skin);
			}
		});
		
		/* build menu bar */
		menuFile.add(menuItemMain);
		menuFile.add(menuItemMainExit);
		menuTheme.add(menuItemMainSunset);
		menuTheme.add(menuItemMainUnderwater);
		menuTheme.add(menuItemMainJungle);
		menuBack.add(menuItemMainBack);
		menuBar.add(menuFile);
		menuBar.add(menuTheme);
		menuBar.add(menuBack);
		frame.setJMenuBar(menuBar);
		
		
		/* build frame */
		frame.setSize(600, 430);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Survivor Admin");
		frame.setLocationRelativeTo(null);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}

	private void updateTheme(){
		if (skin.equals("Sunset")){
			panel.setBackground(Color.orange);
			textBanner.setForeground(Color.black);
			buttonCreate.setBackground(Color.red);
			buttonCreate.setForeground(Color.black);
			buttonModify.setBackground(Color.red);
			buttonModify.setForeground(Color.black);
			buttonDelete.setBackground(Color.red);
			buttonDelete.setForeground(Color.black);
			buttonList.setBackground(Color.red);
			buttonList.setForeground(Color.black);
		} else if (skin.equals("Underwater")){
			panel.setBackground(Color.cyan);
			textBanner.setForeground(Color.white);
			buttonCreate.setBackground(Color.blue);
			buttonCreate.setForeground(Color.white);
			buttonModify.setBackground(Color.blue);
			buttonModify.setForeground(Color.white);
			buttonDelete.setBackground(Color.blue);
			buttonDelete.setForeground(Color.white);
			buttonList.setBackground(Color.blue);
			buttonList.setForeground(Color.white);
		} else {
			panel.setBackground(Color.green);
			textBanner.setForeground(Color.blue);
			buttonCreate.setBackground(Color.white);
			buttonCreate.setForeground(Color.blue);
			buttonModify.setBackground(Color.white);
			buttonModify.setForeground(Color.blue);
			buttonDelete.setBackground(Color.white);
			buttonDelete.setForeground(Color.blue);
			buttonList.setBackground(Color.white);
			buttonList.setForeground(Color.blue);
		}
	}
}