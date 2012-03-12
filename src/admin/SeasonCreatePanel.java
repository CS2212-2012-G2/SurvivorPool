package admin;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;

import common.Utils;

import admin.data.GameData;
import admin.data.JSONUtils;

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
	
	private JPanel innerFieldPanel;
	private JPanel innerFieldPanel2;
	
	public SeasonCreatePanel(){
		this.setPreferredSize(new Dimension(300,200));
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		innerFieldPanel = new JPanel();
		innerFieldPanel.setLayout(new MigLayout());
	
		innerFieldPanel2 = new JPanel();
		innerFieldPanel2.setLayout(new BorderLayout());
		
		JLabel lblWelcomeBanner = new JLabel("Welcome to SurvivorPool!");
		JLabel lblInfoBanner = new JLabel("Fill out the fields below to create a new season.");
		JLabel lblWeeks = new JLabel("Weeks:", SwingConstants.TRAILING);
		JLabel lblContestants = new JLabel("Contestants:", SwingConstants.TRAILING);
		JLabel lblTribe1 = new JLabel("Tribe 1:", SwingConstants.TRAILING);
		JLabel lblTribe2 = new JLabel("Tribe 2:", SwingConstants.TRAILING);		
		
		lblWelcomeBanner.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeBanner.setFont(new Font("sanserif", Font.BOLD, 34));
		lblInfoBanner.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoBanner.setFont(new Font("sanserif", Font.ITALIC, 16));
		
		//is there a default number of weeks?
		SpinnerNumberModel weekModel = new SpinnerNumberModel(3,3,12,1); //default,low,min,step
		spnWeek = new JSpinner(weekModel);
		
		SpinnerNumberModel contestantModel = new SpinnerNumberModel(6,6,15,1);//default,low,min,step
		spnContestant = new JSpinner(contestantModel);
		
		
		txtTribe1 = new JTextField("");
		txtTribe2 = new JTextField("");
						
		JButton btnCreate = new JButton("Create Season");		
		
		innerFieldPanel.add(lblWeeks);
		innerFieldPanel.add(spnWeek);
		innerFieldPanel.add(lblContestants, "gap unrelated");
		innerFieldPanel.add(spnContestant, "wrap");
		innerFieldPanel.add(lblTribe1);
		innerFieldPanel.add(txtTribe1, "span, grow");
		innerFieldPanel.add(lblTribe2);
		innerFieldPanel.add(txtTribe2, "span, grow");

		innerFieldPanel2.add(lblInfoBanner, BorderLayout.NORTH);
		innerFieldPanel2.add(innerFieldPanel, BorderLayout.CENTER);
		innerFieldPanel2.add(btnCreate, BorderLayout.SOUTH);
		
		this.add(lblWelcomeBanner, BorderLayout.NORTH);
		this.add(innerFieldPanel2, BorderLayout.CENTER);
		
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
						MainFrame.getRunningFrame().getStatusBar().setErrorMsgLabel("Invalid tribe names");
						//lblAlert.setText("Invalid tribe names!");
						return;
					}
					MainFrame.getRunningFrame().getStatusBar().setErrorMsgLabel("Valid tribe names");
					GameData.initSeason(Integer.parseInt(spnContestant.getValue().toString()));
					GameData.getCurrentGame().setTribeNames(txtTribe1.getText(),txtTribe2.getText());
					
					MainFrame.seasonCreated();
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
	 * @return boolean depending if tribe names are alphanumber and between 1-30 characters
	 */
	private boolean checkValidTribeNames(){
		String pattern = "^[a-zA-Z\\s]{1,30}$";;//regex for alphanumeric and between 1-30 characters long
		return Utils.checkString(txtTribe1.getText(),pattern)
				&&Utils.checkString(txtTribe2.getText(),pattern);
	}
	
}
