package admin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class StartSeasonScreen {
	private JFrame frame;
	private JLabel textBanner, textHeader; // labels with the panels
	private Font font; // fonts to style the labels
	private JButton buttonStart; // buttons
	private JPanel panel;
	private String skin;
	private JTextField betInput;
	private FileWriter fileWrite = null; // I/O
	private BufferedWriter buffWrite = null;

	public StartSeasonScreen(String tempSkin) {
		this.skin = tempSkin;

		frame = new JFrame("Start Season");
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(45, 115, 5, 115));
		panel.setLayout(new FlowLayout ());

		/* banner */
		textBanner = new JLabel("START SEASON", SwingConstants.CENTER); // large
																		// title
																		// banner
		font = new Font("Verdana", Font.BOLD, 50);
		textBanner.setOpaque(false);
		textBanner.setFont(font);
		panel.add(textBanner);

		/* heading */
		textHeader = new JLabel("Enter Weekly Bet Amount",
				SwingConstants.CENTER); // large title banner
		font = new Font("Verdana", Font.BOLD, 20);
		textHeader.setOpaque(false);
		textHeader.setFont(font);
		panel.add(textHeader);

		/* bet textField */
		betInput = new JTextField();
		betInput.setPreferredSize(new Dimension(340, 24));
		panel.add(betInput);

		/* create season button */
		buttonStart = new JButton("Start Season"); // the start button
		buttonStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // if pressed
				if (betInput.getText().length() > 0 && Integer.parseInt(betInput.getText()) >= 0) { // something is entered
					try { // see if the input is an integer
						int tempBetValue = Integer.parseInt(betInput.getText());
						// send tempBetValue to proper data storage.
						try {
							fileWrite = new FileWriter(
									"src/data/SeasonSettings", true);
							buffWrite = new BufferedWriter(fileWrite);
							 String tempString = Integer.toString(tempBetValue);
							buffWrite.write("Weekly_Bet_Value: " + tempString); // first line
							buffWrite.newLine();
							buffWrite.write("Current_Week: 1"); // first line
							buffWrite.newLine();
							buffWrite.close(); // close the file
						} catch (Exception i) {
						}
						
						frame.dispose();
						new MainMenuScreen(skin);

					} catch (NumberFormatException nFE) { // if not an integer
						betInput.setText(""); // clear the input field
					}
				}
				else { // if not proper parameter requirements
					betInput.setText(""); // clear the input field
				}
			}
		});
		panel.add(buttonStart);

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

	private void updateTheme() {
		if (skin.equals("Sunset")) {
			panel.setBackground(Color.orange);
			textBanner.setForeground(Color.black);
			textHeader.setForeground(Color.black);
			buttonStart.setBackground(Color.red);
			buttonStart.setForeground(Color.black);
		} else if (skin.equals("Underwater")) {
			panel.setBackground(Color.cyan);
			textBanner.setForeground(Color.white);
			textHeader.setForeground(Color.white);
			buttonStart.setBackground(Color.blue);
			buttonStart.setForeground(Color.white);
		} else {
			panel.setBackground(Color.green);
			textHeader.setForeground(Color.blue);
			textBanner.setForeground(Color.blue);
			buttonStart.setBackground(Color.white);
			buttonStart.setForeground(Color.blue);
		}
	}

}
