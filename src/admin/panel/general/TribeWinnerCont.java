package admin.panel.general;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class TribeWinnerCont extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static final String TRIBE = "tribe",
			WINNERS = "winners";
	
	private JPanel pnlWin, pnlTribe;
	
	private boolean showTribes;
	
	public TribeWinnerCont(JPanel tribePane, JPanel winPane, boolean showTribes) {
		super(new CardLayout());
		
		pnlWin = winPane;
		pnlTribe = tribePane;
		this.showTribes = showTribes;
		
		add(tribePane, TRIBE);
		add(winPane, WINNERS);
		
		if (showTribes) {
			showTribe();
		} else {
			showWinners();
		}
	}
	
	
	private CardLayout getCL() {
		return (CardLayout)super.getLayout();
	}
	
	protected void showTribe() {
		if (showTribes) return;
		
		showTribes = true;
		getCL().show(pnlTribe, TRIBE);
	}
	
	protected void showWinners() {
		if (!showTribes) return;
		
		showTribes = false;
		getCL().show(pnlWin, WINNERS);
	}
	
	protected boolean isShowingTribes() {
		return showTribes;
	}
}
