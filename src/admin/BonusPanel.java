package admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
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

import data.GameData;
import data.GameData.Field;
import data.bonus.Bonus;
import data.bonus.BonusQuestion;
import data.bonus.BonusQuestion.BONUS_TYPE;

public class BonusPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	JPanel pnlNewQ = new JPanel();
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
	
	private JTextField txtQuestion;
	private JTextField txtAnswer;
	private JTextArea txtQuestionList;
	
	private JRadioButton rbMultChoice;
	private JRadioButton rbShortAnswer;
	
	private ButtonGroup group;
	private ButtonGroup group2;
	
	private String question;
	private String answer;
	
	private JTextField txtAnswerA;
	private JTextField txtAnswerB;
	private JTextField txtAnswerC;
	private JTextField txtAnswerD;
	
	private JRadioButton rbAnswerA;
	private JRadioButton rbAnswerB;
	private JRadioButton rbAnswerC;
	private JRadioButton rbAnswerD;
	
	private Boolean shortAns;
	private Boolean modifyBonusQuestion = false;
	
	private String currentQuestion;
	
	private BonusQuestion bq;
	
	private int currentWeek;
	private int currentQuestionNumber;
	
	private ChangeListener clWeek;
	private ChangeListener clQuestion;
	
	public BonusPanel() {
		this.setLayout(new BorderLayout());
		currentQuestion = "";
		initPnlAddQuestion();
		initPnlQuestionListing();
		
		if (Bonus.getAllQuestions().isEmpty()){
			setQuestionAddingPanelUneditable();
			btnModify.setEnabled(false);
		} else {
			initExistingBonus();
		}

		initListeners();
		GameData.getCurrentGame().addObserver(this);
	}

	private void initPnlAddQuestion() {
		pnlNewQ.removeAll();
		pnlTypeQ.removeAll();
		
		pnlNewQ.setLayout(new GridLayout(1, 4, 50, 20));
		pnlTypeQ.setLayout(new BorderLayout());
		
		txtQuestion = new JTextField("");
		txtQuestion.setBorder(BorderFactory.createTitledBorder("Question"));
		txtQuestion.setPreferredSize(new Dimension(300, 100));
		
		group = new ButtonGroup();
		group.add(rbMultChoice);
		group.add(rbShortAnswer);
		
		rbMultChoice = new JRadioButton("Multiple Choice");
		rbShortAnswer = new JRadioButton("Short Answer");
		
		rbMultChoice.setAlignmentY(Component.CENTER_ALIGNMENT);
		rbShortAnswer.setAlignmentY(Component.CENTER_ALIGNMENT);
		pnlTypeQ.add(rbMultChoice, BorderLayout.WEST);
		pnlTypeQ.add(rbShortAnswer, BorderLayout.EAST);
		pnlTypeQ.add(btnNext, BorderLayout.SOUTH);
		
		pnlNewQ.add(txtQuestion);
		pnlNewQ.add(pnlTypeQ);

		rbAnswerA = new JRadioButton();
		rbAnswerB = new JRadioButton();
		rbAnswerC = new JRadioButton();
		rbAnswerD = new JRadioButton();
		
		this.add(pnlNewQ, BorderLayout.NORTH);
		this.repaint();
		this.revalidate();
	}
	
	private void initPnlAddShortAnswer() {
		pnlNewQ.removeAll();
		pnlTypeQ.removeAll();
		
		pnlNewQ.setLayout(new GridLayout(1, 2, 50, 20));
		
		txtAnswer = new JTextField("");
		txtAnswer.setBorder(BorderFactory.createTitledBorder("Answer"));
		txtAnswer.setPreferredSize(new Dimension(300, 100));
		
		pnlTypeQ.setLayout(new GridLayout(2, 1, 50, 20));
		pnlTypeQ.add(btnBack);
		pnlTypeQ.add(btnSubmit);
		
		pnlNewQ.add(txtAnswer);
		pnlNewQ.add(pnlTypeQ);

		this.add(pnlNewQ, BorderLayout.NORTH);
		this.repaint();
		this.revalidate();
	}
	
	private void initPnlAddMultipleAnswer() {
		pnlNewQ.removeAll();
		pnlTypeQ.removeAll();
		pnlMultA.removeAll();
		
		pnlNewQ.setLayout(new GridLayout(1, 2, 50, 20));
		
		pnlMultA.setLayout(new GridLayout(4, 2));
		pnlMultA.setBorder(BorderFactory.createTitledBorder("Answers"));
		
		group2 = new ButtonGroup();
		rbAnswerA = new JRadioButton("A");
		rbAnswerB = new JRadioButton("B");
		rbAnswerC = new JRadioButton("C");
		rbAnswerD = new JRadioButton("D");
		group2.add(rbAnswerA);
		group2.add(rbAnswerB);
		group2.add(rbAnswerC);
		group2.add(rbAnswerD);
		
		txtAnswerA = new JTextField("");
		txtAnswerB = new JTextField("");
		txtAnswerC = new JTextField("");
		txtAnswerD = new JTextField("");
		
		pnlMultA.add(rbAnswerA);
		pnlMultA.add(txtAnswerA);
		pnlMultA.add(rbAnswerB);
		pnlMultA.add(txtAnswerB);
		pnlMultA.add(rbAnswerC);
		pnlMultA.add(txtAnswerC);
		pnlMultA.add(rbAnswerD);
		pnlMultA.add(txtAnswerD);
		
		pnlTypeQ.setLayout(new GridLayout(2, 1, 50, 20));
		pnlTypeQ.add(btnBack);
		pnlTypeQ.add(btnSubmit);
		
		pnlNewQ.add(pnlMultA);
		pnlNewQ.add(pnlTypeQ);

		this.add(pnlNewQ, BorderLayout.NORTH);
		this.repaint();
		this.revalidate();
	}
	
	private void initPnlQuestionListing() {		
		pnlListQ.setLayout(new BorderLayout());
		pnlListWeeks.setPreferredSize(new Dimension(640, 200));
		
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
		
		this.add(pnlListQ, BorderLayout.SOUTH);
	}
	
	private void addQuestionToListing(BonusQuestion q) {
		currentQuestion = 
			"Week: " + "\t\t" + q.getWeek() + "\n" + 
			"Question #: " + "\t\t" + q.getNumber() + "\n" +
			"Question Type: " + "\t\t" + q.getBonusType() + "\n" + 
			"Question: " + "\t\t" + q.getPrompt() + "\n" + 
			"Answer: " + "\t\t" + q.getAnswer() + "\n\n";
		txtQuestionList.setText(currentQuestion);
	}
	
	private void setWeekSpinner(int week, int question) {
		spnWeek.removeChangeListener(clWeek);
		weekModel.setMaximum(week);
		weekModel.setValue(question);
		spnWeek.addChangeListener(clWeek);
	}
	
	private void setQuestionSpinner(int qValue, int qMax) {
		spnQuestion.removeChangeListener(clQuestion);
		snmQuestion.setValue(qValue);
		snmQuestion.setMaximum(qMax);
		spnQuestion.addChangeListener(clQuestion);
	}
	
	private void setQuestionAddingPanelUneditable(){
		txtQuestion.setEnabled(false);
		rbMultChoice.setEnabled(false);
		rbShortAnswer.setEnabled(false);
		btnNext.setEnabled(false);
	}
	
	private void setQuestionAddingPanelEditable(){
		txtQuestion.setEnabled(true);
		rbMultChoice.setEnabled(true);
		rbShortAnswer.setEnabled(true);
		btnNext.setEnabled(true);
	}
	
	private void setQuestionAddingPanel(){
		setQuestionAddingPanelEditable();
		txtQuestion.setText(bq.getPrompt());
		modifyBonusQuestion = true;
	}
	
	private void setAnswerAddingPanel(int week, int num){
		if (rbMultChoice.isSelected() && bq.getChoices() != null){
			txtAnswerA.setText(bq.getChoices()[0]);
			txtAnswerB.setText(bq.getChoices()[1]);
			txtAnswerC.setText(bq.getChoices()[2]);
			txtAnswerD.setText(bq.getChoices()[3]);
		} else if (rbShortAnswer.isSelected() && bq.getChoices() == null){
			txtAnswer.setText(bq.getAnswer());
		}
	}
	
	private String getMultiAnswer(){
		if (rbAnswerA.isSelected()){
			return txtAnswerA.getText();
		} else if (rbAnswerB.isSelected()){
			return txtAnswerB.getText();
		} else if (rbAnswerC.isSelected()){
			return txtAnswerC.getText();
		} else if (rbAnswerD.isSelected()){
			return txtAnswerD.getText();
		} else return null;
	}
	
	private String[] getMultiAnswerArray(){
		String[] answers = new String[4];
		if (txtAnswerA.getText().length() > 0) answers[0] = txtAnswerA.getText();
		if (txtAnswerB.getText().length() > 0) answers[1] = txtAnswerB.getText();
		if (txtAnswerC.getText().length() > 0) answers[2] = txtAnswerC.getText();
		if (txtAnswerD.getText().length() > 0) answers[3] = txtAnswerD.getText();
		return answers;
	}
	
	private Boolean getValidMultiAnswers() {
		return (txtAnswerA.getText().length() > 0 && txtAnswerA.getText().length() < 201)
		|| (txtAnswerB.getText().length() > 0 && txtAnswerB.getText().length() < 201)
		|| (txtAnswerC.getText().length() > 0 && txtAnswerC.getText().length() < 201)
		|| (txtAnswerD.getText().length() > 0 && txtAnswerD.getText().length() < 201)
		&& txtAnswerA.getText().length() < 201 && txtAnswerB.getText().length() < 201
		&& txtAnswerC.getText().length() < 201 && txtAnswerD.getText().length() < 201;
	}
	
	private void initExistingBonus() {
		currentWeek = GameData.getCurrentGame().getCurrentWeek();
		currentQuestionNumber = 1;
		bq = Bonus.getQuestionByWeekAndNumber(currentWeek, currentQuestionNumber);
		setWeekSpinner(currentWeek, currentQuestionNumber);
		snmQuestion.setMaximum(Bonus.getNumQuestionsInWeek(currentWeek));
		snmQuestion.setValue(1);
		addQuestionToListing(bq);
	}
	
	private void initListeners(){
		
		btnNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				if (txtQuestion.getText().length() > 0 && txtQuestion.getText().length() < 201){
					question = txtQuestion.getText();
					if (rbShortAnswer.isSelected()){
						shortAns = true;
						initPnlAddShortAnswer();	
						if (modifyBonusQuestion) setAnswerAddingPanel(currentWeek, currentQuestionNumber);
					} else if (rbMultChoice.isSelected()){
						shortAns = false;
						initPnlAddMultipleAnswer();
						if (modifyBonusQuestion) setAnswerAddingPanel(currentWeek, currentQuestionNumber);
					} else {
						MainFrame.getRunningFrame().setStatusErrorMsg(
								"You must select a question type."
										+ " (question type unselected)", rbShortAnswer, rbMultChoice);
					}
				} else {
					MainFrame.getRunningFrame().setStatusErrorMsg(
							"Questions must be 1-200 characters."
									+ " (invalid question)", txtQuestion);
				}
			}		
		});
		
		btnSubmit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				if (modifyBonusQuestion){
					bq.setPrompt(txtQuestion.getText());
					if (shortAns){
						if (txtAnswer.getText().length() > 0 && txtAnswer.getText().length() < 201){
							bq.setAnswer(txtAnswer.getText());
							bq.setChoices(null);
							bq.setBonusType(BONUS_TYPE.SHORT);
							initPnlAddQuestion();
							addQuestionToListing(bq);
							modifyBonusQuestion = false;
						} else {
							MainFrame.getRunningFrame().setStatusErrorMsg(
									"Your answer must be 1-200 characters."
											+ " (invalid answer)", txtAnswer);
							return;
						}
					} else {
						if (getValidMultiAnswers()){
							String[] answers = getMultiAnswerArray();	
							String a = getMultiAnswer();
							if (a != null){
								bq.setAnswer(a);
								bq.setChoices(answers);
								bq.setBonusType(BONUS_TYPE.MULTI);
								initPnlAddQuestion();
								addQuestionToListing(bq);
								modifyBonusQuestion = false;
							} else {
								MainFrame.getRunningFrame().setStatusErrorMsg(
										"You must select one correct answer."
												+ " (correct answer unselected)", rbAnswerA, rbAnswerB, rbAnswerC, rbAnswerD);
								return;
							}													
						} else {
							MainFrame.getRunningFrame().setStatusErrorMsg(
									"Your must write atleast one answer. Answers must be 1-200 characters."
											+ " (invalid answers)", pnlMultA);
							return;
						}
					}
				} else if (shortAns){
					if (txtAnswer.getText().length() > 0 && txtAnswer.getText().length() < 201){
						if (Bonus.getNumQuestionsInWeek(currentWeek) != 0) currentQuestionNumber++;					
						setQuestionSpinner(currentQuestionNumber, Bonus.getNumQuestionsInWeek(currentWeek));
						answer = txtAnswer.getText();						
						bq = new BonusQuestion(question, answer, null, 
								currentWeek, currentQuestionNumber);
						answer = txtAnswer.getText();
						initPnlAddQuestion();
						addQuestionToListing(bq);
						btnModify.setEnabled(true);
						modifyBonusQuestion = false;
					} else {
						MainFrame.getRunningFrame().setStatusErrorMsg(
								"Your answer must be 1-200 characters."
										+ " (invalid answer)", txtAnswer);
					}
				} else {
					if (getValidMultiAnswers()){
				
						String[] answers = getMultiAnswerArray();						
						String a = getMultiAnswer();
						
						if (a != null){
							if (Bonus.getNumQuestionsInWeek(currentWeek) != 0) currentQuestionNumber++;
							setQuestionSpinner(currentQuestionNumber, Bonus.getNumQuestionsInWeek(currentWeek));
							bq = new BonusQuestion(question, a, answers, 
									currentWeek, currentQuestionNumber);
							initPnlAddQuestion();
							addQuestionToListing(bq);
							btnModify.setEnabled(true);
							modifyBonusQuestion = false;
						} else {
							MainFrame.getRunningFrame().setStatusErrorMsg(
									"You must select one correct answer."
											+ " (correct answer unselected)", rbAnswerA, rbAnswerB, rbAnswerC, rbAnswerD);
							return;
						}
					} else {
						MainFrame.getRunningFrame().setStatusErrorMsg(
								"Your must write atleast one answer. Answers must be 1-200 characters."
										+ " (invalid answers)", pnlMultA);
					}
				}				
			}		
		});
		
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				initPnlAddQuestion();
				if (modifyBonusQuestion) setQuestionAddingPanel();
			}
		});
		
		rbMultChoice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				if (rbShortAnswer.isSelected()) rbShortAnswer.setSelected(false);
			}			
		});
		
		rbShortAnswer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				if (rbMultChoice.isSelected()) rbMultChoice.setSelected(false);
			}			
		});
		
		rbAnswerA.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				if (rbAnswerB.isSelected()) rbAnswerB.setSelected(false);
				else if (rbAnswerC.isSelected()) rbAnswerC.setSelected(false);
				else if (rbAnswerD.isSelected()) rbAnswerD.setSelected(false);
			}			
		});
		
		rbAnswerB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				if (rbAnswerA.isSelected()) rbAnswerA.setSelected(false);
				else if (rbAnswerC.isSelected()) rbAnswerC.setSelected(false);
				else if (rbAnswerD.isSelected()) rbAnswerD.setSelected(false);
			}			
		});
		
		rbAnswerC.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				if (rbAnswerB.isSelected()) rbAnswerB.setSelected(false);
				else if (rbAnswerA.isSelected()) rbAnswerA.setSelected(false);
				else if (rbAnswerD.isSelected()) rbAnswerD.setSelected(false);
			}			
		});
		
		rbAnswerD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				if (rbAnswerB.isSelected()) rbAnswerB.setSelected(false);
				else if (rbAnswerC.isSelected()) rbAnswerC.setSelected(false);
				else if (rbAnswerA.isSelected()) rbAnswerA.setSelected(false);
			}			
		});
		
		btnModify.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				setQuestionAddingPanel();
			}			
		});
		
		clWeek = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent ce) {
				currentWeek = (Integer)spnWeek.getValue();
				currentQuestionNumber = 1;
				bq = Bonus.getQuestionByWeekAndNumber(currentWeek, currentQuestionNumber);
				setQuestionSpinner(currentQuestionNumber, Bonus.getNumQuestionsInWeek(currentWeek));
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
				System.out.println(currentQuestionNumber);
				setQuestionSpinner(currentQuestionNumber, Bonus.getNumQuestionsInWeek(currentWeek));
				bq = Bonus.getQuestionByWeekAndNumber(currentWeek, currentQuestionNumber);
				addQuestionToListing(bq);
			}
		};
		
		spnQuestion.addChangeListener(clQuestion);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1.equals(Field.START_SEASON)){
			setQuestionAddingPanelEditable();
			currentWeek = ((GameData) arg0).getCurrentWeek();
			currentQuestionNumber = 1;
			setWeekSpinner(currentWeek, currentQuestionNumber);
			setQuestionSpinner(currentQuestionNumber, Bonus.getNumQuestionsInWeek(currentWeek));
		}
		if (arg1.equals(Field.ADVANCE_WEEK)){
			setQuestionAddingPanelEditable();
			currentWeek = ((GameData) arg0).getCurrentWeek();
			currentQuestionNumber = 1;
			setWeekSpinner(currentWeek, currentQuestionNumber);
			setQuestionSpinner(currentQuestionNumber, Bonus.getNumQuestionsInWeek(currentWeek));
		}
	}
}