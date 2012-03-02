package admin.playertab;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import data.GameData;

public class PlayerTablePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTable table;
	protected PlayerTableModel tableModel; 
	
	public PlayerTablePanel(PlayerTableModel model) {
		super();
		
		tableModel = model;
		table = new JTable(tableModel);
		table.getTableHeader().setReorderingAllowed(false); // no moving.
		table.setColumnSelectionAllowed(true);
		
	
		JScrollPane scroll = new JScrollPane(table);
		
		setLayout(new BorderLayout(5, 5));
		add(scroll, BorderLayout.CENTER);
	}
	
}
