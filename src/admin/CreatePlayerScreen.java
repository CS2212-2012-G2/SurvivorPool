package admin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

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
import java.util.StringTokenizer;

public class CreatePlayerScreen {
	private JFrame frame;
	private JLabel textBanner, textFirst, textLast; // labels with the panels
	private Font font; // fonts to style the labels
	private JButton buttonCreate; // buttons
	private JPanel panel;
	private String skin;
	private JTextField firstNameInput, lastNameInput;
	private FileWriter fileWrite = null; // I/O
	private BufferedWriter buffWrite = null;
	private StringTokenizer st;

	public CreatePlayerScreen(String tempSkin) {
		this.skin = tempSkin;

		frame = new JFrame("Create Player");
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(45, 115, 5, 115));
		panel.setLayout(new FlowLayout());

		/* banner */
		textBanner = new JLabel("CREATE PLAYER", SwingConstants.CENTER); // large
																			// title
																			// banner
		font = new Font("Verdana", Font.BOLD, 50);
		textBanner.setOpaque(false);
		textBanner.setFont(font);
		panel.add(textBanner);

		/* first name */
		textFirst = new JLabel("First Name", SwingConstants.CENTER); // large
																		// title
																		// banner
		font = new Font("Verdana", Font.BOLD, 20);
		textFirst.setOpaque(false);
		textFirst.setFont(font);
		panel.add(textFirst);

		/* first name textField */
		firstNameInput = new JTextField();
		firstNameInput.setPreferredSize(new Dimension(340, 24));
		panel.add(firstNameInput);

		/* last name */
		textLast = new JLabel("Last Name", SwingConstants.CENTER); // large
																	// title
																	// banner
		font = new Font("Verdana", Font.BOLD, 20);
		textLast.setOpaque(false);
		textLast.setFont(font);
		panel.add(textLast);

		/* last name textField */
		lastNameInput = new JTextField();
		lastNameInput.setPreferredSize(new Dimension(340, 24));
		panel.add(lastNameInput);

		/* create season button */
		buttonCreate = new JButton("Create Player"); // the start button
		buttonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // if pressed
				if (firstNameInput.getText().length() > 0
						&& firstNameInput.getText().length() < 21
						&& lastNameInput.getText().length() > 0
						&& lastNameInput.getText().length() < 21
						&& firstNameInput.getText().matches("^[a-zA-Z]+$")
						&& lastNameInput.getText().matches("^[a-zA-Z]+$")) {

					try { // see if the input is an integer
						try {
							fileWrite = new FileWriter("src/data/UserList",
									true);
	
							int tempEndRange = 6;
							if (lastNameInput.getText().length() < 6) {
								tempEndRange = lastNameInput.getText().length();
							}
							String tempUserID = firstNameInput.getText()
									.substring(0, 1).toLowerCase()
									+ lastNameInput.getText()
											.substring(0, tempEndRange)
											.toLowerCase();
							
							buffWrite = new BufferedWriter(fileWrite);
							/* write output: userID FirstName LastName Score */
							buffWrite.write(checkUserID(tempUserID)
										+ " "
										+ firstNameInput.getText()
												.toLowerCase() + " "
										+ lastNameInput.getText().toLowerCase()
										+ " " + 0);
							buffWrite.newLine();
							buffWrite.close(); // close the file
						} catch (Exception i) {
						}

						frame.dispose();
						new MainMenuScreen(skin);

					} catch (NumberFormatException nFE) {
						firstNameInput.setText(""); // clear the input field
						lastNameInput.setText(""); // clear the input field
					}
				} else { // if not proper parameter requirements
					firstNameInput.setText(""); // clear the input field
					lastNameInput.setText(""); // clear the input field
				}
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
				new PlayerMenuScreen(skin);
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
			textFirst.setForeground(Color.black);
			textLast.setForeground(Color.black);
			buttonCreate.setBackground(Color.red);
			buttonCreate.setForeground(Color.black);
		} else if (skin.equals("Underwater")) {
			panel.setBackground(Color.cyan);
			textBanner.setForeground(Color.white);
			textFirst.setForeground(Color.white);
			textLast.setForeground(Color.white);
			buttonCreate.setBackground(Color.blue);
			buttonCreate.setForeground(Color.white);
		} else {
			panel.setBackground(Color.green);
			textFirst.setForeground(Color.blue);
			textLast.setForeground(Color.blue);
			textBanner.setForeground(Color.blue);
			buttonCreate.setBackground(Color.white);
			buttonCreate.setForeground(Color.blue);
		}
	}

	private String checkUserID(String userID) {
		int tempCount = 0;
		String tempID = "";
		String tempToken = "";
		boolean foundDuplicate = true;
		
		try {
			while (foundDuplicate) {
				foundDuplicate = false;
				
				if (tempCount == 0)
					tempID = userID;
				else
					tempID = userID + tempCount;
				
				FileInputStream file = new FileInputStream("src/data/UserList");
				DataInputStream input = new DataInputStream(file);
				BufferedReader buff = new BufferedReader(new InputStreamReader(
						input));
				
				String tempLine = buff.readLine();
				
				while (tempLine != null) {
					st = new StringTokenizer(tempLine, " ;");
					tempToken = st.nextToken();
					if (tempID.equals(tempToken)) { // if duplicate found
						tempCount++;
						foundDuplicate = true;
					}
					tempLine = buff.readLine();
				}
				input.close();
			}			
		} catch (Exception e) {
		}
		return tempID;
	}
}
