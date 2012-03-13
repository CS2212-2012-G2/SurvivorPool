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
	
	private JLabel tabLabel;
	private JLabel msgLabel;
	private Timer t=null;
	private Component[] errorComps = new Component[0];
	
	private static int TIMER_TIME = 3500;
	
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
	public void setErrorMsgLabel(String txt, Component... comps){
		if(t!=null&&t.isRunning()){
			t.stop();
			System.out.println("yeah");
		}else{
			t= new Timer(TIMER_TIME,displayError);
			t.setRepeats(false);
		}
		
		setMsgLabel(txt);
		msgLabel.setForeground(Color.white);
		this.setBackground(Color.red);
		
		errorComps=comps;
		for (Component c: comps)
			if (c != null)
				c.setBackground(Color.RED);
	
		t.start();
	}
	
	private void resetColor(){
		for (Component c: errorComps)
			if (c != null)
				c.setBackground(AdminUtils.getThemeBG());
		
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
