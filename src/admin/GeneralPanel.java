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

import admin.Utils;

import data.GameData;
import data.Person;

public class GeneralPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	JLabel lblGenInfo = new JLabel("General infos.");
	JButton btnAdvWeek = new JButton("Start season");
	JTextField txtTribe1 = new JTextField();
	JTextField txtTribe2 = new JTextField();
	JButton btnChangeTribeName = new JButton("Change Tribe Name");

	public GeneralPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		initPnlInfo();
		initListeners();
		btnAdvWeek.setVisible(!GameData.getCurrentGame().isSeasonEnded());
	}

	private void initPnlInfo() {
		JPanel pnlInfo = new JPanel(new BorderLayout());
		JPanel pnlGenInfo = new JPanel();
		JPanel pnlTribes = new JPanel();

		pnlGenInfo.setLayout(new BorderLayout());
		txtTribe1.setText(GameData.getCurrentGame().getTribeNames()[0]);
		txtTribe2.setText(GameData.getCurrentGame().getTribeNames()[1]);

		pnlGenInfo.add(lblGenInfo, BorderLayout.CENTER);
		pnlGenInfo.add(btnAdvWeek, BorderLayout.SOUTH);

		if (GameData.getCurrentGame().isSeasonStarted()) {
			btnAdvWeek.setText("Advance Week");
		} 
		

		// TODO: actually set info here rather than just weeks.
		lblGenInfo.setText("<html>"
				+ Integer.toString(GameData.getCurrentGame().weeksLeft())
				+ " weeks left. File -> Reset to start new season</html>");
		pnlInfo.add(pnlGenInfo, BorderLayout.CENTER);
		pnlInfo.setPreferredSize(new Dimension(450, 400));

		pnlTribes.add(txtTribe1);
		pnlTribes.add(txtTribe2);
		pnlTribes.add(btnChangeTribeName);

		this.add(pnlInfo);
		this.add(pnlTribes);

	}

	private void initListeners() {
		btnAdvWeek.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GameData g = GameData.getCurrentGame();
					if(g.getAllContestants().size() == 0){
						JOptionPane.showMessageDialog(null,"This will be a boring game with no contestants.  Add some!");
						return;
					}
					if(g.getAllContestants().size() != g.getInitialContestants()){
						JOptionPane.showMessageDialog(null,"You need to have " + g.getInitialContestants() + " contestants to start.");
						return;
					}
					else if (!g.isSeasonStarted()) {
					String s = JOptionPane.showInputDialog("Enter weekly bet amount!");
					if (Utils.checkString(s, "^[0-9]+$")) {
						if (Integer.parseInt(s) >= 0) {
							GameData.getCurrentGame().startSeason(Integer.parseInt(s));
							MainFrame.getRunningFrame().seasonStarted();
							btnAdvWeek.setText("Advance Week");
							return;
						}
						return;
					}
					JOptionPane.showMessageDialog(null, "Invalid amount entered.");
				} else {
					GameData.getCurrentGame().advanceWeek();
					lblGenInfo.setText("<html>" + Integer.toString(GameData.getCurrentGame().weeksLeft())
							+ " weeks left. File -> Reset to start new season</html>");
					btnAdvWeek.setVisible(!GameData.getCurrentGame().isSeasonEnded());
				}
			}
		});

		btnChangeTribeName.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!Utils.checkString(txtTribe1.getText(),
						Person.TRIBE_PATTERN)) {
					MainFrame.getRunningFrame().setStatusErrorMsg(
							"Tribe 1 name invalid.", txtTribe1);
				} else if (!Utils.checkString(txtTribe2.getText(),
						Person.TRIBE_PATTERN)) {
					MainFrame.getRunningFrame().setStatusErrorMsg(
							"Tribe 2 name invalid.", txtTribe2);
				} else {
					GameData.getCurrentGame().setTribeNames(
							txtTribe1.getText(), txtTribe2.getText());
					MainFrame mf = MainFrame.getRunningFrame();
					mf.setStatusMsg("Tribes changed.");
					mf.forceGameDataRefresh();
				}
			}

		});
	}
}
