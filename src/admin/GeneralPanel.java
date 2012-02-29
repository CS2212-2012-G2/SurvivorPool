package admin;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;


public class GeneralPanel extends JPanel {

	JLabel lblGenInfo = new JLabel("General infos.");
	JButton btnAdvWeek = new JButton("Advance week");
	JLabel lblBonusInfo = new JLabel("Bonus info.");
	
	JRadioButton rbMultChoice = new JRadioButton("Multiple Choice");
	JRadioButton rbShortAnswer = new JRadioButton("Short Answer");
	JTextField txtQuestion = new JTextField("");
	
	public GeneralPanel(){
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		initPnlInfo();
		initPnlBonus();
		initPnlAnswer();
	}
	
	private void initPnlInfo(){
		JPanel pnlInfo = new JPanel(new BorderLayout());
		JPanel pnlGenInfo = new JPanel();
				
		pnlGenInfo.setLayout(new BorderLayout());
		pnlGenInfo.add(lblGenInfo,BorderLayout.CENTER);
		pnlGenInfo.add(btnAdvWeek,BorderLayout.SOUTH);
		
		// TODO: GBL?
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,pnlGenInfo,lblBonusInfo);
		splitPane.setDividerSize(0);
		splitPane.setDividerLocation(320);
		
		pnlInfo.add(splitPane,BorderLayout.CENTER);
		pnlInfo.setPreferredSize(new Dimension(450,400));
		this.add(pnlInfo);
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
