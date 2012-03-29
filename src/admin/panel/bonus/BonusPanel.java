package admin.panel.bonus;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import admin.MainFrame;
import admin.Utils;

import data.GameData;
import data.GameData.UpdateTag;
import data.bonus.Bonus;
import data.bonus.BonusQuestion;
import data.bonus.BonusQuestion.BONUS_TYPE;

/**
 * 
 * @author Kevin Brightwell, Justin MacDonald, Ramesh Raj
 *
 */
public class BonusPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	JPanel pnlNewQInput = new JPanel();
	JPanel pnlListQ = new JPanel();
	JPanel pnlTypeQ = new JPanel();
	JPanel pnlViewWeek = new JPanel();
	JPanel pnlListWeeks = new JPanel();
	JPanel pnlMultA = new JPanel();
	
	JButton btnNext = new JButton("Next");
	
	JLabel lblViewWeek = new JLabel("View Week:");
	
	SpinnerNumberModel weekModel = new SpinnerNumberModel(1, 1, 1, 1); // default,low,min,step
	JSpinner spnWeek = new JSpinner(weekModel);
	
	JLabel lblViewQuestion = new JLabel("View Question:");
	
	SpinnerNumberModel snmQuestion = new SpinnerNumberModel(1, 1, 1, 1); // default,low,min,step
	JSpinner spnQuestion = new JSpinner(snmQuestion);
	
	JButton btnBack = new JButton("Back");
	JButton btnSubmit = new JButton("Submit");
	
	JButton btnModify = new JButton("Modify");
	
	private JTextArea txtQuestionList;

	private String question;
	private String answer;
	
	private JTextField txtAnswerA;
	private JTextField txtAnswerB;
	private JTextField txtAnswerC;
	private JTextField txtAnswerD;
	private List<JTextField> txtAnsList;
	
	private JRadioButton rbAnswerA;
	private JRadioButton rbAnswerB;
	private JRadioButton rbAnswerC;
	private JRadioButton rbAnswerD;
	private List<JRadioButton> rbAnsList;
	
	private boolean shortAns;
	private boolean modifyBonusQuestion = false;
	
	private String currentQuestion;
	
	private BonusQuestion bq;
	
	private int currentWeek;
	private int currentQuestionNumber;
	
	private ChangeListener clWeek;
	private ChangeListener clQuestion;
	
	////////////////// EDITS:
	
	private JPanel pnlQuestionEdit;
	private CardLayout cardsQPanel;
	
	/* first STEP question */
	private JPanel pnlNewQ1;
	
	private JLabel lblPrompt;
	private JTextField tfPromptInput;
	
	private JRadioButton rbMultChoice;
	private JRadioButton rbShortAnswer;
	
	private JButton btnNextPart;
	
	private static final String STEP_1 = "step 1",
			STEP_2 = "step 2";
	
	/* Second STEP panel */
	private JPanel pnlNewQ2;
	
	private JPanel pnlQTypeSwap;
	private CardLayout cardQType;
	
	private JPanel pnlMultiAns;
	
	private JPanel pnlShortAns;
	private JLabel lblQAnswer;
	private JTextField tfQAnswer;
	
	private JButton btnNewQBack;
	private JButton btnNewQSubmit;
	
	private final static String TYPE_MULTI = "multii",
			TYPE_SHORT = "shortt";
	
	/* end step panel */
	
	/**
	 * Constructor for Bonus Panel
	 */
	public BonusPanel() {
		super();
		setLayout(new GridLayout(2, 1, 0, 10));
		
		buildQuestionPaneAll();
		
		// TODO: replace
		initPnlQuestionListing();
		
		
		add(pnlQuestionEdit);
		add(pnlListQ);
		
		
		/*this.setLayout(new BorderLayout());
		currentQuestion = "";
		initPnlAddQuestion();
		initPnlQuestionListing();
		
		//check if bonus questions already exist
		if (Bonus.getAllQuestions().isEmpty()){
			setQuestionAddingPanelEditable(false);
			btnModify.setEnabled(false);
		} else {
			initExistingBonus();
		}*/

		initListeners();
		GameData.getCurrentGame().addObserver(this);
	}
	
	private void buildQuestionPanelP1() {
		// starting card:
		pnlNewQ1 = new JPanel();
		pnlNewQ1.setLayout(new GridLayout(4, 1, 0, 5));
		
		lblPrompt = new JLabel("Prompt:");
		lblPrompt.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		
		tfPromptInput = new JTextField();
		
		// build the radio buttons for type:
		JPanel rbPane = new JPanel();
		rbPane.setLayout(new BoxLayout(rbPane, BoxLayout.X_AXIS));
		
		ButtonGroup bg = new ButtonGroup();
		rbMultChoice = new JRadioButton("Multiple Choice");
		rbShortAnswer = new JRadioButton("Short Answer");
		bg.add(rbMultChoice); // link them together
		bg.add(rbShortAnswer);
		rbMultChoice.setSelected(true);
		
		rbPane.add(rbMultChoice);
		rbPane.add(Box.createHorizontalStrut(10));
		rbPane.add(rbShortAnswer);
		
		btnNextPart = new JButton("Next");
		btnNextPart.setAlignmentX(JButton.RIGHT_ALIGNMENT);
		
		pnlNewQ1.add(lblPrompt);
		pnlNewQ1.add(tfPromptInput);
		pnlNewQ1.add(rbPane);
		pnlNewQ1.add(btnNextPart);
		
	}
	
	
	/**
	 * TODO:
	 */
	private void buildQuestionPanelP2() {
		pnlNewQ2 = new JPanel();
		pnlNewQ2.setLayout(new BoxLayout(pnlNewQ2, BoxLayout.X_AXIS));
		
		pnlQTypeSwap = new JPanel();
		cardQType = new CardLayout();
		pnlQTypeSwap.setLayout(cardQType);
		
		
		/* Multiple choice: */
		// TODO: replace with array
		ButtonGroup bg = new ButtonGroup();
		rbAnswerA = new JRadioButton("A");
		rbAnswerB = new JRadioButton("B");
		rbAnswerC = new JRadioButton("C");
		rbAnswerD = new JRadioButton("D");
		rbAnsList = Arrays.asList(rbAnswerA, rbAnswerB, rbAnswerC, rbAnswerD);
		
		rbAnswerA.setSelected(true);
		
		for (JRadioButton rb: rbAnsList) 
			bg.add(rb);
		
		// multiple choice text fields:
		txtAnswerA = new JTextField("");
		txtAnswerB = new JTextField("");
		txtAnswerC = new JTextField("");
		txtAnswerD = new JTextField("");
		txtAnsList = Arrays.asList(txtAnswerA, txtAnswerB, txtAnswerC, txtAnswerD);
		
		pnlMultiAns = new JPanel();
		pnlMultiAns.setLayout(new GridLayout(4, 0, 0, 5));
		
		for (int i = 0; i < 4; i++) {
			JRadioButton rb = rbAnsList.get(i);
		//	rb.setPreferredSize(new Dimension((int)(rb.getWidth()*1.5d), rb.getHeight()));
			JTextField ans = txtAnsList.get(i);
			
			rb.setAlignmentX(JTextField.LEFT_ALIGNMENT);
			
			// add to button group
			bg.add(rb);
			
			JPanel subPane = new JPanel();
			subPane.setLayout(new BoxLayout(subPane, BoxLayout.X_AXIS));
			subPane.add(rb);
			subPane.add(Box.createHorizontalStrut(5));
			subPane.add(ans);
			
			// put the sub panel inside the main container for the questions
			pnlMultiAns.add(subPane);
		}
		
		/* end multi choice */
		
		/* short answer: */
		
		pnlShortAns = new JPanel();
		pnlShortAns.setLayout(new GridLayout(2, 1, 0, 5));
		
		lblQAnswer = new JLabel("Answer:");
		tfQAnswer = new JTextField();
		
		pnlShortAns.add(lblQAnswer);
		pnlShortAns.add(tfQAnswer);
		
		/* end short */
		
		pnlQTypeSwap.add(pnlMultiAns, TYPE_MULTI);
		pnlQTypeSwap.add(pnlShortAns, TYPE_SHORT);
		
		/* buttons: */
		
		btnNewQBack = new JButton("Back");
		btnNewQSubmit = new JButton("Submit");
		
		btnNewQBack.setAlignmentX(JButton.RIGHT_ALIGNMENT);
		btnNewQSubmit.setAlignmentX(JButton.RIGHT_ALIGNMENT);
		
		btnNewQBack.setSize(btnNewQSubmit.getPreferredSize());
		
		JPanel sub = new JPanel();
		sub.setLayout(new BoxLayout(sub, BoxLayout.Y_AXIS));
		
		sub.add(btnNewQBack);
		sub.add(Box.createVerticalStrut(5));
		sub.add(btnNewQSubmit);
		
		/* end buttons */
		
		
		pnlNewQ2.add(pnlQTypeSwap);
		pnlNewQ2.add(Box.createHorizontalStrut(10));
		pnlNewQ2.add(sub);
		
		if (rbMultChoice.isSelected()) {
			cardQType.show(pnlQTypeSwap, TYPE_MULTI);
		} else {
			cardQType.show(pnlQTypeSwap, TYPE_SHORT);
		}
		
		
	}
	
	/**
	 * Builds the entire question panel (all others included internally).
	 */
	private void buildQuestionPaneAll() {
		pnlQuestionEdit = new JPanel();
		pnlQuestionEdit.setBorder(BorderFactory.createTitledBorder("Question"));
		cardsQPanel = new CardLayout();
		
		pnlQuestionEdit.setLayout(cardsQPanel);
		
		buildQuestionPanelP1();
		buildQuestionPanelP2();
		
		pnlQuestionEdit.add(pnlNewQ1, STEP_1);
		pnlQuestionEdit.add(pnlNewQ2, STEP_2);
		
		cardsQPanel.show(pnlQuestionEdit, STEP_1);
	}
	
	/**
	 * construct the questing LISTING panel
	 */
	private void initPnlQuestionListing() {		
		pnlListQ.setLayout(new BorderLayout());
		pnlListWeeks.setPreferredSize(new Dimension(640, 200));

		this.validate();
		Utils.style(this);
		
		pnlListQ.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		txtQuestionList = new JTextArea("");
		txtQuestionList.setBorder(null);
		txtQuestionList.setEditable(false);
		
		pnlViewWeek.add(lblViewWeek);
		pnlViewWeek.add(spnWeek);
		pnlViewWeek.add(lblViewQuestion);
		pnlViewWeek.add(spnQuestion);
		pnlViewWeek.add(btnModify);
		
		pnlListWeeks.add(txtQuestionList);
		
		pnlListQ.add(pnlViewWeek, BorderLayout.NORTH);
		pnlListQ.add(pnlListWeeks, BorderLayout.CENTER);
	}
	
	/**
	 * initialise the bonus panel if bonus questions already exist
	 */
	private BonusQuestion initExistingBonus() {
		currentWeek = GameData.getCurrentGame().getCurrentWeek();
		currentQuestionNumber = 1;
		
		try {
			bq = Bonus.getQuestion(currentWeek, currentQuestionNumber - 1);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		
		setWeekSpinner(currentWeek, currentWeek);
		setQuestionSpinner(currentQuestionNumber, Bonus.getNumQuestionsInWeek(currentWeek));
		addQuestionToListing(bq);
		
		return bq;
	}
	
	/**
	 * add the indicated bonus question to the LISTING panel 
	 * 		basically, turn the bonus question into readable text
	 * @param q
	 */
	private void addQuestionToListing(BonusQuestion q) {
		if (q != null){
			currentQuestion = 
				"Week: " + "\t\t" + q.getWeek() + "\n" + 
				"Question #: " + "\t\t" + (q.getNumber() + 1) + "\n" +
				"Question Type: " + "\t\t" + q.getBonusType() + "\n" + 
				"Question: " + "\t\t" + q.getPrompt() + "\n" + 
				"Answer: " + "\t\t" + q.getAnswer() + "\n\n";
		} else {
			currentQuestion = "";
		}
		txtQuestionList.setText(currentQuestion);
	}
	
	/**
	 * set the value, and maximum value of the WEEK spinner
	 * @param wValue
	 * @param wMax
	 */
	private void setWeekSpinner(int wValue, int wMax) {
		spnWeek.removeChangeListener(clWeek);
		weekModel.setValue(wValue);
		weekModel.setMaximum(wMax);
		spnWeek.addChangeListener(clWeek);
	}
	
	/**
	 * set the value and maximum value of the QUESTION spinner
	 * @param qValue
	 * @param qMax
	 */
	private void setQuestionSpinner(int qValue, int qMax) {
		spnQuestion.removeChangeListener(clQuestion);
		snmQuestion.setValue(qValue);
		snmQuestion.setMaximum(qMax);
		spnQuestion.addChangeListener(clQuestion);
	}
	
	/**
	 * sets components in the questing adding panel to edit
	 * @param Whether editable or not (enabled).
	 */
	private void setQuestionAddingPanelEditable(boolean edit) {
		tfQAnswer.setEnabled(edit);
		tfPromptInput.setEnabled(edit);
		rbMultChoice.setEnabled(edit);
		rbShortAnswer.setEnabled(edit);
		btnNext.setEnabled(edit);
	}
	
	/**
	 * loads the question adding panel with the current bonus question
	 */
	private void setQuestionAddingPanel(){
		setQuestionAddingPanelEditable(true);
		tfPromptInput.setText(bq.getPrompt());
		modifyBonusQuestion = true;
	}
	
	/**
	 * loads the answer adding panel with the current bonus question
	 */
	private void setAnswerAddingPanel(){
		String[] choices = bq.getChoices();
		if (rbMultChoice.isSelected() && choices != null){
			for (int i = 0; i < 4; i++) 
				txtAnsList.get(i).setText(choices[i]);
			
		} else {//if (rbShortAnswer.isSelected()) { // one must be selected?
			tfQAnswer.setText(bq.getAnswer());
		}
	}
	
	/**
	 * returns the correct answer of a multiple choice question, 
	 * 		indicated by the selected radio button
	 * @return
	 */
	private String getMultiAnswer(){
		for (int i = 0; i < 4; i++) {
			if (rbAnsList.get(i).isSelected()) {
				return txtAnsList.get(i).getText();
			}
		}
		
		return null;
	}
	
	/**
	 * returns a string array of the multiple choice answers
	 * @return answers
	 */
	private String[] getMultiAnswerArray(){
		String[] answers = new String[4];
		for (int i = 0; i < 4; i++) {
			JTextField tf = txtAnsList.get(i);
			if (tf.getText().length() > 0) {
				answers[i] = tf.getText();
			}
		}
		
		return answers;
	}
	
	/**
	 * checks if all multiple choice answers are 1-200 characters 
	 * 		AND if at least one answer exists
	 * @return
	 */
	// returns whether all four are valid as of now.. 
	private Boolean getValidMultiAnswers() {
		boolean res = true;
		for (int i=0; i < 4 && res; i++) {
			String s = txtAnsList.get(i).getText();
			// checks each tf is (1,200)
			res = res && (s.length() > 0 && s.length() <= 200); 
		}
		
		return res;
	}
	
	/**
	 * checks if a String is 1-200 characters
	 * @param t
	 * @return true if text is 1-200 char
	 */
	private Boolean getValidQuestionOrAnswer(String t){
		return (t.length() > 0 && t.length() < 201);
	}
	
	/**
	 * adds a new short answer question:
	 * 		sets Question spinner,
	 * 		adds bonus question to backend,
	 * 		resets question adding panel,
	 * 		adds question to the gui LIST
	 */
	private void addNewShortAnswer() {
		currentWeek = GameData.getCurrentGame().getCurrentWeek();
		currentQuestionNumber = Bonus.getNumQuestionsInWeek(currentWeek) + 1;				
		answer = tfQAnswer.getText();						
		bq = new BonusQuestion(question, answer, null, 
				currentWeek, currentQuestionNumber - 1);

		addQuestionToListing(bq);
		setWeekSpinner(currentWeek, currentWeek);
		setQuestionSpinner(currentQuestionNumber, currentQuestionNumber);
		btnModify.setEnabled(true);
		modifyBonusQuestion = false;
	}
	
	/**
	 * adds a new multiple choice question:
	 * 		sets the question spinner,
	 * 		adds the bonus question to backend,
	 * 		sets gui fields
	 * @param answer: the correct answer
	 * @param answers: the list of possible answers
	 */
	private void addNewMultipleChoice(String answer, String[] answers){
		currentWeek = GameData.getCurrentGame().getCurrentWeek();
		currentQuestionNumber = Bonus.getNumQuestionsInWeek(currentWeek) + 1;
		bq = new BonusQuestion(question, answer, answers, 
				currentWeek, currentQuestionNumber - 1);

		addQuestionToListing(bq);
		setWeekSpinner(currentWeek, currentWeek);
		setQuestionSpinner(currentQuestionNumber, currentQuestionNumber);
		btnModify.setEnabled(true);
		modifyBonusQuestion = false;
	}

	/**
	 * modifies the current short answer question & 
	 * 		sets appropriate gui fields.
	 */
	private void modifyShortAnswer(){
		bq.setAnswer(tfQAnswer.getText());
		bq.setChoices(null);
		bq.setBonusType(BONUS_TYPE.SHORT);

		addQuestionToListing(bq);
		modifyBonusQuestion = false;
	}
	
	/**
	 * modifies the current multiple choice bonus question &
	 * 		resets appropriate gui fields.
	 * @param answer
	 * @param answers
	 */
	private void modifyMultipleChoice(String answer, String[] answers){
		bq.setAnswer(answer);
		bq.setChoices(answers);
		bq.setBonusType(BONUS_TYPE.MULTI);

		addQuestionToListing(bq);
		modifyBonusQuestion = false;
	}
	
	/**
	 * initialises all of the listeners
	 */
	private void initListeners(){
		
		btnNextPart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				if (getValidQuestionOrAnswer(tfPromptInput.getText())){
					
					question = tfPromptInput.getText();
					
					if (modifyBonusQuestion)
						setAnswerAddingPanel();
					
					cardsQPanel.show(pnlQuestionEdit, STEP_2);
						
				} else {
					MainFrame.getRunningFrame().setStatusErrorMsg(
							"Questions must be 1-200 characters."
									+ " (invalid question)", tfPromptInput);
				}
			}		
		});
		
		// TODO: Reorganize
		btnNewQSubmit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				// enter if we are modifying a question
				if (modifyBonusQuestion){
					bq.setPrompt(tfPromptInput.getText());
					
					// is it a short answer?
					if (shortAns){
						if (getValidQuestionOrAnswer(tfQAnswer.getText())){
							
							modifyShortAnswer();
							
						} else {
							MainFrame.getRunningFrame().setStatusErrorMsg(
									"Your answer must be 1-200 characters."
											+ " (invalid answer)", tfQAnswer);
						}
						// otherwise it is a multiple choice question
					} else {
						if (getValidMultiAnswers()){
							
							String[] answers = getMultiAnswerArray();	
							String a = getMultiAnswer();
							
							if (a != null){
								
								modifyMultipleChoice(a, answers);
							} else {
								MainFrame.getRunningFrame().setStatusErrorMsg(
										"You must select one correct answer."
												+ " (correct answer unselected)", 
												rbAnsList.toArray(new Component[0]));
								return;
							}													
						} else {
							MainFrame.getRunningFrame().setStatusErrorMsg(
									"Your must write atleast one answer. Answers must be 1-200 characters."
											+ " (invalid answers)", pnlMultA);
							return;
						}
					}
					
				//otherwise, we are adding a NEW bonus question
				} else {
					if (shortAns){
			
						if (getValidQuestionOrAnswer(tfQAnswer.getText())){
							
							addNewShortAnswer();
							
						} else {
							MainFrame.getRunningFrame().setStatusErrorMsg(
									"Your answer must be 1-200 characters."
											+ " (invalid answer)", tfQAnswer);
						}
					} else {
					// otherwise, we are adding a NEW MULTIPLE CHOICE
				
						if (getValidMultiAnswers()){
					
							String[] answers = getMultiAnswerArray();						
							String a = getMultiAnswer();
							
							if (a != null){
								addNewMultipleChoice(a, answers);
							} else {
								MainFrame.getRunningFrame().setStatusErrorMsg(
										"You must select one correct answer."
												+ " (correct answer unselected)", 
												rbAnswerA, rbAnswerB, rbAnswerC, 
												rbAnswerD);
							}
						} else {
							MainFrame.getRunningFrame().setStatusErrorMsg(
									"Your must write atleast one answer. Answers must be 1-200 characters."
											+ " (invalid answers)", pnlMultA);
						}
					}
				}				
			}		
		});
		
		btnNewQBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				cardsQPanel.show(pnlQuestionEdit, STEP_1);
			}
		});
		
		btnModify.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				setQuestionAddingPanel();
			}			
		});
		
		// Action listener to show the correct pane on next screen
		ActionListener rbClick = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (ae.getSource() == rbMultChoice){
					cardQType.show(pnlQTypeSwap, TYPE_MULTI);
					shortAns = false;
				} else if (ae.getSource() == rbShortAnswer) {
					cardQType.show(pnlQTypeSwap, TYPE_SHORT);
					shortAns = true;
				}
			}
		};
		
		rbMultChoice.addActionListener(rbClick);
		rbShortAnswer.addActionListener(rbClick);
		
		clWeek = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent ce) {
				currentWeek = (Integer)spnWeek.getValue();
				currentQuestionNumber = 1;

				setQuestionSpinner(currentQuestionNumber, Bonus.getNumQuestionsInWeek(currentWeek));
				
				try {
					bq = Bonus.getQuestion(currentWeek, currentQuestionNumber - 1);
				} catch (IndexOutOfBoundsException e){
					bq = null;
				}
				
				addQuestionToListing(bq);
				
				if (currentWeek == GameData.getCurrentGame().getCurrentWeek())
					btnModify.setEnabled(true);
				else btnModify.setEnabled(false);
			}			
		};
		
		spnWeek.addChangeListener(clWeek);
		
		clQuestion = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent ce) {
				currentQuestionNumber = (Integer)spnQuestion.getValue();
				
				try {
					bq = Bonus.getQuestion(currentWeek, currentQuestionNumber - 1);
				} catch (IndexOutOfBoundsException e) {
					bq = null;
				}
				
				addQuestionToListing(bq);
			}
		};
		
		spnQuestion.addChangeListener(clQuestion);
	}

	@Override
	public void update(Observable observ, Object obj) {
		GameData g = (GameData)observ;
		
		if (obj.equals(EnumSet.of(UpdateTag.START_SEASON))){
			setQuestionAddingPanelEditable(true);
			currentWeek = g.getCurrentWeek();
			currentQuestionNumber = 1;
			setWeekSpinner(currentWeek, g.getCurrentWeek());
			setQuestionSpinner(currentQuestionNumber, Bonus.getNumQuestionsInWeek(currentWeek));
		}
		if (obj.equals(EnumSet.of(UpdateTag.ADVANCE_WEEK))){
			currentWeek = g.getCurrentWeek();
			currentQuestionNumber = 1;
			setWeekSpinner(currentWeek, g.getCurrentWeek());
			setQuestionSpinner(1, 1);
			txtQuestionList.setText("");
		}
	}
}
