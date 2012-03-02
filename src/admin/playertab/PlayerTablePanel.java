package admin.playertab;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class PlayerTablePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTable table;
	protected PlayerTableModel tableModel;
	
	private String[] columnNames = new String[] {
			"Select", "ID", "Last Name", "First Name", "Tribe", "Date Cast"
	};
	
	public PlayerTablePanel() {
		super();
		
		tableModel = new PlayerTableModel(columnNames);
		table = new JTable(tableModel);
	
		JScrollPane scroll = new JScrollPane(table);
		
		setLayout(new BorderLayout());
		add(scroll, BorderLayout.CENTER);
	}
	
}
