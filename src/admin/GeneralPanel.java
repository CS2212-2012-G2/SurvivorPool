package admin;
//TODO: MAKE THIS PANEL LOOK BETTER!
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import common.Utils;

import data.GameData;




public class GeneralPanel extends JPanel {

	JLabel lblGenInfo = new JLabel("General infos.");
	JButton btnAdvWeek = new JButton("Start season");
	JLabel lblBonusInfo = new JLabel("Bonus info.");
	
	JRadioButton rbMultChoice = new JRadioButton("Multiple Choice");
	JRadioButton rbShortAnswer = new JRadioButton("Short Answer");
	JTextField txtQuestion = new JTextField("");
	
	GameData gd;
	
	public GeneralPanel(){
		gd = GameData.getCurrentGame();
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		initPnlInfo();
		initPnlBonus();
		initPnlAnswer();
		initListeners();
		btnAdvWeek.setVisible(!GameData.getCurrentGame().getSeasonEnded());
	}
	
	private void initPnlInfo(){
		JPanel pnlInfo = new JPanel(new BorderLayout());
		JPanel pnlGenInfo = new JPanel();
				
		pnlGenInfo.setLayout(new BorderLayout());
		pnlGenInfo.add(lblGenInfo,BorderLayout.CENTER);
		pnlGenInfo.add(btnAdvWeek,BorderLayout.SOUTH);
		
		if(GameData.getCurrentGame().getSeasonStarted())
			btnAdvWeek.setText("Advance Week");
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,pnlGenInfo,lblBonusInfo);
		splitPane.setDividerSize(1);
		splitPane.setDividerLocation(320);
		
		//TODO: actually set info here rather than just weeks.
		lblGenInfo.setText("<html>"+Integer.toString(gd.weeksLeft())+" weeks left. File -> Reset to start new season</html>");
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

	private void initListeners(){
		btnAdvWeek.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!GameData.getCurrentGame().getSeasonStarted()){
					//TODO: implement weekly bet amount
					System.out.println("Need to implement weekly bet amount.");
					
					String s=JOptionPane.showInputDialog("Enter weekly bet amount!This does not do anything at the moment");
					if(Utils.checkString(s, "^[0-9]+$")){
						if(Integer.parseInt(s)!=0){
							GameData.getCurrentGame().startSeason();
							MainFrame.getRunningFrame().seasonStarted();
							btnAdvWeek.setText("Advance Week");
						}
					}
						
				}else{
					GameData.getCurrentGame().advanceWeek();
					lblGenInfo.setText("<html>"+Integer.toString(gd.weeksLeft())+" weeks left. File -> Reset to start new season</html>");
					btnAdvWeek.setVisible(!GameData.getCurrentGame().getSeasonEnded());
				}
			}
			
		});
	}
}
