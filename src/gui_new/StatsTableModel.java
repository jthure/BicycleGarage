package gui_new;

import javax.swing.table.AbstractTableModel;

public class StatsTableModel extends AbstractTableModel {
	String[] columnNames = {"Members", "Bicycles", "Bicycles parked", "Members checked in"};
	Object[][] data;
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public int getRowCount() {
		return data.length;
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

}
