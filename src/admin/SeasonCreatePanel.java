package admin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
	
	JLabel lblAlert;
	
	private FileWriter fileWrite = null; // I/O
	private BufferedWriter buffWrite = null;

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
		
		lblAlert = new JLabel("");
		
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
		this.add(lblAlert);
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
					if(!checkValidTribeNames()){
						lblAlert.setText("Invalid tribe names!");
						return;
					}
					lblAlert.setText("valid tribe names!");
					fileWrite = new FileWriter(
							"src/data/SeasonSettings", false);
					buffWrite = new BufferedWriter(fileWrite);
					 String tempString = spnContestant.getValue().toString();
					buffWrite.write("Number_Of_Contestants: " + tempString); // first line
					buffWrite.newLine();
					tempString = spnWeek.getValue().toString();
					buffWrite.write("Number_Of_Weeks: " + tempString); // second line
					buffWrite.newLine();
					buffWrite.write("Tribe_1_Name: " + txtTribe1.getText()); // 3rd line
					buffWrite.newLine();
					buffWrite.write("Tribe_2_Name: " + txtTribe2.getText()); // 4th line
					buffWrite.newLine();
					buffWrite.close(); // close the file
					//TODO:Go to next panel
					
				} catch (Exception i) {
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
	 * @return boolean depending if tribe names are alphanumber and between 1-30 characters
	 */
	private boolean checkValidTribeNames(){
		String pattern = "\\w{1,30}";//regex for alphanumeric and between 1-30 characters long
		return checkString(txtTribe1.getText(),pattern)
				&&checkString(txtTribe2.getText(),pattern);
	}
	
	/**
	 * Checks if string matches pattern.
	 * @param val The string to check for validity
	 * @param pattern A regex pattern that has all possible valid values
	 * @return true if string matches pattern
	 */
	private boolean checkString(String val,String pattern){
		if(val==null)
			return false;
		if(val.length()==0)
			return false;
		return Pattern.matches(pattern, val);
	}
}
