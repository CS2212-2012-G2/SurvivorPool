/**
 * 
 */
package admin;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author kevin
 *
 */
public class StatusPanel extends JPanel {
	
	JLabel tabLabel;
	JLabel msgLabel;
	
	public StatusPanel() {
		setLayout(new BorderLayout(5, 5));
		
		tabLabel = new JLabel();
		msgLabel = new JLabel();
		
		add(tabLabel, BorderLayout.LINE_START);
		add(msgLabel, BorderLayout.LINE_END);
	}
	
	/**
	 * Set the tab(General,Contestant,user,..) label
	 * @param txt The tab's name
	 */
	public void setTabLabel(String txt) {
		tabLabel.setText(txt);
	}
	
	/**
	 * Display message to user. For error messages see setErrorMsgLabel
	 * @param txt text to display
	 */
	public void setMsgLabel(String txt) {
		this.setBackground(AdminUtils.getThemeBG());
		msgLabel.setText(txt);
	}
	
	/**
	 * Display message to user and change color to RED
	 * @param txt Error text to display
	 */
	public void setErrorMsgLabel(String txt){
		setMsgLabel(txt);
		msgLabel.setForeground(Color.white);
		this.setBackground(Color.red);
	}
}
