package client;

/* Author: CS2212 Group 2
 * File Name: MainMenuScreen.java
 * Date: 25/01/2012
 * Project: UWOSurvivorPool
 * Course: CS2212b
 * Description:
 * */

import net.rim.device.api.command.Command;
import net.rim.device.api.command.CommandHandler;
import net.rim.device.api.command.ReadOnlyCommandMetadata;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.toolbar.ToolbarButtonField;
import net.rim.device.api.ui.toolbar.ToolbarManager;
import net.rim.device.api.util.StringProvider;

public class MainMenuScreen extends MainScreen implements FieldChangeListener {
	/* Variables */
	private ButtonField button1, button2, button3; // The continue button.
	private Bitmap backgroundBitmap; // background image
	private String userData;

	public MainMenuScreen(String userData) {
		super(NO_VERTICAL_SCROLL);

		/* THIS WILL BE CHANGED IN SOME FORM */
		this.userData = userData;
		/* WHEN DATA PERSISTANCE IS ADDED */

		backgroundBitmap = Bitmap.getBitmapResource("MainMenu.png");

		VerticalFieldManager vertFieldManager = new VerticalFieldManager(
				VerticalFieldManager.USE_ALL_WIDTH
						| VerticalFieldManager.USE_ALL_HEIGHT) {
			// Override the paint method to draw the background image.
			public void paint(Graphics graphics) {
				graphics.drawBitmap(0, 0, 640, 430, backgroundBitmap, 0, 0);
				super.paint(graphics);
			}
		};

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
			/* if pressed, go back to splash */
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

		/* buttons */
		button1 = new ButtonField("View Standings", LabelField.FIELD_HCENTER);
		button1.setChangeListener(this);
		button1.setMargin(230, 0, 0, 0); // formatting the button placements
		button2 = new ButtonField("Make Your Vote", LabelField.FIELD_HCENTER);
		button2.setChangeListener(this);
		button3 = new ButtonField("Bonus Questions", LabelField.FIELD_HCENTER);
		button3.setChangeListener(this);

		/* Build the components to MainScreen */
		this.setStatus(manager);
		vertFieldManager.add(button1);
		vertFieldManager.add(button2);
		vertFieldManager.add(button3);
		this.add(vertFieldManager);
	}

	public void fieldChanged(Field arg0, int arg1) {
		if (arg0 == button1) { // View Standings
			UiApplication.getUiApplication().pushScreen(
					new StandingScreen(userData));
		} else if (arg0 == button2) { // Voting Area
			UiApplication.getUiApplication().pushScreen(
					new PickMenuScreen(userData));
		} else if (arg0 == button3) { // Bonus Questions
			UiApplication.getUiApplication().pushScreen(
					new BonusScreen(userData));
		}

	}
}
