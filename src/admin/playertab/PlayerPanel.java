package admin.playertab;

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
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import data.Person;

// TODO: REWRITE AND REBUILD. 

public class PlayerPanel extends JPanel {
	private JLabel textBanner, textFirst, textLast; // labels with the panels
	private Font font; // fonts to style the labels
	private JButton buttonCreate; // buttons
	private JTextField firstNameInput, lastNameInput;
	private FileWriter fileWrite = null; // I/O
	private BufferedWriter buffWrite = null;
	private StringTokenizer st;

	public PlayerPanel() {
		setBorder(BorderFactory.createEmptyBorder(45, 115, 5, 115));
		setLayout(new FlowLayout());

		/* banner */
		textBanner = new JLabel("CREATE PLAYER", SwingConstants.CENTER); // large
																			// title
																			// banner
		font = new Font("Verdana", Font.BOLD, 50);
		textBanner.setOpaque(false);
		textBanner.setFont(font);
		add(textBanner);

		/* first name */
		textFirst = new JLabel("First Name", SwingConstants.CENTER); // large
																		// title
																		// banner
		font = new Font("Verdana", Font.BOLD, 20);
		textFirst.setOpaque(false);
		textFirst.setFont(font);
		add(textFirst);

		/* first name textField */
		firstNameInput = new JTextField();
		firstNameInput.setPreferredSize(new Dimension(340, 24));
		add(firstNameInput);

		/* last name */
		textLast = new JLabel("Last Name", SwingConstants.CENTER); // large
																	// title
																	// banner
		font = new Font("Verdana", Font.BOLD, 20);
		textLast.setOpaque(false);
		textLast.setFont(font);
		add(textLast);

		/* last name textField */
		lastNameInput = new JTextField();
		lastNameInput.setPreferredSize(new Dimension(340, 24));
		add(lastNameInput);

		/* create season button */
		buttonCreate = new JButton("Create Player"); // the start button
		buttonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // if pressed
				if (firstNameInput.getText().matches(Person.REGEX_FIRST_NAME)
						&& lastNameInput.getText().matches(Person.REGEX_LAST_NAME)) {

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
							buffWrite.write(checkUserID(tempUserID) + " "
									+ firstNameInput.getText().toLowerCase()
									+ " "
									+ lastNameInput.getText().toLowerCase()
									+ " " + 0);
							buffWrite.newLine();
							buffWrite.close(); // close the file
							
							JOptionPane.showMessageDialog(null,"Created Player with ID: " + tempUserID);
						} catch (Exception i) {
						}

					} catch (NumberFormatException nFE) {
						firstNameInput.setText(""); // clear the input field
						lastNameInput.setText(""); // clear the input field
					}
				} else { // if not proper parameter requirements
					JOptionPane.showMessageDialog(null,"Invalid names!(dialog box not permanent)");
				}
				
				firstNameInput.setText(""); // clear the input field
				lastNameInput.setText(""); // clear the input field
			}
		});
		add(buttonCreate);
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
