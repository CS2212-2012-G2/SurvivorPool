package client;

/* Author: CS2212 Group 2
 * File Name: SplashScreen.java
 * Date: 25/01/2012
 * Project: UWOSurvivorPool
 * Course: CS2212b
 * Description: 
 * */

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.container.*;
import net.rim.device.api.ui.toolbar.*;
import net.rim.device.api.util.StringProvider;
import net.rim.device.api.command.*;

public class SplashScreen extends MainScreen implements FieldChangeListener {
	/* Variables */
	private ButtonField button1; // The continue button.
	private FontFamily ff1; // fonts.
	private Font font1; // fonts.
	private EditField edit; // editable text field
	private Bitmap backgroundBitmap; // background image
	private String userName;

	public SplashScreen() {
		super(NO_VERTICAL_SCROLL);

		/* FOR TESTING PURPOSES ONLY */
		userName = "USER";
		/* REMOVE WHEN DATA PERSISTANCE IS ADDED */

		backgroundBitmap = Bitmap.getBitmapResource("Splash.png"); // background

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
			/* refresh button */
			ToolbarButtonField toolbutton1 = new ToolbarButtonField(null,
					new StringProvider("Refresh"));
			toolbutton1.setCommandContext(new Object() {
				public String toString() {
					return "toolbutton1";
				}
			});
			/* if pressed, go back to the splash screen */
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

		/* Log-in button */
		// centre the button
		button1 = new ButtonField("Log In", LabelField.FIELD_HCENTER);
		button1.setChangeListener(this); // activate listener

		/* build editable text field */
		edit = new EditField("\nUserID:  ", "", 10, EditField.NO_NEWLINE) {
			public void paint(Graphics graphics) { // keep on same line
				graphics.setColor(Color.WHITE); // white text
				super.paint(graphics);
			}
		};
		edit.setMargin(233, 120, 35, 70); // align components
		edit.setNonSpellCheckable(true); // no spell check

		try { // set up the font
			ff1 = FontFamily.forName("Verdana");
			font1 = ff1.getFont(Font.BOLD, 30);
		} catch (final ClassNotFoundException cnfe) {
			UiApplication.getUiApplication().invokeLater(new Runnable() {
				public void run() {
					Dialog.alert("FontFamily.forName() threw "
							+ cnfe.toString());
				}
			});
		}
		edit.setFont(font1);

		/* Build the components to MainScreen */
		this.setStatus(manager);
		vertFieldManager.add(edit);
		vertFieldManager.add(button1);
		this.add(vertFieldManager);
	}

	public void fieldChanged(Field arg0, int arg1) {
		if (arg0 == button1) { // if the log in button is clicked
			if (userName.equals(edit.getText().toUpperCase())) { 
				UiApplication.getUiApplication().pushScreen(
						new MainMenuScreen(userName));
			} else
				edit.setText(""); // bad username, clear the field
		}

	}

}