package net.aegistudio.pe.viewer;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import net.aegistudio.pe.PortableExecutable;
import net.aegistudio.pe.coff.Section;
import net.aegistudio.uio.ra.RandomAccessible;

public class PeTableModel extends DefaultTreeModel {
	private static final long serialVersionUID = 1L;
	
	protected final DefaultMutableTreeNode peFile;
	
	public PeTableModel(PortableExecutable pe, RandomAccessible ra, File file) {
		super(new DefaultMutableTreeNode(
				new PeFileViewObject(file, pe)));
		
		this.peFile = (DefaultMutableTreeNode) super.getRoot();
		for(int i = 0; i < pe.sections.size(); i ++) {
			Section section = pe.sections.get(i);
			SectionViewObject sectionVo = new SectionViewObject(section, ra);
			DefaultMutableTreeNode sectionNode = 
					new DefaultMutableTreeNode(sectionVo);
			this.peFile.add(sectionNode);
		}
	}
}
