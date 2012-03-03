package client;

/* Author: CS2212 Group 2
 * File Name: BonusScreen.java
 * Date: 25/01/2012
 * Project: UWOSurvivorPool
 * Course: CS2212b
 * Description:
 * */

import net.rim.device.api.command.Command;
import net.rim.device.api.command.CommandHandler;
import net.rim.device.api.command.ReadOnlyCommandMetadata;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ObjectChoiceField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.toolbar.ToolbarButtonField;
import net.rim.device.api.ui.toolbar.ToolbarManager;
import net.rim.device.api.util.StringProvider;

public class BonusScreen extends MainScreen implements FieldChangeListener {
	/* Variables */
	private LabelField labelTitle; // various labels.
	private int currentQuestionNumber;
	private FontFamily ff1; // fonts.
	private Font font1, font2; // fonts.
	private ButtonField buttonSend, buttonPrevious, buttonNext;
	private EditField answerField, questionField; // editable text field
	private BonusQuestion tempQuestion, tempMultiQuestion, current, tempQuestion2;
	private ObjectChoiceField multiChoiceField;
	private BonusQuestion[] bonusCollection;
	private String[] multiChoiceArray;

	public BonusScreen(String userData) {
		super(NO_VERTICAL_SCROLL);

		/* TESTING PURPOSES, REMOVE WHEN DATA PERSISTANCE IS ACTIVE */
		tempQuestion = new BonusQuestion("QWERTQWERTQWERTQWERTQWERTQWERTQW"
				+ "ERTQWERTQWERTQWERTQWERTQWERTQWERTQWERTQWERTQWERTQWERTQWERTQ"
				+ "WERTQWERT", "word", null, "");
		tempQuestion2 = new BonusQuestion("Qtrerter", "word", null, "");
		String[] multiChoiceArray = { "tim", "bobby", "joer", "summa" };
		tempMultiQuestion = new BonusQuestion("How?", "multi", multiChoiceArray, "");
		bonusCollection = new BonusQuestion[] { tempQuestion, tempQuestion2, tempMultiQuestion };
		currentQuestionNumber = 0;
		current = bonusCollection[currentQuestionNumber];
		/*
		 * WHEN DATA PERSISTANCE IS ACTIVATED MAKE FOR LOOP THAT BUILDS EVERY
		 * QUESTION INTO A OBJECT AND FILLS THE QUESTION ARRAY
		 * ------------------
		 * ------------------------------------------------------
		 */

		updateQuestionScreen();
	}

