package admin;

import javax.swing.JPanel;

public class PlayerPanel extends JPanel {

	private EditPlayerFieldsPanel paneEditFields;
	
	public PlayerPanel(){
		paneEditFields = new EditPlayerFieldsPanel(new String[] {"SEXY", "MORE SEXY"});
		add(paneEditFields);
	}
}
