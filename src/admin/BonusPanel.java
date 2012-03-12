package admin;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class BonusPanel extends JPanel{

	JLabel lblBonusInfo = new JLabel("Bonus info.");
	
	JRadioButton rbMultChoice = new JRadioButton("Multiple Choice");
	JRadioButton rbShortAnswer = new JRadioButton("Short Answer");
	JTextField txtQuestion = new JTextField("");
	
	public BonusPanel(){
		initPnlBonus();
		initPnlAnswer();
	}
	
	private void initPnlBonus(){
		JPanel pnlBonus = new JPanel();
		JPanel pnlMC = new JPanel();
		
		pnlBonus.setLayout(new BorderLayout());
		pnlMC.setLayout(new BoxLayout(pnlMC, BoxLayout.Y_AXIS));
	
		ButtonGroup group = new ButtonGroup();
		group.add(rbMultChoice);
		group.add(rbShortAnswer);
		
		rbMultChoice.setAlignmentY(Component.CENTER_ALIGNMENT);
		rbShortAnswer.setAlignmentY(Component.CENTER_ALIGNMENT);
		pnlMC.add(rbMultChoice);
		pnlMC.add(rbShortAnswer);
	
		
		JLabel lblQuestion = new JLabel("Question: ");
		txtQuestion.setSize(150, 150);
		
		pnlBonus.add(pnlMC,BorderLayout.EAST);
		pnlBonus.add(lblQuestion,BorderLayout.WEST);
		pnlBonus.add(txtQuestion,BorderLayout.CENTER);
		
		
		this.add(pnlBonus);
	}
	
	private void initPnlAnswer(){
		JPanel pnlAnswer = new JPanel(); //added panel just incase we need to add anything else
		JLabel lblAnswer = new JLabel("Answer something(Couldn't read writing).");
		
		
		pnlAnswer.add(lblAnswer);
		pnlAnswer.setPreferredSize(new Dimension(480,200));
		this.add(pnlAnswer);
	}	
}
