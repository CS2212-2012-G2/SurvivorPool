package admin.playertab;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import data.GameData;

public class PlayerTablePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTable table;
	protected PlayerTableModel tableModel; 
	private JTableHeader header;
	
	public PlayerTablePanel(PlayerTableModel model) {
		super();
		
		tableModel = model;
		table = new JTable(tableModel);
		header = table.getTableHeader();
		
		// settings:
		header.setReorderingAllowed(false); // no moving.
		table.setColumnSelectionAllowed(true);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		header.addMouseListener(tableModel.new SortColumnAdapter());
		table.addMouseListener(tableModel.new RowSelectorAdapter());
	
		TableCellRenderer renderer = new TableCellRenderer() {

			JLabel label = new JLabel();
			
			@Override
	        public JComponent getTableCellRendererComponent(JTable table,
	                Object value, boolean isSelected, boolean hasFocus,
	                int row, int column) {
	            
				if (table.isRowSelected(row)) {
					label.setBackground(Color.RED);
				} else {
					label.setBackground(UIManager.getColor("Table.background"));
				}
				
				label.setOpaque(true);
				label.setText("" + value);
				
	            return label;
	        }

	    };
	    table.setDefaultRenderer(Object.class, renderer);
		
		JScrollPane scroll = new JScrollPane(table);
		
		setLayout(new BorderLayout(5, 5));
		add(scroll, BorderLayout.CENTER);
	}
	
}
