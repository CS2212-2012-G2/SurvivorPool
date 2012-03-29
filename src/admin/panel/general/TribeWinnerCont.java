package admin.panel.general;

import java.awt.CardLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class TribeWinnerCont extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static final String TRIBE = "Tribes",
			WINNERS = "Winners";
	
	private JPanel pnlWin, pnlTribe;
	
	private boolean showTribes;
	
	public TribeWinnerCont(JPanel tribePane, JPanel winPane, boolean showTribes) {
		super(new CardLayout(5, 5));
		//setBorder(BorderFactory.createTitledBorder(TRIBE));
		setBorder(BorderFactory.createTitledBorder(""));
		
		pnlWin = winPane;
		pnlTribe = tribePane;
		
		add(tribePane, TRIBE);
		add(winPane, WINNERS);
		
		this.showTribes = !showTribes; // filthy hack
		if (showTribes) {
			showTribe();
		} else {
			showWinners();
		}
	}
	
	
	private CardLayout getCL() {
		return (CardLayout)super.getLayout();
	}
	
	private TitledBorder getTBorder() {
		return (TitledBorder)getBorder();
	}
	
	protected void showTribe() {
		if (showTribes) return;
		
		showTribes = true;
		
		getTBorder().setTitle(TRIBE);
		getCL().show(this, TRIBE);
		repaint();
	}
	
	protected void showWinners() {
		if (!showTribes) return;
		
		showTribes = false;
		
		getTBorder().setTitle(WINNERS);
		getCL().show(this, WINNERS);
		repaint();
	}
	
	protected boolean isShowingTribes() {
		return showTribes;
	}
}
