package admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import data.GameData;

public class BonusPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	JPanel pnlNewQ = new JPanel();
	JPanel pnlListQ = new JPanel();
	JPanel pnlTypeQ = new JPanel();
	JPanel pnlViewWeek = new JPanel();
	
	JTextField txtQuestion = new JTextField("");
	JRadioButton rbMultChoice = new JRadioButton("Multiple Choice");
	JRadioButton rbShortAnswer = new JRadioButton("Short Answer");
	JButton btnNext = new JButton("Next");
	
	JLabel lblViewWeek = new JLabel("View Week:");
	
	SpinnerNumberModel weekModel = new SpinnerNumberModel(1, 1, 1, 1); // default,low,min,step
	JSpinner spnWeek = new JSpinner(weekModel);
	
	JTextField txtAnswer = new JTextField("");
	JButton btnBack = new JButton("Back");
	JButton btnSubmit = new JButton("Submit");
	
	public BonusPanel() {
		initPnlBonus();
		initListeners();
	}

	private void initPnlBonus() {
		this.setLayout(new BorderLayout());
		pnlNewQ.setLayout(new GridLayout(1, 4, 50, 20));
		pnlListQ.setLayout(new BorderLayout());
		pnlTypeQ.setLayout(new BorderLayout());
		
		//build question adding panel
		txtQuestion.setBorder(BorderFactory.createTitledBorder("Question"));
		txtQuestion.setPreferredSize(new Dimension(300, 100));
		
		ButtonGroup group = new ButtonGroup();
		group.add(rbMultChoice);
		group.add(rbShortAnswer);

		rbMultChoice.setAlignmentY(Component.CENTER_ALIGNMENT);
		rbShortAnswer.setAlignmentY(Component.CENTER_ALIGNMENT);
		pnlTypeQ.add(rbMultChoice, BorderLayout.WEST);
		pnlTypeQ.add(rbShortAnswer, BorderLayout.EAST);
		pnlTypeQ.add(btnNext, BorderLayout.SOUTH);
		
		pnlNewQ.add(txtQuestion);
		pnlNewQ.add(pnlTypeQ);
		
		//build listing information panel
		pnlListQ.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		pnlViewWeek.add(lblViewWeek);
		pnlViewWeek.add(spnWeek);
		
		pnlListQ.add(pnlViewWeek, BorderLayout.NORTH);
		
		this.add(pnlNewQ, BorderLayout.NORTH);
		this.add(pnlListQ, BorderLayout.SOUTH);
	}
	
	private void initListeners(){
		btnNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
}
