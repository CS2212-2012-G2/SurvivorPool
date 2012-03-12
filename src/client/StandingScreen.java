package client;

/* Author: CS2212 Group 2
 * File Name: StandingsScreen.java
 * Date: 25/01/2012
 * Project: UWOSurvivorPool
 * Course: CS2212b
 * Description:
 * */

import client.data.GameData;
import data.Contestant;
import data.User;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.command.Command;
import net.rim.device.api.command.CommandHandler;
import net.rim.device.api.command.ReadOnlyCommandMetadata;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.table.RichList;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.toolbar.ToolbarButtonField;
import net.rim.device.api.ui.toolbar.ToolbarManager;
import net.rim.device.api.util.StringProvider;

public class StandingScreen extends MainScreen implements FieldChangeListener {
	/* Variables */
	private LabelField labelTemp; // various labels.
	private String temp;
	private FontFamily ff1; // fonts.
	private Font font1, font2; // fonts.
	private User tempUser;
	
	public StandingScreen(String userData) {
		super();

		VerticalFieldManager vertFieldManager = new VerticalFieldManager(
				VerticalFieldManager.USE_ALL_WIDTH
						| VerticalFieldManager.VERTICAL_SCROLLBAR) {
			// Override the paint method to draw the background image.
			public void paint(Graphics graphics) {
				graphics.setColor(Color.BLACK);
				graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
				super.paint(graphics);
			}
		};
		;

		/* build the tool bar */
		ToolbarManager manager = new ToolbarManager();
		setToolbar(manager);
		try {
			/* Logout button */
			ToolbarButtonField toolbutton1 = new ToolbarButtonField(null,
					new StringProvider("Log Out"));
			toolbutton1.setCommandContext(new Object() {
				public String toString() {
					return "toolbutton1";
				}
			});
			/* if pressed, go back to Splash */
			toolbutton1.setCommand(new Command(new CommandHandler() {
				public void execute(ReadOnlyCommandMetadata metadata,
						Object context) {
					UiApplication.getUiApplication().pushScreen(
							new SplashScreen());
				}
			}));
			/* Exit button */
			ToolbarButtonField toolbutton2 = new ToolbarButtonField(null,
					new StringProvider("Exit"));
			toolbutton2.setCommandContext(new Object() {
				public String toString() {
					return "toolbutton2";
				}
			});
			/* if pressed, exit the system */
			toolbutton2.setCommand(new Command(new CommandHandler() {
				public void execute(ReadOnlyCommandMetadata metadata,
						Object context) {
					System.exit(0);
				}
			}));

			/* add buttons to the tool bar */
			manager.add(toolbutton1);
			manager.add(toolbutton2);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		RichList list = new RichList(vertFieldManager, false, 1, 0);
		try { // set up the header list font
			ff1 = FontFamily.forName("Verdana");
			font1 = ff1.getFont(Font.BOLD, 20);
		} catch (final ClassNotFoundException cnfe) {
			UiApplication.getUiApplication().invokeLater(new Runnable() {
				public void run() {
					Dialog.alert("FontFamily.forName() threw "
							+ cnfe.toString());
				}
			});
		}

		try { // set up the smaller list font
			ff1 = FontFamily.forName("Verdana");
			font2 = ff1.getFont(Font.BOLD, 20);
		} catch (final ClassNotFoundException cnfe) {
			UiApplication.getUiApplication().invokeLater(new Runnable() {
				public void run() {
					Dialog.alert("FontFamily.forName() threw "
							+ cnfe.toString());
				}
			});
		}

		/* build the list */
		temp = "Rank Score Name"; // headers
		labelTemp = new LabelField(temp, LabelField.ELLIPSIS) {
			public void paint(Graphics g) {
				g.setColor(Color.WHITE); // white text
				super.paint(g);
			}
		};
		labelTemp.setFont(font1);

		
		User[] userList = GameData.getCurrentGame().getAllUsers();
		list.add(new Object[] { labelTemp }); // add headers

		/* fill list with players */
		for (int i = 0; i < userList.length; i++) {
			tempUser = userList[i];
			int tempPlacement = i + 1;
			
			// Formatting decisions
			if (tempPlacement > 99)
				temp = " " + tempPlacement + "\t   ";
			else if (tempPlacement > 9)
				temp = "   " + tempPlacement + "\t   ";
			else
				temp = "\t" + tempPlacement + "\t   ";

			if (tempUser.getPoints() > 99)
				temp = temp + tempUser.getPoints() + "   " + tempUser.getFirstName() + " " + tempUser.getLastName();
			else if (tempUser.getPoints() > 9)
				temp = temp + " " + tempUser.getPoints() + "    " + tempUser.getFirstName() + " " + tempUser.getLastName();
			else
				temp = temp + " " + tempUser.getPoints() + "\t\t" + tempUser.getFirstName() + " " + tempUser.getLastName();
			/* list contains labels so that the text colour can change */
			labelTemp = new LabelField(temp, LabelField.ELLIPSIS) {
				public void paint(Graphics g) {
					g.setColor(Color.WHITE);
					super.paint(g);
				}
			};
			labelTemp.setFont(font2);
			list.add(new Object[] { labelTemp });

		}

		/* Build the components to MainScreen */
		this.setStatus(manager);
		this.add(vertFieldManager);

	}

	public void fieldChanged(Field arg0, int arg1) {
		// merely required, but not used.
	}

}