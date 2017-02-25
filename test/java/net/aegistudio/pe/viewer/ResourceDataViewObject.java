package net.aegistudio.pe.viewer;

import java.awt.BorderLayout;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import net.aegistudio.pe.LcidTable;
import net.aegistudio.pe.rsrc.Resource;
import net.aegistudio.pe.rsrc.WcharString;
import net.aegistudio.pe.rsrc.WinResourceType;
import net.aegistudio.uio.ra.AccessInputStream;
import net.aegistudio.uio.ra.ByteBufferAdapter;
import net.aegistudio.uio.ra.RandomAccessible;
import net.aegistudio.uio.stream.InputTranslator;

public class ResourceDataViewObject implements ViewObject {
	protected final Resource resource;
	protected final RandomAccessible ra;
	protected final String name;
	protected final WinResourceType rcType;
	
	public ResourceDataViewObject(WinResourceType resourceType, 
			String name, RandomAccessible ra, Resource resource) {
		this.rcType = resourceType;
		this.name = name;
		this.resource = resource;
		this.ra = ra;
	}
	
	public String toString() {
		if(rcType != null && rcType.equals(WinResourceType.STRING)) {
			return LcidTable.instance.getLangugage(Integer.parseInt(name))
				+ " (" + name + ")";
		}
		return name;
	}
	
	@Override
	public List<Entry> openView() {
		try {
			List<Entry> views = new ArrayList<>();
			
			byte[] data = new byte[resource.size.get()];
			InputTranslator translator = new InputTranslator(
					new AccessInputStream(ra), "utf8");
			resource.data(ra, translator, data);

			if(rcType != null) switch(rcType) {
				case HTML:
				//case VERSION:
				case STRING:
					InputTranslator innerTranslator = new InputTranslator(
							new AccessInputStream(new ByteBufferAdapter(data)), "utf8");
					
					DefaultTableModel stringTable = new DefaultTableModel();
					stringTable.setColumnCount(2);
					try {
						for(int i = 0; i < 16; i ++) {
							WcharString string = new WcharString("");
							string.translate(innerTranslator);
							Vector<String> rowVector = new Vector<>();
							rowVector.add(Integer.toString(i));
							rowVector.add(string.get());
							stringTable.addRow(rowVector);
						}
					}
					catch(EOFException eof) {
						
					}
					stringTable.fireTableDataChanged();
					
					DefaultTableColumnModel columnModel = new DefaultTableColumnModel();
					TableColumn index = new TableColumn(0);
					index.setHeaderValue("#");
					index.setPreferredWidth(100);
					columnModel.addColumn(index);
					
					TableColumn stringCol = new TableColumn(1);
					stringCol.setHeaderValue("String");
					stringCol.setPreferredWidth(300);
					columnModel.addColumn(stringCol);
					
					JPanel textPanel = new JPanel();
					textPanel.setLayout(new BorderLayout());
					textPanel.add(new JScrollPane(new JTable(stringTable, columnModel)));
					views.add(new Entry("Text", textPanel));
				default:
					break;
			}
			
			views.add(new Entry("Hex", new HexDumpView(data, 16, 4)));
			return views;
		}
		catch(IOException e) {
			e.printStackTrace();
			return Arrays.asList();
		}
	}

}
