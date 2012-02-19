package admin;
//TODO: MAKE THIS PANEL LOOK BETTER!
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import common.Utils;

import data.GameData;

public class GeneralPanel extends JPanel {

	JLabel lblGenInfo = new JLabel("General infos.");
	JButton btnAdvWeek = new JButton("Start season");
	JTextField txtTribe1 = new JTextField();
	JTextField txtTribe2 = new JTextField();
	JButton btnChangeTribeName = new JButton("Change Tribe Name");
	
	public GeneralPanel(){
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		initPnlInfo();
		initListeners();
		btnAdvWeek.setVisible(!GameData.getCurrentGame().getSeasonEnded());
	}
	
	private void initPnlInfo(){
		JPanel pnlInfo = new JPanel(new BorderLayout());
		JPanel pnlGenInfo = new JPanel();
<<<<<<< HEAD
		JPanel pnlTribes = new JPanel();
		
=======
				
>>>>>>> Changed formatting of general info tab in Admin GUI.
		pnlGenInfo.setLayout(new BorderLayout());
		txtTribe1.setText(GameData.getCurrentGame().getTribeNames()[0]);
		txtTribe2.setText(GameData.getCurrentGame().getTribeNames()[1]);
		
		pnlGenInfo.add(lblGenInfo,BorderLayout.CENTER);
		pnlGenInfo.add(btnAdvWeek,BorderLayout.SOUTH);
		
<<<<<<< HEAD
		if(GameData.getCurrentGame().getSeasonStarted())
			btnAdvWeek.setText("Advance Week");

		//TODO: actually set info here rather than just weeks.
		lblGenInfo.setText("<html>"+Integer.toString(GameData.getCurrentGame().weeksLeft())+" weeks left. File -> Reset to start new season</html>");
		pnlInfo.add(pnlGenInfo,BorderLayout.CENTER);
		pnlInfo.setPreferredSize(new Dimension(450,400));
=======
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
>>>>>>> Changed formatting of general info tab in Admin GUI.
		
		pnlTribes.add(txtTribe1);
		pnlTribes.add(txtTribe2);
		pnlTribes.add(btnChangeTribeName);
		
		this.add(pnlInfo);
		this.add(pnlTribes);

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
							return;
						}
						return;
					}
					JOptionPane.showMessageDialog(null, "Invalid amount entered.");
						
				}else{
					GameData.getCurrentGame().advanceWeek();
					lblGenInfo.setText("<html>"+Integer.toString(GameData.getCurrentGame().weeksLeft())+" weeks left. File -> Reset to start new season</html>");
					btnAdvWeek.setVisible(!GameData.getCurrentGame().getSeasonEnded());
				}
			}
			
		});
		
		btnChangeTribeName.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!Utils.checkString(txtTribe1.getText(),Utils.TRIBE_PATTERN)){
					MainFrame.getRunningFrame().setStatusErrorMsg("Tribe 1 name invalid.",txtTribe1);
				}else if(!Utils.checkString(txtTribe2.getText(),Utils.TRIBE_PATTERN)){
					MainFrame.getRunningFrame().setStatusErrorMsg("Tribe 2 name invalid.",txtTribe2);
				}else{
					GameData.getCurrentGame().setTribeNames(txtTribe1.getText(), txtTribe2.getSelectedText());
					MainFrame.getRunningFrame().setStatusMsg("Tribes changed.");
					//TODO: make tribe name change other panels as well(Contestant jcombobox is not updated)
				}		
			}
			
		});
	}
<<<<<<< HEAD
=======
	
	private void initPnlAnswer(){
		JPanel pnlAnswer = new JPanel(); //added panel just incase we need to add anything else
		JLabel lblAnswer = new JLabel("Answer something(Couldn't read writing).");
		
		
		pnlAnswer.add(lblAnswer);
		pnlAnswer.setPreferredSize(new Dimension(480,200));
		this.add(pnlAnswer);
	}	
>>>>>>> Changed formatting of general info tab in Admin GUI.
}
