package admin.panel.bonus;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import data.bonus.BonusQuestion;

public class ViewQPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel lblWeek = new JLabel("not");
	private JLabel lblNum = new JLabel("initialized");
	private JLabel lblType = new JLabel("at ");
	private JLabel lblQuestion = new JLabel("start");
	private JLabel lblAnswer = new JLabel("for some reason");
	
	public ViewQPanel(){
		this.setPreferredSize(new Dimension(100,100));
		this.setLayout(new GridLayout(5,2,5,5));
		this.add(new JLabel("Week: ",JLabel.RIGHT));
		this.add(lblWeek);
		this.add(new JLabel("Question #: ",JLabel.RIGHT));
		this.add(lblNum);
		this.add(new JLabel("Question Type: ",JLabel.RIGHT));
		this.add(lblType);
		this.add(new JLabel("Question: ",JLabel.RIGHT));
		this.add(lblQuestion);
		this.add(new JLabel("Answer: ",JLabel.RIGHT));
		this.add(lblAnswer);
	}
	
	public void updateLabels(BonusQuestion q) {
		//System.out.println("yeah");
		lblWeek.setText(""+q.getWeek());
		lblNum.setText(""+q.getNumber());
		lblType.setText(""+q.getBonusType());
		lblQuestion.setText(""+q.getPrompt());
		lblAnswer.setText(""+q.getAnswer());
	}
}
