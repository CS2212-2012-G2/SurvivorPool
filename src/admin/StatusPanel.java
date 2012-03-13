/**
 * 
 */
package admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * @author kevin
 *
 */
public class StatusPanel extends JPanel{
	
	JLabel tabLabel;
	JLabel msgLabel;
	Timer t=null;
	Component errorComp;
	
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
		if(t==null||!t.isRunning()){
			resetColor();
			msgLabel.setText(txt);
		}
	}
	
	/**
	 * Display an error message. Makes panel red
	 * @param txt Error text to display
	 * @param c A component to make the background red(can pass null if none needed)
	 */
	public void setErrorMsgLabel(String txt,Component c){
		if(t!=null&&t.isRunning()){
			t.stop();
			System.out.println("yeah");
		}else{
			t= new Timer(2000,displayError);
			t.setRepeats(false);
		}
		
		setMsgLabel(txt);
		msgLabel.setForeground(Color.white);
		this.setBackground(Color.red);
		errorComp=c;
		if(c!=null)
			c.setBackground(Color.red);
		
		t.start();
	}
	
	private void resetColor(){
		if(errorComp!=null)
			errorComp.setBackground(AdminUtils.getThemeBG());
		msgLabel.setForeground(AdminUtils.getThemeFG());
		this.setBackground(AdminUtils.getThemeBG());
	}
	
	Action displayError = new AbstractAction(){

		@Override
		public void actionPerformed(ActionEvent e) {
			resetColor();
			
		}
		
	};
}
