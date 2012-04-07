package admin.panel.general;

import java.awt.CardLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * Small container class to hold the tribe and winner panels and elegantly swap
 * between them without cluttering up {@link GeneralPanel}.
 * 
 * @author Kevin Brightwell (@Nava2)
 */
public class TribeWinnerCont extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static final String TRIBE = "Tribes",
			WINNERS = "Winners";
	
	private boolean showTribes;
	
	/**
	 * Creates the panel and lays out the two panels in a {@link CardLayout}.
	 * @param tribePane
	 * @param winPane
	 * @param showTribes	Whether to show tribes initially
	 */
	public TribeWinnerCont(JPanel tribePane, JPanel winPane, boolean showTribes) {
		super(new CardLayout(5, 5));
		//setBorder(BorderFactory.createTitledBorder(TRIBE));
		setBorder(BorderFactory.createTitledBorder(""));
		
		add(tribePane, TRIBE);
		add(winPane, WINNERS);
		
		this.showTribes = !showTribes; // filthy hack
		if (showTribes) {
			showTribe();
		} else {
			showWinners();
		}
	}
	
	/**
	 * Returns the {@link CardLayout} object used by this container.
	 * @return
	 */
	private CardLayout getCL() {
		return (CardLayout)super.getLayout();
	}
	
	/**
	 * Gets the {@link TitledBorder} object so the panel can change its Title 
	 * when switching from Tribes to displaying winners
	 * @return
	 */
	private TitledBorder getTBorder() {
		return (TitledBorder)getBorder();
	}
	
	/**
	 * Shows the tribe changing interface.
	 */
	protected void showTribe() {
		if (showTribes) return;
		
		showTribes = true;
		
		getTBorder().setTitle(TRIBE);
		getCL().show(this, TRIBE);
		repaint();
	}
	
	/**
	 * Shows the winners information
	 */
	protected void showWinners() {
		if (!showTribes) return;
		
		showTribes = false;
		
		getTBorder().setTitle(WINNERS);
		getCL().show(this, WINNERS);
		repaint();
	}
	
	/**
	 * Tells whether the container is showing the tribes
	 * @return	True if showing tribes
	 */
	protected boolean isShowingTribes() {
		return showTribes;
	}
}
