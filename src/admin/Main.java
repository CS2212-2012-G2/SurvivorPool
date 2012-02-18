package admin;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Main extends JFrame{

	private JMenuBar menuBar = new JMenuBar();
	private JMenu mnuFile = new JMenu("File");
	private JMenu mnuTheme = new JMenu("Theme");
	
	private JMenuItem mnuItemExit;
	private JMenuItem mnuItemSunset;
	private JMenuItem mnuItemJungle;
	private JMenuItem mnuItemUnderwater;

	private class AL implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource() == mnuItemExit) {
				System.exit(0);
			} else if (ae.getSource() == mnuItemSunset) {
				System.out.println("Need to implement themes");
			}else if (ae.getSource() == mnuItemJungle) {
				System.out.println("Need to implement themes");
			}else if (ae.getSource() == mnuItemUnderwater) {
				System.out.println("Need to implement themes");
			}

		}

	}

	ActionListener al = new AL();
	
	public Main(){
		initGUI();
			
		this.setSize(640, 480);
		this.setVisible(true);
		this.setTitle("Survivor Pool Admin");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void initGUI(){
		JTabbedPane tabPane = new JTabbedPane();
		
		//can do this by setting a jlabel as a tab and changing its prefered size
		//<html><body leftmargin=30 topmargin=8 marginwidth=50 marginheight=5>General</body></html>
		
		tabPane.addTab("<html><body><table width='150'>General</table></body></html>",new GeneralPanel());
		tabPane.addTab("Player",new PlayerPanel());
		tabPane.addTab("Users", new UserPanel());
		tabPane.setBackground(Color.cyan);//tab background color,not the panel
		
		
		mnuItemExit = new JMenuItem("Exit");
		mnuItemSunset = new JMenuItem("Sunset");
		mnuItemUnderwater = new JMenuItem("Underwater");
		mnuItemJungle = new JMenuItem("Jungle");
		
		mnuTheme.add(mnuItemSunset);
		mnuTheme.add(mnuItemUnderwater);
		mnuTheme.add(mnuItemJungle);
		
		mnuFile.add(mnuItemExit);
		
		menuBar.add(mnuFile);
		menuBar.add(mnuTheme);
		
		mnuItemExit.addActionListener(al);
		
		
		this.setJMenuBar(menuBar);
		this.add(tabPane);
	}
	
	
	public static void main(String[] args) {
		Main m = new Main();

	}

}
