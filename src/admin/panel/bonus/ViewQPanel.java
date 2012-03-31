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
	private JLabel lblAnswer = new JLabel("for");
	private JLabel lblChoicesStr = new JLabel("",JLabel.RIGHT);
	private JLabel lblChoices = new JLabel("");
	
	public ViewQPanel(){
		this.setPreferredSize(new Dimension(100,100));
		this.setLayout(new GridLayout(7,2,5,1));
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
		this.add(lblChoicesStr);
		this.add(lblChoices);
	}
	
	public void updateLabels(BonusQuestion q) {
		lblWeek.setText(""+q.getWeek());
		lblNum.setText(""+q.getNumber());
		lblType.setText(""+q.getBonusType());
		lblQuestion.setText(""+q.getPrompt());
		lblAnswer.setText(""+q.getAnswer());
		if(q.getBonusType()==BonusQuestion.BONUS_TYPE.MULTI){
			lblChoicesStr.setText("Choices: ");
			String[] choices = q.getChoices();
			String s = "";
			for(String c: choices){
				s+=c+", ";
			}
			lblChoices.setText(s);
		}else{
			lblChoicesStr.setText("");
			lblChoices.setText("");
		}
	}
}
