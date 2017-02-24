package net.aegistudio.pe.viewer;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import net.aegistudio.pe.coff.Section;

public class SectionView extends JPanel {
	private static final long serialVersionUID = 1L;
	public final JTable table;
	public final DefaultTableModel tableModel;
	
	public SectionView(Section section) {
		super.setLayout(new BorderLayout());
		
		tableModel = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int r, int c) {
				return c != 0;
			}
		};
		tableModel.setColumnCount(2);
		
		DefaultTableColumnModel columnModel 
				= new DefaultTableColumnModel();
		
		TableColumn key = new TableColumn(0);
		key.setHeaderValue("Key");
		key.setPreferredWidth(100);
		columnModel.addColumn(key);
		
		TableColumn value = new TableColumn(1);
		value.setHeaderValue("Value");
		value.setPreferredWidth(300);
		columnModel.addColumn(value);
		
		addInformation("Name", section.asciiName.get());
		addInformation("Base (RVA)", 
				Long.toHexString(section.rvaBase.get()));
		addInformation("Size (RVA)", 
				Long.toHexString(section.rvaSize.get()));
		addInformation("Base", Long.toHexString(section.pointer.get()));
		addInformation("Size", Long.toHexString(section.size.get()));
		
		table = new JTable(tableModel, columnModel);
		table.getTableHeader().setReorderingAllowed(false);
		super.add(new JScrollPane(table));
	}
	
	public void addInformation(String name, String value) {
		Vector<String> row = new Vector<>();
		row.add(name); 
		row.add(value);
		tableModel.addRow(row);
	}
}
