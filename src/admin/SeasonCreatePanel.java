package admin;
//TODO: MAKE THIS PANEL LOOK BETTER!
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import admin.data.GameData;

import common.Utils;

/**
 * Screen for creating a season. Controls the number of contestants and weeks
 * and the two tribes name.
 * @author CS2212 GROUP TWO 2011/2012
 *
 */
public class SeasonCreatePanel extends JPanel {

	JSpinner spnWeek;
	JSpinner spnContestant;
	boolean programChange = false; //true when spinner value changed by program
	
	JTextField txtTribe1;
	JTextField txtTribe2;
	
	public SeasonCreatePanel(){
		this.setPreferredSize(new Dimension(400,400));
		this.setLayout(new GridLayout(6,2));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JLabel lblInitSeason = new JLabel("Create Season");
		JLabel lblFiller = new JLabel("");
		JLabel lblWeeks = new JLabel("Weeks:");
		JLabel lblContestants = new JLabel("Contestants:");
		JLabel lblTribe1 = new JLabel("Tribe 1:");
		JLabel lblTribe2 = new JLabel("Tribe 2:");
		
		
		//is there a default number of weeks?
		SpinnerNumberModel weekModel = new SpinnerNumberModel(3,3,12,1); //default,low,min,step
		spnWeek = new JSpinner(weekModel);
		
		SpinnerNumberModel contestantModel = new SpinnerNumberModel(6,6,15,1);//default,low,min,step
		spnContestant = new JSpinner(contestantModel);
		
		
		txtTribe1 = new JTextField("");
		txtTribe2 = new JTextField("");
		
		
		
		JButton btnCreate = new JButton("Create Season");
		
		this.add(lblInitSeason);
		this.add(lblFiller);
		this.add(lblWeeks);
		this.add(spnWeek);
		this.add(lblContestants);
		this.add(spnContestant);
		this.add(lblTribe1);
		this.add(txtTribe1);
		this.add(lblTribe2);
		this.add(txtTribe2);
		this.add(btnCreate);
		
		spnWeek.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent ce) {
				JSpinner spn = (JSpinner) ce.getSource();
				if(!programChange) //makes sure that the code did not change the value
					changeSpinnerValue(spn);				
			}
			
		});
		spnContestant.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent ce) {
				JSpinner spn = (JSpinner) ce.getSource();
				if(!programChange)
					changeSpinnerValue(spn);	
			}
			
		});
		
		btnCreate.addActionListener(new ActionListener(){

			//write the number of contestants and weeks to the file.
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					int response = checkValidTribeNames();
					if(response==1){
						MainFrame.getRunningFrame().setErrorMsg("Invalid tribe names",txtTribe1);
					}else if(response == 2){
						MainFrame.getRunningFrame().setErrorMsg("Invalid tribe names",txtTribe2);
					}else{
						MainFrame.getRunningFrame().setErrorMsg("Valid tribe names",null);
						GameData.initSeason(Integer.parseInt(spnContestant.getValue().toString()));
						GameData.getCurrentGame().setTribeNames(txtTribe1.getText(),txtTribe2.getText());
						MainFrame.seasonCreated();
					}
				} catch (Exception i) {
					i.printStackTrace();
				}
				
			}
			
		});
	}
	
	/**
	 * Changes the other spinner value.
	 * @param spn The JSpinner that called this function. 
	 */
	private void changeSpinnerValue(JSpinner spn){
		programChange=true;
		if(spn.equals(spnContestant))
			spnWeek.setValue(Integer.parseInt(spnContestant.getValue().toString())-3);
		else
			spnContestant.setValue(Integer.parseInt(spnWeek.getValue().toString())+3);
		programChange=false;
	}

	/**
	 * Checks if the tribe names are valid according to specifications.
	 * @return 0 if both names are valid, 1 if tribe 1 is wrong, and 2 if tribe 2 name is wrong
	 */
	private int checkValidTribeNames(){
		//regex for alphanumeric and between 1-30 characters long
		if(!Utils.checkString(txtTribe1.getText(),Utils.TRIBE_PATTERN))
			return 1;
		else if(!Utils.checkString(txtTribe2.getText(),Utils.TRIBE_PATTERN))
			return 2;
		else
			return 0;
	}
	
}
