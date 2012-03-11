/**
 * 
 */
package admin;

import java.awt.BorderLayout;

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
	
	public void setTabLabel(String txt) {
		tabLabel.setText(txt);
	}
	
	public void setMsgLabel(String txt) {
		msgLabel.setText(txt);
	}
}
