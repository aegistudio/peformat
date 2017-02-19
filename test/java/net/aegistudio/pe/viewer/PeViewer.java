package net.aegistudio.pe.viewer;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;

import org.junit.Test;

import net.aegistudio.pe.PortableExecutable;
import net.aegistudio.uio.CorruptException;
import net.aegistudio.uio.ra.AccessInputStream;
import net.aegistudio.uio.ra.RandomFileAdapter;
import net.aegistudio.uio.stream.InputTranslator;

public class PeViewer {
	public static final String LOOK_AND_FEEL = 
			"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";

	public @Test void test() throws CorruptException, IOException, InterruptedException {
		try { UIManager.setLookAndFeel(LOOK_AND_FEEL); } catch(Exception e) {}
		
		// Initialize file chooser.
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File arg0) {
				if(arg0.isDirectory()) return true;
				if(arg0.getName().endsWith(".exe")) return true;
				if(arg0.getName().endsWith(".dll")) return true;
				if(arg0.getName().endsWith(".lib")) return true;
				return false;
			}
	
			@Override
			public String getDescription() {
				return "PE format (*.exe, *.dll, *.lib)";
			}
		});
		
		// Open file.
		int status = fileChooser.showOpenDialog(null);
		if(status != JFileChooser.APPROVE_OPTION) return;
		
		File file = fileChooser.getSelectedFile();
		RandomAccessFile randomFile = new RandomAccessFile(file, "r");
		RandomFileAdapter adapter = new RandomFileAdapter(randomFile);
		PortableExecutable pe = new PortableExecutable();
		pe.translate(new InputTranslator(new AccessInputStream(adapter), "utf8"), adapter);
				
		// Open viewer.
		JFrame jframe = new JFrame("Pe Viewer");
		jframe.setLocationRelativeTo(null);
		jframe.setSize(600, 480);
		
		JTabbedPane tabPane = new JTabbedPane();
		jframe.add(BorderLayout.CENTER, tabPane);
		
		JTree contentTable = new JTree();
		contentTable.setModel(new PeTableModel(pe, adapter, file));
		contentTable.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) 
						arg0.getPath().getLastPathComponent();
				Object object = treeNode.getUserObject();
				
				if(object instanceof ViewObject) {
					ViewObject vo = (ViewObject)object;
					tabPane.removeAll();
	
					vo.openView().forEach(entry ->
							tabPane.add(entry.name, entry.component));
				}
			}
		});
		jframe.add(BorderLayout.WEST, new JScrollPane(contentTable));
		
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jframe.setVisible(true);
		
		while(true) { 
			if(!jframe.isDisplayable()) return;
			Thread.sleep(1000);
		}
	}
}
