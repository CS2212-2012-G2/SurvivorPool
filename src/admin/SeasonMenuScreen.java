package admin;

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

public class SeasonMenuScreen {
	private JFrame frame;
	private JLabel textBanner; // labels with the panels
	private Font font; // fonts to style the labels
	private JButton buttonStartSeason, buttonAdvance, buttonCreate; // buttons
	private JPanel panel;
	private String skin;
	
	public SeasonMenuScreen(String Tempskin) {
		this.skin = Tempskin;
		
		frame = new JFrame("Season Menu");
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(5, 115, 5, 115));
		panel.setLayout(new GridLayout(5, 1, 5, 5));
		
		/* banner */
		textBanner = new JLabel("SEASON MENU", SwingConstants.CENTER); // large title banner
		font = new Font("Verdana", Font.BOLD, 40);
		textBanner.setOpaque(false);
		textBanner.setFont(font);
		panel.add(textBanner);

		/* start season button */
		buttonStartSeason = new JButton("Start Season"); // the continue button
		buttonStartSeason.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				/* push to the next frame */
				new StartSeasonScreen(skin);
			}
		});
		panel.add(buttonStartSeason);

		/* advance week button */
		buttonAdvance = new JButton("Advance Week"); // the continue button
		buttonAdvance.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/* ---------------- ADVANCE THE WEEK      ------------------*/
			}
		});
		panel.add(buttonAdvance);

		/* create season button */
		buttonCreate = new JButton("Create Season"); // the continue button
		buttonCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				/* push to the next frame */
				new CreateSeasonScreen(skin);
			}
		});
		panel.add(buttonCreate);
		
		/* menu bar */
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		JMenu menuTheme = new JMenu("Theme");
		/* new game menu button */
		JMenuItem menuItemMain = new JMenuItem("Main Menu");
		menuItemMain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new MainMenuScreen(skin);
			}
		});
		/* exit menu button */
		JMenuItem menuItemMainExit = new JMenuItem("Exit");
		menuItemMainExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		/* theme selection buttons */
		JMenuItem menuItemMainSunset = new JMenuItem("Sunset");
		menuItemMainSunset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				skin = "Sunset";
				updateTheme();
			}
		});

		JMenuItem menuItemMainUnderwater = new JMenuItem("Underwater");
		menuItemMainUnderwater.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				skin = "Underwater";
				updateTheme();
			}
		});

		JMenuItem menuItemMainJungle = new JMenuItem("Jungle");
		menuItemMainJungle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				skin = "Jungle";
				updateTheme();
			}
		});
		
		/* first skin */
		updateTheme();
		
		JMenu menuBack = new JMenu("Back");
		JMenuItem menuItemMainBack = new JMenuItem("Go Back");
		menuItemMainBack.addActionListener(new ActionListener() {
			@Override
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
			buttonStartSeason.setBackground(Color.red);
			buttonStartSeason.setForeground(Color.black);
			buttonAdvance.setBackground(Color.red);
			buttonAdvance.setForeground(Color.black);
			buttonCreate.setBackground(Color.red);
			buttonCreate.setForeground(Color.black);
		} else if (skin.equals("Underwater")){
			panel.setBackground(Color.cyan);
			textBanner.setForeground(Color.white);
			buttonStartSeason.setBackground(Color.blue);
			buttonStartSeason.setForeground(Color.white);
			buttonAdvance.setBackground(Color.blue);
			buttonAdvance.setForeground(Color.white);
			buttonCreate.setBackground(Color.blue);
			buttonCreate.setForeground(Color.white);
		} else {
			panel.setBackground(Color.green);
			textBanner.setForeground(Color.blue);
			buttonStartSeason.setBackground(Color.white);
			buttonStartSeason.setForeground(Color.blue);
			buttonAdvance.setBackground(Color.white);
			buttonAdvance.setForeground(Color.blue);
			buttonCreate.setBackground(Color.white);
			buttonCreate.setForeground(Color.blue);
		}
	}
	
}