	/* NEEDS WORK ---------------------------------------------------- */
	private void updateQuestionScreen() {
		current = bonusCollection[currentQuestionNumber];

		VerticalFieldManager vertFieldManager = new VerticalFieldManager(
				VerticalFieldManager.USE_ALL_WIDTH | VerticalFieldManager.USE_ALL_HEIGHT) {
			// Override the paint method to draw the background image.
			public void paint(Graphics graphics) {
				graphics.setColor(Color.BLACK);
				graphics.fillRect(0, 0, 640, 430);
				super.paint(graphics);
			}
		};

		/* build the tool bar */
		ToolbarManager manager = new ToolbarManager();
		setToolbar(manager);
		try {
			/* Logout button */
			ToolbarButtonField toolbuttonSend = new ToolbarButtonField(null,
					new StringProvider("Log Out"));
			toolbuttonSend.setCommandContext(new Object() {
				public String toString() {
					return "toolbuttonSend";
				}
			});
			/* if pressed, go back to Splash */
			toolbuttonSend.setCommand(new Command(new CommandHandler() {
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
			manager.add(toolbuttonSend);
			manager.add(toolbutton2);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		/* Font setup */
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

		/* For multiple choice questions */
		int iSetTo = 0; // displays choose

		/*
		 * --------------------------------------------------------------------
		 * GATHER BONUS QUESTION DATA AND FORMAT IT TO SCREEN
		 * 
		 * NEED: QUESTION, QUESTION TYPE, ANSWER LOCATION
		 * 
		 * THOUGHTS: ONLY RETRIEVE QUESTIONS FROM SYSTEM THAT DON'T HAVE
		 * ANSWERS. PILE THEM UP IN SOME FORMAT, THEN BUILD THE LIST FROM THEM.
		 * PUT THEM INTO A DATA STRUCTURE OF SOME SORT, AND CHECK TO SEEWHAT
		 * TYPE OF QUESTION. IF MULTI, ADD THE APPROPRIATE MULTI CHOICE FIELDS
		 * AFTER THE QUESTION IF NOT, ADD THE WORD ANSWER FIELDS
		 * -------------------------------------------------------------------
		 */

		/* Add the question */
		questionField = new EditField(current.getQuestion(), "", 1,
				EditField.NO_NEWLINE) {
			public void paint(Graphics graphics) { // keep on same line
				graphics.setColor(Color.WHITE); // white text
				super.paint(graphics);
			}
		};
		questionField.setEditable(false);
		questionField.setFont(font2);
		questionField.setMargin(30, 20, 30, 20);
		vertFieldManager.add(questionField);

		/* Add the answer method */
		if (current.getType().equals("multi")) {
			/* For multiple choice questions */
			multiChoiceField = new ObjectChoiceField("",
					current.getMultiChoices(), iSetTo,
					ObjectChoiceField.FORCE_SINGLE_LINE
							| ObjectChoiceField.FIELD_HCENTER);
			vertFieldManager.add(multiChoiceField);
		} else {
			/* word answer questions */
			answerField = new EditField("Answer:  ", "", 200,
					EditField.NO_NEWLINE) {
				public void paint(Graphics graphics) { // keep on same line
					graphics.setColor(Color.WHITE); // white text
					super.paint(graphics);
				}
			};
			if (current.hasAnswer()) {
				answerField.setText(current.getAnswer());
			}
			answerField.setMargin(0, 20, 10, 20);
			vertFieldManager.add(answerField);
		}

		/* Add the send button */
		buttonSend = new ButtonField("Send", ButtonField.FIELD_HCENTER
				| ButtonField.FIELD_BOTTOM);
		buttonSend.setChangeListener(this);
		if (current.hasAnswer())
			buttonSend.setEnabled(false);
		buttonSend.setMargin(0, 20, 30, 20);
		vertFieldManager.add(buttonSend);

		HorizontalFieldManager horFieldManager = new HorizontalFieldManager(
				HorizontalFieldManager.USE_ALL_WIDTH
						| HorizontalFieldManager.FIELD_HCENTER) {
			// Override the paint method to draw the background image.
			public void paint(Graphics graphics) {
				// graphics.setColor(Color.GREEN);
				// graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
				super.paint(graphics);
			}
		};

		try { // set up the smaller list font
			ff1 = FontFamily.forName("Verdana");
			font1 = ff1.getFont(Font.BOLD, 48);
		} catch (final ClassNotFoundException cnfe) {
			UiApplication.getUiApplication().invokeLater(new Runnable() {
				public void run() {
					Dialog.alert("FontFamily.forName() threw "
							+ cnfe.toString());
				}
			});
		}

		/* Top manager bar setup */
		buttonPrevious = new ButtonField("Prev"); // previous button
		buttonPrevious.setChangeListener(this);
		if (currentQuestionNumber == 0)
			buttonPrevious.setEnabled(false);
		horFieldManager.add(buttonPrevious);
		labelTitle = new LabelField("  Bonus Questions ");
		labelTitle.setFont(font1); // centre label
		horFieldManager.add(labelTitle);
		buttonNext = new ButtonField("Next"); // next button
		buttonNext.setChangeListener(this);
		if (currentQuestionNumber == bonusCollection.length - 1)
			buttonNext.setEnabled(false);
		horFieldManager.add(buttonNext); 

		this.setTitle(horFieldManager);
		this.add(vertFieldManager);
		this.setStatus(manager);
	}

	public void fieldChanged(Field arg0, int arg1) {
		if (arg0 == buttonSend) { // if the SEND button is clicked
			if (current.getType().equals("word")) {
				if (!answerField.getText().equals("")) { // if not empty input
					buttonSend.setEnabled(false); // turn off button
					current.setAnswer(answerField.getText());
					// collect the answer from answerField and send out to data
				}
			} else {
			//	current.setAnswer(multiChoiceArray[multiChoiceField.getIndex()]);
				buttonSend.setEnabled(false); // turn off button
				// get choice from the choice field, send to data
			}
		} else if (arg0 == buttonNext) {
			currentQuestionNumber++;
			this.deleteAll();
			updateQuestionScreen();
		} else if (arg0 == buttonPrevious) {
			currentQuestionNumber--;
			this.deleteAll();
			updateQuestionScreen();

		}
	}

}