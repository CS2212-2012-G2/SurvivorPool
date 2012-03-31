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
	
	private void setLabelsNull() {
		lblWeek.setText("Not Set");
		lblNum.setText("Not Set");
		lblType.setText("Not Set");
		lblQuestion.setText("Not Set");
		lblAnswer.setText("Not Set");
		lblChoicesStr.setText("");
		lblChoices.setText("");
	}
	
	public void updateLabels(BonusQuestion q) {
		if (q == null) {
			setLabelsNull();
			return;
		}
		
		lblWeek.setText(""+q.getWeek());
		lblNum.setText(""+q.getNumber());
		lblType.setText(""+q.getBonusType());
		lblQuestion.setText(""+q.getPrompt());
		lblAnswer.setText(""+q.getAnswer());
		if(q.getBonusType()==BonusQuestion.BONUS_TYPE.MULTI){
			lblChoicesStr.setText("Choices: ");
			String[] choices = q.getChoices();
			
			// build the string label
			StringBuilder sb = new StringBuilder(800);
			sb.append("<html>");
			for(String c: choices){
				if (c.equalsIgnoreCase(q.getAnswer()))
					sb.append("<i>" + c + "</i>, ");
				else
					sb.append(c + ", ");
			}
			sb.deleteCharAt(sb.lastIndexOf(","));
		//	sb.append("</html>");
			
			lblChoices.setText(sb.toString());
		}else{
			lblChoicesStr.setText("");
			lblChoices.setText("");
		}
	}
}
